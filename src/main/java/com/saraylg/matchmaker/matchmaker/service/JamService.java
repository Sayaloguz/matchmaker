package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;
import com.saraylg.matchmaker.matchmaker.repository.JamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JamService {

    private final JamRepository jamsRepository;
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

    // Obtener jams que ha hecho un usuario y en las que participa

    @GetMapping("/byCreator/{id}")
    public List<JamOutputDTO> getJamsByCreator(String id) {
        return jamsRepository.getJamsByCreator(id);
    }

    @GetMapping("/byUser/{id}")
    public List<JamOutputDTO> getJamsByUser(String id) {
        return jamsRepository.getJamsByUser(id);
    }

}
