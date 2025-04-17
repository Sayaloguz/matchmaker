package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioDTO;
import com.saraylg.matchmaker.matchmaker.service.UsuariosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;

    /**
     * Devuelve los datos públicos de un jugador de Steam
     * @param steamId Steam ID del usuario
     * @return  DTO con nombre, avatar, url de perfil, etc.
     */
    @GetMapping("/player-data/{steamId}")
    public UsuarioDTO getPlayerData(@PathVariable String steamId) {

        return usuariosService.getPlayer(steamId);

    }

}