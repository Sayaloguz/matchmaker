package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.input.SteamAppDetailsInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.internal.SteamAppListResponse;
import com.saraylg.matchmaker.matchmaker.mapper.SteamAppMapper;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.repository.mongo.SteamAppMongoRepository;
import com.saraylg.matchmaker.matchmaker.service.generics.GenericSteamApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamAppRepository {

    private final SteamAppMongoRepository repo;
    private final WebClient steamWebClient;   // https://api.steampowered.com
    private final WebClient storeWebClient;   // https://store.steampowered.com/api
    private final SteamAppMapper steamAppMapper;
    private static final String APPS_PATH = "/ISteamApps/GetAppList/v2";
    private static final String LAST_APP_ID_FILE = "last_appid.txt";

    /**
     * Descarga la lista entera de apps.
     * Para cada app, llama a /appdetails y filtra por type (game o dlc).
     * Solo guarda esas apps en Mongo, preservando las que ya están.
     * Usa control de concurrencia y retry con delay para evitar baneos.
     */
    public long syncCatalog() {
        SteamAppListResponse listResponse = steamWebClient.get()
                .uri(APPS_PATH)
                .retrieve()
                .bodyToMono(SteamAppListResponse.class)
                .block();

        if (listResponse == null || listResponse.getApplist() == null) return 0;

        List<SteamAppListResponse.App> apps = listResponse.getApplist().getApps();
        System.out.println("Total apps recibidas: " + apps.size());

        long lastAppId = readLastAppId();
        System.out.println("Reanudando desde appid > " + lastAppId);

        AtomicInteger counter = new AtomicInteger();
        AtomicInteger totalSaved = new AtomicInteger();
        AtomicLong lastProcessedAppId = new AtomicLong(lastAppId);

        Flux.fromIterable(apps)
                .filter(app -> app.getAppid() > lastAppId)
                .filter(app -> app.getName() != null && !app.getName().isBlank())
                .delayElements(Duration.ofMillis(100)) // aumentar para evitar bans
                .flatMap(app -> fetchDetails(app.getAppid())
                                .doOnError(e -> System.err.println("Error fetching details for appid " + app.getAppid() + ": " + e.getMessage()))
                                .onErrorResume(e -> Mono.empty())
                        , 5) // concurrencia baja para no saturar API
                .filter(Objects::nonNull)
                .doOnNext(app -> {
                    int current = counter.incrementAndGet();
                    lastProcessedAppId.set(app.getAppid());
                    if (current % 50 == 0) {
                        System.out.println("/////////////////////////////////// Procesados: " + current + " apps");
                    }
                })
                .buffer(50)
                .flatMap(batch -> Mono.fromCallable(() -> {
                    repo.saveAll(batch); // síncrono, bloqueante
                    return batch.size();
                }))
                .doOnNext(savedCount -> {
                    int total = totalSaved.addAndGet(savedCount);
                    System.out.println("//////////////////////////////////////////// Guardado lote. Total acumulado: " + total);
                    saveLastAppId(lastProcessedAppId.get());
                })
                .blockLast();

        System.out.println("Sincronización finalizada. Total apps guardadas: " + totalSaved.get());
        return totalSaved.get();
    }

    private Mono<SteamAppEntity> fetchDetails(Long appid) {
        return storeWebClient.get()
                .uri("/appdetails?appids={id}", appid)
                .retrieve()
                .bodyToMono(SteamAppDetailsInputDTO.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(10)))
                .flatMap(resp -> {
                    SteamAppDetailsInputDTO.AppDetails d = resp.get(String.valueOf(appid));
                    if (d == null || d.getData() == null) return Mono.empty();

                    boolean isGame = "game".equalsIgnoreCase(d.getData().getType());
                    boolean comingSoon = Optional.ofNullable(d.getData().getReleaseDate())
                            .map(SteamAppDetailsInputDTO.ReleaseDate::isComingSoon)
                            .orElse(false);
                    boolean isAdultOnly = Optional.ofNullable(d.getData().getCategories())
                            .map(cats -> cats.stream()
                                    .anyMatch(cat -> "Adult Only".equalsIgnoreCase(cat.getDescription())))
                            .orElse(false);

                    System.out.println("appid " + appid + " | " + d.getData().getName());

                    if (isGame && !comingSoon && !isAdultOnly) {
                        System.out.println("|||||||||||||||||||||||||||||||||||||||| Pasa el filtro - AppId: " + appid);
                        return Mono.just(steamAppMapper.toEntity(appid, d));
                    }

                    return Mono.empty();
                })
                .onErrorResume(e -> {
                    System.err.println("Retries exhausted for appid " + appid + ": " + e.getMessage());
                    return Mono.empty();
                });
    }


    private void saveLastAppId(long appid) {
        try (FileWriter writer = new FileWriter(LAST_APP_ID_FILE)) {
            writer.write(String.valueOf(appid));
        } catch (IOException e) {
            System.err.println("Error al guardar lastAppId: " + e.getMessage());
        }
    }

    private long readLastAppId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LAST_APP_ID_FILE))) {
            return Long.parseLong(reader.readLine());
        } catch (Exception e) {
            return 0L; // Si no existe archivo o error, empezar desde 0
        }
    }

    // Devolución de datos desde la BBDD

    public List<GenericSteamApp> findAll() {
        List<SteamAppEntity> apps = repo.findAll();
        return apps.stream()
                .map(steamAppMapper::entityToGeneric)
                .collect(Collectors.toList());
    }


    public Optional<GenericSteamApp> findByAppid(Long appid) {
        return repo.findByAppid(appid)
                .map(steamAppMapper::entityToGeneric);
    }


    public List<GenericSteamApp> findByName(String name) {
        List<SteamAppEntity> apps = repo.findByNameRegexIgnoreCase(".*" + name + ".*");
        return apps.stream()
                .map(steamAppMapper::entityToGeneric)
                .collect(Collectors.toList());
    }

}
