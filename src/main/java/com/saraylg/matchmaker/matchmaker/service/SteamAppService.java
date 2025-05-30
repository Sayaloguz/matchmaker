package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.SteamAppOutputDto;
import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
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

    public List<SteamAppOutputDto> findAll() {
        return repository.findAll();
    }

    public Optional<SteamAppOutputDto> findByAppid(Long appid) {
        return repository.findByAppid(appid);
    }

    public List<SteamAppOutputDto> findByName(String name) {
        return repository.findByName(name);
    }
}
