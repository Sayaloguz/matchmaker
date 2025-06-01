package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.exceptions.InvalidJamOperationException;
import com.saraylg.matchmaker.matchmaker.exceptions.PlayerAlreadyJoinedException;
import com.saraylg.matchmaker.matchmaker.exceptions.JamNotFoundException;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;

import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.repository.mongo.JamMongoRepository;
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


    public JamOutputDTO newJam(JamInputDTO jamInputDTO) {
        JamEntity jam = jamMapper.jamInputDtoToJam(jamInputDTO);

        jamMongoRepository.save(jam);

        return jamMapper.jamToOutputDto(jam);
    }

    public List<JamOutputDTO> getAllJams() {
        List<JamEntity> jams = jamMongoRepository.findAll();

        List<JamOutputDTO> jamsDTO = new ArrayList<>();
        for (JamEntity jam : jams) {
            updateJamStateIfNeeded(jam);
            jamsDTO.add(jamMapper.jamToOutputDto(jam));
        }
        return jamsDTO;
    }

    public Optional<JamEntity> getJamById(String id) {
        Optional<JamEntity> jamOpt = jamMongoRepository.findById(id);
        jamOpt.ifPresent(this::updateJamStateIfNeeded);
        return jamOpt;

        //return jamMongoRepository.findById(id);
    }

    // No actualizamos el estado aquí
    public Optional<JamEntity> getJamsByTitle(String title) {
        return jamMongoRepository.findAll().stream()
                .filter(jam -> jam.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst();
    }

    public List<JamEntity> getJamByState(String state) {
        return jamMongoRepository.findAll().stream()
                .filter(jam -> String.valueOf(jam.getState()).equalsIgnoreCase(state))
                .peek(this::updateJamStateIfNeeded)
                .toList();
    }

    public List<JamEntity> getJamByMode(String gameMode) {
        return jamMongoRepository.findAll().stream()
                .filter(jam -> String.valueOf(jam.getGameMode()).equalsIgnoreCase(gameMode))
                .peek(this::updateJamStateIfNeeded)
                .toList();
    }

    public JamOutputDTO modifyJam(JamModifyDTO jamModifyDTO) {
        JamEntity existing = jamMongoRepository.findById(jamModifyDTO.getId())
                .orElseThrow(() -> new RuntimeException("Jam no encontrada"));

        if(jamModifyDTO.getTitle() != null && !jamModifyDTO.getTitle().isEmpty()) {
            existing.setTitle(jamModifyDTO.getTitle());
        }
        if (jamModifyDTO.getDescription() != null && !jamModifyDTO.getDescription().isEmpty()) {
            existing.setDescription(jamModifyDTO.getDescription());
        }
        if (jamModifyDTO.getJamDate() != null && !jamModifyDTO.getJamDate().isEmpty()) {
            existing.setJamDate(jamModifyDTO.getJamDate());
        }
        if (jamModifyDTO.getJamTime() != null && !jamModifyDTO.getJamTime().isEmpty()) {
            existing.setJamTime(jamModifyDTO.getJamTime());
        }
        if (jamModifyDTO.getMaxPlayers() != null) {
            existing.setMaxPlayers(jamModifyDTO.getMaxPlayers());
        }
        if (jamModifyDTO.getVoiceMode() != null) {
            existing.setVoiceMode(jamModifyDTO.getVoiceMode());
        }
        if (jamModifyDTO.getLanguage() != null) {
            existing.setLanguage(jamModifyDTO.getLanguage());
        }
        if (jamModifyDTO.getDuration() != null && !jamModifyDTO.getDuration().isEmpty()) {
            existing.setDuration(jamModifyDTO.getDuration());
        }

        return jamMapper.jamToOutputDto(jamMongoRepository.save(existing));
    }


    public String deleteJam(String id) {
        jamMongoRepository.deleteById(id);
        return "Jam eliminada con éxito";
    }

    // Añadir y quitar jugadores de una jam

    public JamOutputDTO addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        JamEntity jam = jamMongoRepository.findById(jamId)
                .orElseThrow(() -> new JamNotFoundException("Jam no encontrada"));

        // Convertir el DTO a entidad
        UsuarioEntity jugador = usuarioMapper.dtoToUsuariosEntity(jugadorDTO);

        // Evitar duplicados
        boolean alreadyJoined = jam.getPlayers() != null &&
                jam.getPlayers().stream().anyMatch(p -> p.getSteamId().equals(jugador.getSteamId()));

        if (alreadyJoined) {
            throw new PlayerAlreadyJoinedException("El jugador ya está en la jam");
        }

        if (jam.getPlayers() == null) jam.setPlayers(new ArrayList<>());
        jam.getPlayers().add(jugador);
        jamMongoRepository.save(jam);


        return jamMapper.jamToOutputDto(jam);
    }


    public JamOutputDTO removePlayerFromJam(String jamId, String steamIdToRemove) {
        JamEntity jam = jamMongoRepository.findById(jamId)
                .orElseThrow(() -> new RuntimeException("Jam no encontrada"));

        if (jam.getCreatedBy() != null && steamIdToRemove.equals(jam.getCreatedBy().getSteamId())) {
            throw new InvalidJamOperationException("No puedes eliminar al creador de la jam");
        }

        if (jam.getPlayers() != null) {
            jam.getPlayers().removeIf(p -> p.getSteamId().equals(steamIdToRemove));
        }

        updateJamStateIfNeeded(jam); // <<<<< Puede pasar de FULL a OPEN
        JamEntity updated = jamMongoRepository.save(jam);
        return jamMapper.jamToOutputDto(updated);
    }

    // Obtener jams que ha hecho un usuario y en las que participa

    public List<JamOutputDTO> getJamsByCreator(String steamId) {
        return jamMongoRepository
                .findByCreatedBy_SteamId(steamId).stream()
                .peek(this::updateJamStateIfNeeded)
                .map(jamMapper::jamToOutputDto)
                .toList();
    }

    public List<JamOutputDTO> getJamsByUser(String steamId) {
        return jamMongoRepository
                .findByPlayers_SteamId(steamId).stream()
                .peek(this::updateJamStateIfNeeded)
                .map(jamMapper::jamToOutputDto)
                .toList();
    }



}
