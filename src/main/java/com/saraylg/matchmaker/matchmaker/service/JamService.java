package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;
import com.saraylg.matchmaker.matchmaker.repository.JamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JamService {

    private final JamsRepository jamsRepository;
    private final JamMapper jamMapper;

    public List<JamOutputDTO> getAllJams() {
        return jamsRepository.getAllJams();
    }

    public List<JamOutputDTO> getJamsByState(String state) {
        return jamsRepository.getJamByState(state).stream()
                .map(jamMapper::jamToOutputDto)
                .toList();
    }

    public JamOutputDTO newJam(JamInputDTO jamInputDTO) {
        return jamsRepository.newJam(jamInputDTO);
    }

    public JamOutputDTO modifyJam(JamModifyDTO jamModifyDTO) {
        return jamsRepository.modifyJam(jamModifyDTO);
    }


    public String deleteJam(String id) {
        return jamsRepository.deleteJam(id);
    }

    public JamOutputDTO getJamById(String id) {
        return jamsRepository.getJamById(id)
                .map(jamMapper::jamToOutputDto)
                .orElseThrow(() -> new RuntimeException("Jam no encontrada"));
    }

    public List<JamOutputDTO> getJamsByTitle(String title) {
        return jamsRepository.getJamsByTitle(title)
                .map(jamMapper::jamToOutputDto)
                .stream().toList();
    }

    public List<JamOutputDTO> getJamsByMode(String mode) {
        return jamsRepository.getJamByMode(mode).stream()
                .map(jamMapper::jamToOutputDto)
                .toList();
    }

    // Añadir y quitar jugadores de una jam
    public JamOutputDTO addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        return jamsRepository.addPlayerToJam(jamId, jugadorDTO);
    }

    public JamOutputDTO removePlayerFromJam(String jamId, String steamIdToRemove) {
        return jamsRepository.removePlayerFromJam(jamId, steamIdToRemove);
    }


}
