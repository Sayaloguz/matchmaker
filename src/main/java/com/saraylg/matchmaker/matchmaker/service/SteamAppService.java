package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.output.SteamAppOutputDTO;
import com.saraylg.matchmaker.matchmaker.repository.SteamAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SteamAppService {

    private final SteamAppRepository repository;

    public long syncCatalog() {
        return repository.syncCatalog();
    }

    public List<SteamAppOutputDTO> findAll() {
        return repository.findAll();
    }

    public Optional<SteamAppOutputDTO> findByAppid(Long appid) {
        return repository.findByAppid(appid);
    }

    public List<SteamAppOutputDTO> findByName(String name) {
        return repository.findByName(name);
    }
}
