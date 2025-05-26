package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuariosService;

    @GetMapping("/byId/steam/{steamId}")
    public UsuarioOutputDTO getPlayerData(@PathVariable String steamId) {
        return usuariosService.getPlayer(steamId);
    }

    @GetMapping("/byId/mongo/{steamId}")
    public UsuarioOutputDTO getUserById(@PathVariable String steamId) {
        return usuariosService.getUserById(steamId);
    }

    @PostMapping("/save/{steamId}")
    public UsuarioOutputDTO getAndSavePlayer(@PathVariable String steamId) {
        return usuariosService.getAndSavePlayer(steamId);
    }

    @GetMapping("/")
    public List<UsuarioOutputDTO> getAllUsers() {
        return usuariosService.getAllUsers();
    }


    @PutMapping("/update/{steamId}")
    public UsuarioOutputDTO updateUser(
            @PathVariable String steamId,
            @RequestBody @Valid UsuarioInputDTO usuarioDTO
    ) {
        return usuariosService.updateUser(steamId, usuarioDTO);
    }

    @DeleteMapping("/delete/{steamId}")
    public String deleteUser(@PathVariable String steamId) {
        return usuariosService.deleteUser(steamId);
    }

}
