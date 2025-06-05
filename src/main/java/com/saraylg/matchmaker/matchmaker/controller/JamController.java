package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.input.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.JamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/jams")
@RequiredArgsConstructor
@Tag(name = "Jams", description = "Endpoints relacionados con la gestión de jams (eventos de creación)")
public class JamController {

    private final JamService jamService;

    @Operation(summary = "Obtener todas las jams disponibles")
    @GetMapping("/")
    public List<JamOutputDTO> getAllJams() {
        return jamService.getAllJams();
    }

    @Operation(summary = "Obtener jams por estado")
    @GetMapping("/byState/{state}")
    public List<JamOutputDTO> getJamByState(@PathVariable String state) {
        //System.out.println("Buscando jams con estado: " + state);
        return jamService.getJamsByState(state);
    }

    @Operation(summary = "Obtener una jam por su ID")
    @GetMapping("/byId/{id}")
    public JamOutputDTO getJamById(@PathVariable String id) {
        return jamService.getJamById(id);
    }

    @Operation(summary = "Buscar jams por título")
    @GetMapping("/byTitle/{title}")
    public List<JamOutputDTO> getByTitle(@PathVariable String title) {
            return jamService.getJamsByTitle(title);
    }

    @Operation(summary = " Buscar jams abiertas por título")
    @GetMapping("/openByTitle")
    public List<JamOutputDTO> getOpenByTitle(@RequestParam String title) {
        return jamService.getOpenJamsByTitle(title);
    }

    @Operation(summary = "Obtener jams creadas por un usuario (ID del creador)")
    @GetMapping("/byCreator/{id}")
    public List<JamOutputDTO> getByCreator(@PathVariable String id) {
        return jamService.getJamsByCreator(id);
    }

    @Operation(summary = "Obtener jams en las que participa un usuario")
    @GetMapping("/byUser/{id}")
    public List<JamOutputDTO> getByUser(@PathVariable String id) {
        return jamService.getJamsByUser(id);
    }

    @Operation(summary = "Crear una nueva jam")
    @PostMapping("/save")
    public JamOutputDTO createJam(@Valid @RequestBody JamInputDTO jamInputDTO) {
        return jamService.newJam(jamInputDTO);
    }

    @Operation(summary = "Modificar una jam existente")
    @PutMapping("/modify")
    public JamOutputDTO modifyJam(@Valid @RequestBody JamModifyDTO jamModifyDTO) {
        return jamService.modifyJam(jamModifyDTO);
    }

    // Añadir y quitar jugadores de una jam

    @Operation(summary = "Añadir un jugador a una jam")
    @PostMapping("/{jamId}/addPlayer")
    public ResponseEntity<JamOutputDTO> addPlayerToJam(
            @PathVariable String jamId,
            @RequestBody UsuarioInputDTO jugador) {
        return ResponseEntity.ok(jamService.addPlayerToJam(jamId, jugador));
    }

    @Operation(summary = "Eliminar un jugador de una jam")
    @DeleteMapping("/{jamId}/removePlayer/{steamId}")
    public ResponseEntity<JamOutputDTO> removePlayerFromJam(
            @PathVariable String jamId,
            @PathVariable String steamId) {
        return ResponseEntity.ok(jamService.removePlayerFromJam(jamId, steamId));
    }


    @Operation(summary = "Eliminar una jam por su ID")
    @DeleteMapping("/delete/{id}")
    public String deleteJam(@PathVariable String id) {
        return jamService.deleteJam(id);
    }

}
