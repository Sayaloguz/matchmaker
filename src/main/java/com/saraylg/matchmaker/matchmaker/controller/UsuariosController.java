package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.UsuariosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;

    // Este es de prueba
    @GetMapping("/steam/{steamId}")
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


    @GetMapping("/")
    public List<UsuarioOutputDTO> getAllUsers() {
        return usuariosService.getAllUsers();
    }

    @GetMapping("/mongo/{steamId}")
    public UsuarioOutputDTO getUserById(@PathVariable String steamId) {
        return usuariosService.getUserById(steamId);
    }

    @PutMapping("/{steamId}")
    public UsuarioOutputDTO updateUser(
            @PathVariable String steamId,
            @RequestBody @Valid UsuarioInputDTO usuarioDTO
    ) {
        return usuariosService.updateUser(steamId, usuarioDTO);
    }

    @DeleteMapping("/{steamId}")
    public String deleteUser(@PathVariable String steamId) {
        return usuariosService.deleteUser(steamId);
    }


}