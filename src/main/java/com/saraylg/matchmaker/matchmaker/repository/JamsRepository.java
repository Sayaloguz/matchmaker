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
import com.saraylg.matchmaker.matchmaker.model.JamsEntity;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.mongo.JamsMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JamsRepository {

    private final JamMapper jamMapper;
    private final JamsMongoRepository jamMongoRepository;
    private final UsuarioMapper usuarioMapper;

    public JamOutputDTO newJam(JamInputDTO jamInputDTO) {
        JamsEntity jam = jamMapper.jamInputDtoToJam(jamInputDTO);

        jamMongoRepository.save(jam);

        return jamMapper.jamToOutputDto(jam);
    }

    public List<JamOutputDTO> getAllJams() {
        List<JamsEntity> jams = jamMongoRepository.findAll();

        List<JamOutputDTO> jamsDTO = new ArrayList<>();
        for (JamsEntity jam : jams) {
            jamsDTO.add(jamMapper.jamToOutputDto(jam));
        }
        return jamsDTO;
    }

    public Optional<JamsEntity> getJamById(String id) {

        return jamMongoRepository.findById(id);

    }

    public Optional<JamsEntity> getJamsByTitle(String title) {
        return jamMongoRepository.findAll().stream()
                .filter(jam -> jam.getTitle().toLowerCase().contains(title.toLowerCase()))
                .findFirst();
    }

    public List<JamsEntity> getJamByState(String state) {
        return jamMongoRepository.findAll().stream()
                .filter(jam -> String.valueOf(jam.getState()).equalsIgnoreCase(state))
                .toList();
    }

    public List<JamsEntity> getJamByMode(String gameMode) {
        return jamMongoRepository.findAll().stream()
                .filter(jam -> String.valueOf(jam.getGameMode()).equalsIgnoreCase(gameMode))
                .toList();
    }

    public JamOutputDTO modifyJam(JamModifyDTO jamModifyDTO) {
        JamsEntity existing = jamMongoRepository.findById(jamModifyDTO.getId())
                .orElseThrow(() -> new RuntimeException("Jam no encontrada"));

        existing.setTitle(jamModifyDTO.getTitle());
        existing.setGameMode(jamModifyDTO.getGameMode());
        existing.setJamDate(jamModifyDTO.getJamDate());
        existing.setDuration(jamModifyDTO.getDuration());

        return jamMapper.jamToOutputDto(jamMongoRepository.save(existing));
    }


    public String deleteJam(String id) {
        jamMongoRepository.deleteById(id);
        return "Jam eliminada con éxito";
    }

    // Añadir y quitar jugadores de una jam

    public JamOutputDTO addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        JamsEntity jam = jamMongoRepository.findById(jamId)
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
        JamsEntity jam = jamMongoRepository.findById(jamId)
                .orElseThrow(() -> new RuntimeException("Jam no encontrada"));

        if (jam.getCreatedBy() != null && steamIdToRemove.equals(jam.getCreatedBy().getSteamId())) {
            throw new InvalidJamOperationException("No puedes eliminar al creador de la jam");
        }

        if (jam.getPlayers() != null) {
            jam.getPlayers().removeIf(p -> p.getSteamId().equals(steamIdToRemove));
        }

        JamsEntity updated = jamMongoRepository.save(jam);
        return jamMapper.jamToOutputDto(updated);
    }


}
