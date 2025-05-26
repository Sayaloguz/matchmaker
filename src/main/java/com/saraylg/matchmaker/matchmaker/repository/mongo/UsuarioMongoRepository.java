package com.saraylg.matchmaker.matchmaker.repository.mongo;

import com.saraylg.matchmaker.matchmaker.model.SteamAppEntity;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioMongoRepository extends MongoRepository<UsuarioEntity, String> {

}
