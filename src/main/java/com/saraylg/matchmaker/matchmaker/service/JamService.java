package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.input.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.exceptions.JamNotFoundException;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.repository.JamRepository;
import com.saraylg.matchmaker.matchmaker.repository.mongo.JamMongoRepository;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericJam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JamService {

    private final JamRepository jamsRepository;
    private final JamMongoRepository jamMongoRepository;


    public List<GenericJam> getAllJams() {
        return jamsRepository.getAllJams();
    }


    public List<GenericJam> getJamsByState(String state) {
        return jamsRepository.getJamByState(state).stream()
                .toList();
    }

    public GenericJam newJam(JamInputDTO jamInputDTO) {
        return jamsRepository.newJam(jamInputDTO);
    }

    public GenericJam modifyJam(JamModifyDTO jamModifyDTO) {
        return jamsRepository.modifyJam(jamModifyDTO);
    }


    public GenericJam deleteJam(String id) {
        return jamsRepository.deleteJam(id);
    }

    public GenericJam getJamById(String id) {
        return jamsRepository.getJamById(id)
                .orElseThrow(() -> new JamNotFoundException("Jam no encontrada"));
    }


    public List<GenericJam> getJamsByTitle(String title) {
        return jamsRepository.getJamsByTitle(title).stream()
                .toList();
    }

    public List<GenericJam> getOpenJamsByTitle(String title) {
        return jamsRepository.getOpenJamsByTitle(title).stream()
                .toList();
    }

    public List<GenericJam> getJamsByMode(String mode) {
        return jamsRepository.getJamByMode(mode).stream()
                .toList();
    }

    // Añadir y quitar jugadores de una jam
    /*public GenericJam addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        return jamsRepository.addPlayerToJam(jamId, jugadorDTO);
    }*/
    public GenericJam addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        return jamsRepository.addPlayerToJam(jamId, jugadorDTO);
    }

    public GenericJam removePlayerFromJam(String jamId, String steamId) {
        return jamsRepository.removePlayerFromJam(jamId, steamId);
    }

    public void deleteJamsCreatedByUser(String steamId) {
        // Obtener todas las jams creadas por el usuario
        List<JamEntity> jamsCreadas = jamMongoRepository.findByCreatedBy_SteamId(steamId);

        // Eliminar cada jam
        for (JamEntity jam : jamsCreadas) {
            jamsRepository.deleteJam(jam.getId());
        }
    }

    public void removeUserFromAllJams(String steamId) {
        // Obtener todas las jams en las que participa el usuario
        List<JamEntity> jamsParticipadas = jamMongoRepository.findByPlayers_SteamId(steamId)
                .stream()
                .filter(jamEntity -> jamEntity.getState() != JamState.FINISHED)
                .toList();

        // Eliminar al usuario de cada jam
        for (JamEntity jam : jamsParticipadas) {
            jamsRepository.removePlayerFromJam(jam.getId(), steamId);
        }
    }


    // Obtener jams que ha hecho un usuario y en las que participa
    public List<GenericJam> getJamsByCreator(String id) {
        return jamsRepository.getJamsByCreator(id);
    }

    public List<GenericJam> getJamsByUser(String id) {
        return jamsRepository.getJamsByUser(id);
    }


}
