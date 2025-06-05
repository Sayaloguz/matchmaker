package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints relacionados con usuarios registrados")
public class UsuarioController {


    private final UsuarioService usuariosService;

    @Operation(summary = "Obtener datos de jugador por Steam ID (fuente externa)")
    @GetMapping("/byId/steam/{steamId}")
    public UsuarioOutputDTO getPlayerData(@PathVariable String steamId) {
        return usuariosService.getPlayer(steamId);
    }

    @Operation(summary = "Obtener usuario por ID almacenado en MongoDB")
    @GetMapping("/byId/mongo/{steamId}")
    public UsuarioOutputDTO getUserById(@PathVariable String steamId) {
        return usuariosService.getUserById(steamId);
    }

    @Operation(summary = "Buscar usuario en Steam y guardarlo en la base de datos")
    @PostMapping("/save/{steamId}")
    public UsuarioOutputDTO getAndSavePlayer(@PathVariable String steamId) {
        return usuariosService.getAndSavePlayer(steamId);
    }

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping("/")
    public List<UsuarioOutputDTO> getAllUsers() {
        return usuariosService.getAllUsers();
    }

    @Operation(summary = "Actualizar datos del usuario")
    @PutMapping("/{steamId}")
    public UsuarioOutputDTO updateUser(
            @PathVariable String steamId
    ) {
        return usuariosService.updateUser(steamId);
    }

    @Operation(summary = "Eliminar un usuario por Steam ID")
    @DeleteMapping("/{steamId}")
    public String deleteUser(@PathVariable String steamId) {
        return usuariosService.deleteUser(steamId);
    }


}
