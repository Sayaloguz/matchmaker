package com.saraylg.matchmaker.matchmaker.mongo;

import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMongoRepository extends MongoRepository<UsuarioEntity, String> {
}
