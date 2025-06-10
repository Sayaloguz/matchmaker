package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.input.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.GenericResponseDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;
import com.saraylg.matchmaker.matchmaker.service.JamService;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericJam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/jams")
@RequiredArgsConstructor
@Tag(name = "Jams", description = "Endpoints relacionados con la gestión de jams (eventos de creación)")
public class JamController {

    private final JamService jamService;
    private final JamMapper jamMapper;

    @Operation(summary = "Obtener todas las jams disponibles")
    @GetMapping("/")
    public List<JamOutputDTO> getAllJams() {
        return jamMapper.genericListToOutput(
                jamService.getAllJams());
    }

    @Operation(summary = "Obtener jams por estado")
    @GetMapping("/byState/{state}")
    public List<JamOutputDTO> getJamByState(@PathVariable String state) {
        return jamMapper.genericListToOutput(jamService.getJamsByState(state));
    }

    @Operation(summary = "Obtener una jam por su ID")
    @GetMapping("/byId/{id}")
    public JamOutputDTO getJamById(@PathVariable String id) {
        return jamMapper.genericToOutput(jamService.getJamById(id));
    }

    @Operation(summary = "Buscar jams por título")
    @GetMapping("/byTitle/{title}")
    public List<JamOutputDTO> getByTitle(@PathVariable String title) {
        return jamMapper.genericListToOutput(jamService.getJamsByTitle(title));
    }

    @Operation(summary = " Buscar jams abiertas por título")
    @GetMapping("/openByTitle")
    public List<JamOutputDTO> getOpenByTitle(@RequestParam String title) {
        return jamMapper.genericListToOutput(jamService.getOpenJamsByTitle(title));
    }

    @Operation(summary = "Obtener jams creadas por un usuario (ID del creador)")
    @GetMapping("/byCreator/{id}")
    public List<JamOutputDTO> getByCreator(@PathVariable String id) {
        return jamMapper.genericListToOutput(jamService.getJamsByCreator(id));
    }

    @Operation(summary = "Obtener jams en las que participa un usuario")
    @GetMapping("/byUser/{id}")
    public List<JamOutputDTO> getByUser(@PathVariable String id) {
        return jamMapper.genericListToOutput(jamService.getJamsByUser(id));
    }

    @Operation(summary = "Crear una nueva jam")
    @PostMapping("/save")
    public JamOutputDTO createJam(@Valid @RequestBody JamInputDTO jamInputDTO) {
        return jamMapper.genericToOutput(jamService.newJam(jamMapper.inputToGeneric(jamInputDTO)
        ));
    }

    @Operation(summary = "Modificar una jam existente")
    @PutMapping("/modify")
    public JamOutputDTO modifyJam(@Valid @RequestBody JamModifyDTO jamModifyDTO) {
        return jamMapper.genericToOutput(
                jamService.modifyJam(
                        jamMapper.modifyToGeneric(jamModifyDTO))
        );
    }

    // Añadir y quitar jugadores de una jam
    @Operation(summary = "Añadir un jugador a una jam. Se pide input de usuario completo para evitar referencias circulares.")
    @PostMapping("/{jamId}/addPlayer")
    public GenericResponseDTO<GenericJam> addPlayerToJam(
            @PathVariable String jamId,
            @RequestBody UsuarioInputDTO jugador) {
        return new GenericResponseDTO<GenericJam>(
                "Jugador añadido correctamente",
                "200",
                jamService.addPlayerToJam(jamId, jugador)
        );
    }

    @Operation(summary = "Eliminar un jugador de una jam")
    @DeleteMapping("/{jamId}/removePlayer/{steamId}")
    public GenericResponseDTO<GenericJam> removePlayerFromJam(
            @PathVariable String jamId,
            @PathVariable String steamId) {

        return new GenericResponseDTO<GenericJam>(
                "Jugador eliminado correctamente",
                "200",
                jamService.removePlayerFromJam(jamId, steamId)
        );
    }


    @Operation(summary = "Eliminar una jam por su ID")
    @DeleteMapping("/delete/{id}")
    public GenericResponseDTO<GenericJam> deleteJam(@PathVariable String id) {

        return new GenericResponseDTO<GenericJam>(
                "Jam eliminada correctamente",
                "200",
                jamService.deleteJam(id)
        );
    }

}
