package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.SteamAppOutputDto;
import com.saraylg.matchmaker.matchmaker.dto.TitleInputDTO;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.service.SteamAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Tag(name = "Juegos de Steam", description = "Endpoints para gestionar y consultar juegos del catálogo de Steam")
public class SteamAppController {

    private final SteamAppService service;

    @Operation(summary = "Obtener todos los juegos del catálogo")
    @GetMapping("/")
    public List<SteamAppOutputDto> list() {
        return service.findAll();
    }

    @Operation(summary = "Obtener juego por appid")
    @GetMapping("/byId/{appid}")
    public ResponseEntity<SteamAppOutputDto> getByAppId(@PathVariable Long appid) {
        return service.findByAppid(appid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Sincronizar catálogo desde Steam")
    @PostMapping("/sync")
    public ResponseEntity<String> sync() {
        long count = service.syncCatalog();
        return ResponseEntity.ok("Catálogo actualizado: " + count + " juegos");
    }

    @Operation(summary = "Buscar juegos por título")
    @GetMapping("/search")
    public List<SteamAppOutputDto> searchByName(@RequestParam String title) {
        return service.findByName(title);
    }
}
