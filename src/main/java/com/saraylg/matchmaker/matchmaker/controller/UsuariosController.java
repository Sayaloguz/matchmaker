package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.UsuariosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;


    @GetMapping("/{steamId}")
    public UsuarioOutputDTO getPlayerData(@PathVariable String steamId) {
        return usuariosService.getPlayer(steamId);
    }


    @PostMapping("/")
    public String saveUser(@Valid @RequestBody UsuarioInputDTO usuarioDTO) {
        return usuariosService.saveUser(usuarioDTO);
    }


    @PostMapping("/{steamId}")
    public UsuarioOutputDTO getAndSavePlayer(@PathVariable String steamId) {
        return usuariosService.getAndSavePlayer(steamId);
    }


}