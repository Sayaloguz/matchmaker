package com.saraylg.matchmaker.matchmaker.repository.mongo;

import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SteamAppMongoRepository
        extends MongoRepository<SteamAppEntity, Long> {
    Optional<SteamAppEntity> findByAppid(Long appid);

    List<SteamAppEntity> findByNameRegexIgnoreCase(String name);

}