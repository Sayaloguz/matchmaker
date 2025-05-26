package com.saraylg.matchmaker.matchmaker;

import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.mongo.JamMongoRepository;
import com.saraylg.matchmaker.matchmaker.repository.JamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JamStateScheduler {

    private final JamRepository jamRepository;
    private final JamMongoRepository jamMongoRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // Ejecuta cada 6 horas
    @Scheduled(fixedRate = 21600000) // Cada 6 horas
    public void updateJamStates() {
        List<JamEntity> jams = jamMongoRepository.findByStateIn(List.of(JamState.OPEN, JamState.FULL));

        for (JamEntity jam : jams) {
            JamState originalState = jam.getState();

            jamRepository.updateJamStateIfNeeded(jam); // Solo gestiona FINISHED

            if (jam.getState() != originalState) {
                jamMongoRepository.save(jam);
            }
        }
    }

}
