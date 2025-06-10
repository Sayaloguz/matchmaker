package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.input.SteamPlayerInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.internal.SteamApiResponse;
import com.saraylg.matchmaker.matchmaker.exceptions.UserNotFoundException;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.repository.UsuarioRepository;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuariosRepository;
    private final UsuarioMapper usuarioMapper;

    @Value("${steam.api.key}")
    private String steamApiKey;
    private final WebClient steamWebClient;
    private static final String STEAM_API_PLAYER_PATH = "/ISteamUser/GetPlayerSummaries/v0002/";

    private final JamService jamService;
    private final InvitationService invitationService;


    public GenericUsuario getPlayer(String steamId) {
        Optional<GenericUsuario> userOpt = usuariosRepository.findUserById(steamId);

        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            SteamPlayerInputDTO steamPlayer = fetchSteamPlayer(steamId);
            GenericUsuario usuario = usuarioMapper.steamPlayerToGeneric(steamPlayer);
            return usuariosRepository.saveUser(usuario);
        }
    }


    public GenericUsuario getAndSavePlayer(String steamId) {
        Optional<GenericUsuario> existing = usuariosRepository.findUserById(steamId);

        if (existing.isPresent()) {
            return existing.get();
        }

        SteamPlayerInputDTO steamPlayer = fetchSteamPlayer(steamId);

        return usuariosRepository.saveUser(usuarioMapper.steamPlayerToGeneric(steamPlayer));
    }


    public List<GenericUsuario> getAllUsers() {
        return usuariosRepository.findAllUsers();
    }


    public GenericUsuario getUserById(String steamId) {
        GenericUsuario user = usuariosRepository.findUserById(steamId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return user;
    }

    public GenericUsuario updateUser(String steamId) {

        SteamPlayerInputDTO steamPlayer = fetchSteamPlayer(steamId);

        GenericUsuario usuario = usuarioMapper.steamPlayerToGeneric(steamPlayer);

        return usuariosRepository.updateUserIfChanged(steamId, usuario);
    }


    public GenericUsuario deleteUser(String steamId) {
        usuariosRepository.findUserById(steamId)
                .orElseThrow(() -> new UserNotFoundException(steamId));

        jamService.deleteJamsCreatedByUser(steamId);
        jamService.removeUserFromAllJams(steamId);
        invitationService.deleteAllUserInvitations(steamId);

        return usuariosRepository.deleteUser(steamId);
    }

    // Consulta a la Steam Web API y obtiene los datos de un jugador en su sistema
    private SteamPlayerInputDTO fetchSteamPlayer(String steamId) {
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
