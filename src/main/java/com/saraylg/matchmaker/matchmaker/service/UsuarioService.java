package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.input.SteamPlayerInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.internal.SteamApiResponse;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.repository.UsuarioRepository;
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

    /**
     * Devuelve los datos del jugador. Si no está en la BD, lo obtiene de Steam.
     * ✅ Usa la API de Steam si el usuario no existe.
     */
    public UsuarioOutputDTO getPlayer(String steamId) {
        Optional<UsuarioEntity> userOpt = usuariosRepository.findUserById(steamId);

        if (userOpt.isPresent()) {
            return usuarioMapper.entityToOutputDto(userOpt.get());
        } else {
            SteamPlayerInputDTO steamPlayer = fetchSteamPlayer(steamId);
            UsuarioInputDTO dto = usuarioMapper.steamPlayerToDto(steamPlayer);
            usuariosRepository.saveUser(dto);
            return usuarioMapper.dtoToOutputDto(dto);
        }
    }

    /**
     * Si el usuario no existe, lo obtiene de Steam y lo guarda.
     * ✅ Usa la API de Steam si es necesario.
     */
    public UsuarioOutputDTO getAndSavePlayer(String steamId) {
        Optional<UsuarioEntity> existing = usuariosRepository.findUserById(steamId);

        if (existing.isPresent()) {
            return usuarioMapper.entityToOutputDto(existing.get());
        }

        SteamPlayerInputDTO steamPlayer = fetchSteamPlayer(steamId);
        UsuarioInputDTO dto = usuarioMapper.steamPlayerToDto(steamPlayer);
        usuariosRepository.saveUser(dto);
        return usuarioMapper.dtoToOutputDto(dto);
    }

    /**
     * Devuelve todos los usuarios guardados.
     * ❌ No usa la API de Steam.
     */
    public List<UsuarioOutputDTO> getAllUsers() {
        return usuariosRepository.findAllUsers()
                .stream()
                .map(usuarioMapper::entityToOutputDto)
                .toList();
    }

    /**
     * Devuelve un usuario por su ID desde la base de datos.
     * ❌ No usa la API de Steam.
     */
    public UsuarioOutputDTO getUserById(String steamId) {
        UsuarioEntity user = usuariosRepository.findUserById(steamId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.entityToOutputDto(user);
    }

    /**
     * Actualiza los datos del usuario.
     *  ✅ Sí usa la API de Steam.
     */
    public UsuarioOutputDTO updateUser(String steamId) {
        // Reutilizamos método existente para obtener el usuario desde Steam
        SteamPlayerInputDTO steamPlayer = fetchSteamPlayer(steamId);
        UsuarioInputDTO usuarioDesdeSteam = usuarioMapper.steamPlayerToDto(steamPlayer);

        // Reutilizamos el método del repository
        return usuariosRepository.updateUserIfChanged(steamId, usuarioDesdeSteam);
    }

    /**
     * Elimina un usuario por su Steam ID.
     * ❌ No usa la API de Steam.
     */
    public String deleteUser(String steamId) {
        return usuariosRepository.deleteUser(steamId);
    }

    /**
     * Método interno para consultar a la Steam Web API y obtener los datos de un jugador.
     * ✅ Usa la API de Steam.
     */
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

    /**
     * Crea un usuario vacío si no existe.
     * ❌ No usa la API de Steam.
     */
    public UsuarioOutputDTO getOrCreateUser(String steamId) {
        Optional<UsuarioEntity> userOpt = usuariosRepository.findUserById(steamId);

        if (userOpt.isPresent()) {
            return usuarioMapper.entityToOutputDto(userOpt.get());
        } else {
            UsuarioInputDTO newUser = new UsuarioInputDTO();
            newUser.setSteamId(steamId);
            usuariosRepository.saveUser(newUser);
            return getUserById(steamId);
        }
    }
}
