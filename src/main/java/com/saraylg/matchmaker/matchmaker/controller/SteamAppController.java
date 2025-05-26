package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.TitleInputDTO;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.repository.SteamAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class SteamAppController {

    private final SteamAppRepository repository;

    /** Devuelve todo el catálogo desde Mongo */
    @GetMapping("/")
    public List<SteamAppEntity> list() {
        return repository.findAll();
    }

    @GetMapping("/getById/{appid}")
    public ResponseEntity<SteamAppEntity> getByAppId(@PathVariable Long appid) {
        return repository.findByAppid(appid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/sync")
    public ResponseEntity<String> sync() {
        long count = repository.syncCatalog();
        return ResponseEntity.ok("Catálogo actualizado: " + count + " juegos");
    }

    // No se va a utilizar en principio, pero se deja por si acaso
    @GetMapping("/search")
    public List<SteamAppEntity> searchByName(@RequestParam TitleInputDTO titleDTO) {
        return repository.findByName(titleDTO.getTitle());
    }

}
