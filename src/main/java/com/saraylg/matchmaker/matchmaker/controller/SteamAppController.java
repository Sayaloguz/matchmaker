package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.service.SteamAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steam/apps")
@RequiredArgsConstructor
public class SteamAppController {

    private final SteamAppService service;

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


    // EP de búsqueda
    @GetMapping("/by-id/{appid}")
    public ResponseEntity<SteamAppEntity> getByAppId(@PathVariable Long appid) {
        return service.findByAppid(appid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // No se va a utilizar en principio, pero se deja por si acaso
    @GetMapping("/search")
    public List<SteamAppEntity> searchByName(@RequestParam String name) {
        return service.findByName(name);
    }

}
