package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.service.SteamAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/steam/apps")
@RequiredArgsConstructor
public class SteamAppController {

    private final SteamAppService service;

    /** Fuerza una sincronización manual */
    @PostMapping("/sync")
    public ResponseEntity<String> sync() {
        long count = service.syncCatalog();
        return ResponseEntity.ok("Catálogo actualizado: " + count + " juegos");
    }

    /** Devuelve todo el catálogo desde Mongo */
    @GetMapping
    public List<SteamAppEntity> list() {
        return service.findAll();
    }
}
