package com.saraylg.matchmaker.matchmaker.mongo;
import com.saraylg.matchmaker.matchmaker.model.JamsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JamsMongoRepository extends MongoRepository<JamsEntity, String> {

}
