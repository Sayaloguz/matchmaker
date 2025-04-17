package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.SteamApiResponse;
import com.saraylg.matchmaker.matchmaker.dto.SteamPlayer;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    private final UsuarioMapper usuarioMapper;

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final WebClient webClient;

    private static final String STEAM_API_PLAYER_URL = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/";



    public UsuarioDTO getPlayer(String steamId) {

        return usuarioMapper.steamPlayerToDto(fetchSteamPlayer(steamId));

    }


    public String saveUser(UsuarioDTO usuarioDTO) {

        return usuariosRepository.saveUser(usuarioDTO);

    }


    public UsuarioDTO getAndSavePlayer(String steamId) {

        UsuarioDTO dto = usuarioMapper.steamPlayerToDto(fetchSteamPlayer(steamId));
        usuariosRepository.saveUser(dto);

        return dto;
    }


    // Métodos complementarios

    private SteamPlayer fetchSteamPlayer(String steamId) {
        SteamApiResponse steamApiResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(STEAM_API_PLAYER_URL)
                        .queryParam("key", steamApiKey)
                        .queryParam("steamids", steamId)
                        .build())
                .retrieve()
                .bodyToMono(SteamApiResponse.class)
                .block();

        return steamApiResponse.getResponse().getPlayers().get(0);
    }

}
