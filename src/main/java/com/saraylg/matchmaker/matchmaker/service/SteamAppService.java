package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.repository.SteamAppRepository;
import com.saraylg.matchmaker.matchmaker.service.generics.GenericSteamApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SteamAppService {

    private final SteamAppRepository repository;

    public long syncCatalog() {
        return repository.syncCatalog();
    }

    public List<GenericSteamApp> findAll() {
        return repository.findAll();
    }

    public Optional<GenericSteamApp> findByAppid(Long appid) {
        return repository.findByAppid(appid);
    }

    public List<GenericSteamApp> findByName(String name) {
        return repository.findByName(name);
    }
}
