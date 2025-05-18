package com.saraylg.matchmaker.matchmaker.mongo;

import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SteamAppMongoRepository
        extends MongoRepository<SteamAppEntity, Long> { }