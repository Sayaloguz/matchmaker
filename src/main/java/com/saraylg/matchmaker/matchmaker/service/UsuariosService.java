package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.SteamApiResponse;
import com.saraylg.matchmaker.matchmaker.dto.SteamPlayer;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioDTO;
import com.saraylg.matchmaker.matchmaker.repository.UsuariosRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final WebClient webClient;


    public UsuarioDTO getPlayer(String steamId) {
        String url = "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + steamApiKey + "&steamids=" + steamId;

        // Realiza la llamada a la API usando WebClient
        SteamApiResponse steamApiResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(SteamApiResponse.class)
                .block();


        SteamPlayer player = steamApiResponse.getResponse().getPlayers().get(0);

        System.out.println("Steam ID: " + player.getSteamId());

        UsuarioDTO usuarioOutputDTO = new UsuarioDTO();

        usuarioOutputDTO.setSteamId(steamId);
        // Necesitamos parsear la respuesta JSON para obtener el nombre (name), profileUrl, avatar y timeCreated
        usuarioOutputDTO.setName(player.getName());
        usuarioOutputDTO.setProfileUrl(player.getProfileUrl());
        usuarioOutputDTO.setAvatar(player.getAvatar());
        usuarioOutputDTO.setTimeCreated(String.valueOf(player.getTimeCreated()));


        return usuarioOutputDTO;
    }

}
