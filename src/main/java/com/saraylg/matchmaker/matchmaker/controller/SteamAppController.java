package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.TitleInputDTO;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.service.SteamAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class SteamAppController {

    private final SteamAppService service;

    /** Devuelve todo el catálogo desde Mongo */
    @GetMapping("/")
    public List<SteamAppEntity> list() {
        return service.findAll();
    }


    @GetMapping("/getById/{appid}")
    public ResponseEntity<SteamAppEntity> getByAppId(@PathVariable Long appid) {
        return service.findByAppid(appid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/sync")
    public ResponseEntity<String> sync() {
        long count = service.syncCatalog();
        return ResponseEntity.ok("Catálogo actualizado: " + count + " juegos");
    }


    // No se va a utilizar en principio, pero se deja por si acaso
    @GetMapping("/search")
    public List<SteamAppEntity> searchByName(@RequestParam TitleInputDTO titleDTO) {
        System.out.println(titleDTO);
        System.out.println(titleDTO.getTitle());
        return service.findByName(titleDTO.getTitle());
    }

}
