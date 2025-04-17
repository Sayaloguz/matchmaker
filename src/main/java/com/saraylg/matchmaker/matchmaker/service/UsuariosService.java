package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.SteamApiResponse;
import com.saraylg.matchmaker.matchmaker.dto.SteamPlayer;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuariosService {

    private final UsuariosRepository usuariosRepository;

    private final UsuarioMapper usuarioMapper;

    @Value("${steam.api.key}")
    private String steamApiKey;

    private final WebClient steamWebClient;

    private static final String STEAM_API_PLAYER_PATH = "/ISteamUser/GetPlayerSummaries/v0002/";


    public UsuarioOutputDTO getPlayer(String steamId) {
        return usuarioMapper.steamPlayerToOutputDto(fetchSteamPlayer(steamId));
    }


    public String saveUser(UsuarioInputDTO usuarioDTO) {
        return usuariosRepository.saveUser(usuarioDTO);
    }


    public UsuarioOutputDTO getAndSavePlayer(String steamId) {
        UsuarioInputDTO dto = usuarioMapper.steamPlayerToDto(fetchSteamPlayer(steamId));
        usuariosRepository.saveUser(dto);

        UsuarioOutputDTO outputDto = usuarioMapper.dtoToOutputDto(dto);
        return outputDto;
    }


    public List<UsuarioOutputDTO> getAllUsers() {
        return usuariosRepository.findAllUsers()
                .stream()
                .map(usuarioMapper::entityToOutputDto)
                .toList();
    }

    public UsuarioOutputDTO getUserById(String steamId) {
        UsuarioEntity user = usuariosRepository.findUserById(steamId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.entityToOutputDto(user);
    }

    public UsuarioOutputDTO updateUser(String steamId, UsuarioInputDTO dto) {
        return usuariosRepository.updateUser(steamId, dto);
    }

    public String deleteUser(String steamId) {
        return usuariosRepository.deleteUser(steamId);
    }


    // Métodos complementarios

    private SteamPlayer fetchSteamPlayer(String steamId) {
        try {
            SteamApiResponse response = steamWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(STEAM_API_PLAYER_PATH)
                            .queryParam("key", steamApiKey)
                            .queryParam("steamids", steamId)
                            .build())
                    .retrieve()
                    .bodyToMono(SteamApiResponse.class)
                    .block();

            if (response == null || response.getResponse().getPlayers().isEmpty()) {
                throw new RuntimeException("No se encontró información del jugador con Steam ID: " + steamId);
            }

            return response.getResponse().getPlayers().get(0);

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la Steam API: " + e.getMessage(), e);
        }

    }

}
