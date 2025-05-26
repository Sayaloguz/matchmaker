package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.*;
import com.saraylg.matchmaker.matchmaker.service.JamService;
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

    private final JamService jamService;

    @GetMapping("/")
    public List<JamOutputDTO> getAllJams() {

        System.out.println("HOLA");
        return jamService.getAllJams();
    }


    @GetMapping("/getByState/{state}")
    public List<JamOutputDTO> getJamByState(@PathVariable String state) {
        return jamService.getJamsByState(state);
    }


    @GetMapping("/getById/{id}")
    public JamOutputDTO getJamById(@PathVariable String id) {
        return jamService.getJamById(id);
    }


    @GetMapping("/getByTitle")
    public List<JamOutputDTO> getByTitle(@RequestBody TitleInputDTO titleInputDto) {
        String title = titleInputDto.getTitle();
        return jamService.getJamsByTitle(title);
    }

/*
    @GetMapping("/getByMode/{mode}")
    public List<JamOutputDTO> getByMode(@PathVariable String mode) {
        return jamService.getJamsByMode(mode);
    }
    */
    @GetMapping("/byCreator/{id}")
    public List<JamOutputDTO> getByCreator(@PathVariable String id) {
        return jamService.getJamsByCreator(id);
    }

    @GetMapping("/byUser/{id}")
    public List<JamOutputDTO> getByUser(@PathVariable String id) {
        return jamService.getJamsByUser(id);
    }

    @PostMapping("/save")
    public JamOutputDTO createJam(@Valid @RequestBody JamInputDTO jamInputDTO) {
        System.out.println("HOLA");
        return jamService.newJam(jamInputDTO);
    }


    @PostMapping("/modify")
    public JamOutputDTO modifyJam(@Valid @RequestBody JamModifyDTO jamModifyDTO) {
        return jamService.modifyJam(jamModifyDTO);
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

    // http://localhost:8080/jams/68319f0e173c3d203e799902/removePlayer/76561198847318451


    @DeleteMapping("/delete/{id}")
    public String deleteJam(@PathVariable String id) {
        return jamService.deleteJam(id);
    }


// Cambiar esto para que sea un "includes" y mejor por cuerpo
    /*@GetMapping("/getByTitle/{title}")
    public List<JamOutputDTO> getByTitle(@PathVariable String title) {
        return jamService.getJamsByTitle(title);
    }*/







}
