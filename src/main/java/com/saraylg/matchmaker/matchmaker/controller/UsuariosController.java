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


    @GetMapping("/{steamId}")
    public UsuarioDTO getPlayerData(@PathVariable String steamId) {

        return usuariosService.getPlayer(steamId);

    }


    @PostMapping("/")
    public String saveUser(@RequestBody UsuarioDTO usuarioDTO) {
        return usuariosService.saveUser(usuarioDTO);
    }


    @PostMapping("/{steamId}")
    public UsuarioDTO getAndSavePlayer(@PathVariable String steamId) {
        return usuariosService.getAndSavePlayer(steamId);
    }


}