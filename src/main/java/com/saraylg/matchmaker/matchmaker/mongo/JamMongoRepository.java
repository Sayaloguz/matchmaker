package com.saraylg.matchmaker.matchmaker.mongo;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface JamMongoRepository extends MongoRepository<JamEntity, String> {

    List<JamEntity> findByCreatedBy_SteamId(String steamId);

    List<JamEntity> findByPlayers_SteamId(String steamId);

}