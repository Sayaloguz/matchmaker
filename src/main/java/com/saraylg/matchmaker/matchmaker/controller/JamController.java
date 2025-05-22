package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.*;
import com.saraylg.matchmaker.matchmaker.service.JamService;
import com.saraylg.matchmaker.matchmaker.service.SteamOpenIdService;
import com.saraylg.matchmaker.matchmaker.service.UsuariosService;
import com.saraylg.matchmaker.matchmaker.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/jams")
@RequiredArgsConstructor
public class JamController {

    // getAllJams, getJamByState, newJam, modifyJam, deleteJam, getJamById, getJamByName, getJamByMode
    private final JamService jamService;

    @GetMapping
    public List<JamOutputDTO> getAllJams() {
        return jamService.getAllJams();
    }


    @GetMapping("getByState/{state}")
    public List<JamOutputDTO> getJamByState(@PathVariable String state) {
        return jamService.getJamsByState(state);
    }


    @PostMapping("/create")
    public JamOutputDTO createJam(@Valid @RequestBody JamInputDTO jamInputDTO) {
        return jamService.newJam(jamInputDTO);
    }


    @PostMapping("/modify")
    public JamOutputDTO modifyJam(@Valid @RequestBody JamModifyDTO jamModifyDTO) {
        return jamService.modifyJam(jamModifyDTO);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteJam(@PathVariable String id) {
        return jamService.deleteJam(id);
    }

    @GetMapping("/getById/{id}")
    public JamOutputDTO getJamById(@PathVariable String id) {
        return jamService.getJamById(id);
    }

// Cambiar esto para que sea un "includes" y mejor por cuerpo
    /*@GetMapping("/getByTitle/{title}")
    public List<JamOutputDTO> getByTitle(@PathVariable String title) {
        return jamService.getJamsByTitle(title);
    }*/

    @GetMapping("/getByTitle")
    public List<JamOutputDTO> getByTitle(@RequestBody TitleInputDTO titleInputDto) {
        String title = titleInputDto.getTitle();
        return jamService.getJamsByTitle(title);
    }


    @GetMapping("/getByMode/{mode}")
    public List<JamOutputDTO> getByMode(@PathVariable String mode) {
        return jamService.getJamsByMode(mode);
    }

    // Añadir y quitar jugadores de una jam

    @PostMapping("/{jamId}/addPlayer")
    public ResponseEntity<JamOutputDTO> addPlayerToJam(@PathVariable String jamId, @RequestBody UsuarioInputDTO jugador) {
        return ResponseEntity.ok(jamService.addPlayerToJam(jamId, jugador));
    }

    @DeleteMapping("/{jamId}/removePlayer/{steamId}")
    public ResponseEntity<JamOutputDTO> removePlayerFromJam(@PathVariable String jamId, @PathVariable String steamId) {
        return ResponseEntity.ok(jamService.removePlayerFromJam(jamId, steamId));
    }



}
