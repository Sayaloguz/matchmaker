package com.saraylg.matchmaker.matchmaker.repository.mongo;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface JamMongoRepository extends MongoRepository<JamEntity, String> {

    List<JamEntity> findByCreatedBy_SteamId(String steamId);

    List<JamEntity> findByPlayers_SteamId(String steamId);

    List<JamEntity> findByStateIn(List<JamState> states);
    List<JamEntity> findByTitleContainingIgnoreCase(String title);

    // Buscar por título + estado
    List<JamEntity> findByTitleContainingIgnoreCaseAndState(String title, JamState state);


    List<JamEntity> getJamEntityById(String id);
}