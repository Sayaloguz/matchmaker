package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.exceptions.*;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;

import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.repository.mongo.JamMongoRepository;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericJam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JamRepository {

    private final JamMapper jamMapper;
    private final JamMongoRepository jamMongoRepository;
    private final UsuarioMapper usuarioMapper;


    public void updateJamStateIfNeeded(JamEntity jam) {

        if (jam.getState() == JamState.FINISHED) return;

        LocalDateTime now = LocalDateTime.now();
        LocalDate jamDate = LocalDate.parse(jam.getJamDate());
        LocalTime jamTime = LocalTime.parse(jam.getJamTime());
        LocalDateTime jamDateTime = LocalDateTime.of(jamDate, jamTime);

        if (jamDateTime.isBefore(now)) {
            jam.setState(JamState.FINISHED);
        }

    }


    public GenericJam saveJam(GenericJam genericJam) {

        JamEntity jam = jamMapper.genericToEntity(genericJam);

        jamMongoRepository.save(jam);

        return jamMapper.entityToGeneric(jam);

    }


    public List<GenericJam> getAllJams() {

        List<JamEntity> jams = jamMongoRepository.findAll();

        List<GenericJam> everyJam = new ArrayList<>();
        for (JamEntity jam : jams) {
            updateJamStateIfNeeded(jam);
            everyJam.add(jamMapper.entityToGeneric(jam));
        }
        return everyJam;

    }


    public Optional<GenericJam> getJamById(String id) {

        Optional<JamEntity> jamOpt = jamMongoRepository.findById(id);
        jamOpt.ifPresent(this::updateJamStateIfNeeded);
        return jamMapper.entityOptionalToGeneric(jamOpt);

    }


    public List<GenericJam> getJamsByTitle(String title) {
        return jamMapper.entityListToGeneric(
                jamMongoRepository.findByTitleContainingIgnoreCase(title)
        );
    }


    public List<GenericJam> getOpenJamsByTitle(String title) {
        return jamMapper.entityListToGeneric(
                jamMongoRepository.findByTitleContainingIgnoreCaseAndState(title, JamState.OPEN)
        );
    }


    public List<GenericJam> getJamByState(String state) {
        return jamMapper.entityListToGeneric(jamMongoRepository.findAll().stream()
                .filter(jam -> String.valueOf(jam.getState()).equalsIgnoreCase(state))
                .peek(this::updateJamStateIfNeeded)
                .toList());
    }

    public List<GenericJam> getJamByMode(String gameMode) {
        return jamMapper.entityListToGeneric(jamMongoRepository.findAll().stream()
                .filter(jam -> String.valueOf(jam.getGameMode()).equalsIgnoreCase(gameMode))
                .peek(this::updateJamStateIfNeeded)
                .toList());
    }

    public GenericJam modifyJam(GenericJam genericJam) {
        JamEntity existing = jamMongoRepository.findById(genericJam.getId())
                .orElseThrow(() -> new JamNotFoundException("Jam no encontrada"));

        if (genericJam.getMaxPlayers() != null &&
                existing.getPlayers() != null &&
                genericJam.getMaxPlayers() < existing.getPlayers().size()) {
            throw new InvalidJamOperationException("No puedes establecer maxPlayers por debajo del número actual de jugadores.");
        }

        if (genericJam.getTitle() != null && !genericJam.getTitle().isEmpty()) {
            existing.setTitle(genericJam.getTitle());
        }

        if (genericJam.getDescription() != null && !genericJam.getDescription().isEmpty()) {
            existing.setDescription(genericJam.getDescription());
        }

        if (genericJam.getJamDate() != null && !genericJam.getJamDate().isEmpty()) {
            existing.setJamDate(genericJam.getJamDate());
        }

        if (genericJam.getJamTime() != null && !genericJam.getJamTime().isEmpty()) {
            existing.setJamTime(genericJam.getJamTime());
        }

        if (genericJam.getMaxPlayers() != null) {
            existing.setMaxPlayers(genericJam.getMaxPlayers());
        }

        if (genericJam.getVoiceMode() != null) {
            existing.setVoiceMode(genericJam.getVoiceMode());
        }

        if (genericJam.getLanguage() != null) {
            existing.setLanguage(genericJam.getLanguage());
        }

        if (genericJam.getDuration() != null && !genericJam.getDuration().isEmpty()) {
            existing.setDuration(genericJam.getDuration());
        }

        if (genericJam.getGameMode() != null) {
            existing.setGameMode(genericJam.getGameMode());
        }

        int currentPlayers = existing.getPlayers() != null ? existing.getPlayers().size() : 0;
        if (currentPlayers >= existing.getMaxPlayers()) {
            existing.setState(JamState.FULL);
        } else {
            existing.setState(JamState.OPEN);
        }

        return jamMapper.entityToGeneric(jamMongoRepository.save(existing));

    }


    public GenericJam deleteJam(String id) {
        List<GenericJam> deletedJam = jamMapper.entityListToGeneric(jamMongoRepository.getJamEntityById(id));

        if (deletedJam.isEmpty()) {
            throw new JamNotFoundException("Jam no encontrada");
        }

        jamMongoRepository.deleteById(id);
        return deletedJam.get(0);
    }

    public GenericJam addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        JamEntity jam = jamMongoRepository.findById(jamId)
                .orElseThrow(() -> new JamNotFoundException("Jam no encontrada"));

        updateJamStateIfNeeded(jam);


        // Comprobar si el jugador ya está en la jam
        UsuarioEntity jugador = usuarioMapper.inputToEntity(jugadorDTO);
        boolean alreadyJoined = jam.getPlayers() != null &&
                jam.getPlayers().stream().anyMatch(p -> p.getSteamId().equals(jugador.getSteamId()));
        if (alreadyJoined) {
            throw new PlayerAlreadyJoinedException("El jugador ya está en la jam");
        }

        // Comprobar si ya está llena
        int currentPlayers = jam.getPlayers() != null ? jam.getPlayers().size() : 0;
        if (currentPlayers >= jam.getMaxPlayers()) {
            throw new JamAlreadyFullException("La jam ya está llena");
        }

        // Añadir jugador
        if (jam.getPlayers() == null) jam.setPlayers(new ArrayList<>());
        jam.getPlayers().add(jugador);

        // Actualizar estado si ahora está llena
        if (jam.getPlayers().size() >= jam.getMaxPlayers()) {
            jam.setState(JamState.FULL);
        }

        jamMongoRepository.save(jam); // Guardar después de todos los cambios

        return jamMapper.entityToGeneric(jam);
    }



    public GenericJam removePlayerFromJam(String jamId, String steamIdToRemove) {
        JamEntity jam = jamMongoRepository.findById(jamId)
                .orElseThrow(() -> new JamNotFoundException("Jam no encontrada"));

        if (jam.getCreatedBy() != null && steamIdToRemove.equals(jam.getCreatedBy().getSteamId())) {
            throw new InvalidJamOperationException("No puedes eliminar al creador de la jam");
        }

        boolean removed = false;
        if (jam.getPlayers() != null) {
            removed = jam.getPlayers().removeIf(p -> p.getSteamId().equals(steamIdToRemove));
        }

        if (!removed) {
            throw new PlayerNotFoundInJamException("El jugador con SteamID " + steamIdToRemove + " no se encuentra en la jam");
        }

        // Actualizar estado si estaba llena y ahora hay hueco
        if (jam.getState() == JamState.FULL &&
                jam.getPlayers() != null &&
                jam.getPlayers().size() < jam.getMaxPlayers()) {
            jam.setState(JamState.OPEN);
        }

        updateJamStateIfNeeded(jam); // Para marcar como FINISHED si ya pasó la fecha

        JamEntity updated = jamMongoRepository.save(jam);
        return jamMapper.entityToGeneric(updated);

    }



    public List<GenericJam> getJamsByCreator(String steamId) {
        return jamMapper.entityListToGeneric(jamMongoRepository
                .findByCreatedBy_SteamId(steamId).stream()
                .peek(this::updateJamStateIfNeeded)
                .toList());
    }


    public List<GenericJam> getJamsByUser(String steamId) {
        return jamMapper.entityListToGeneric(jamMongoRepository
                .findByPlayers_SteamId(steamId).stream()
                .peek(this::updateJamStateIfNeeded)
                .toList());

    }


}
