package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.input.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.JamMapper;
import com.saraylg.matchmaker.matchmaker.repository.JamRepository;
import com.saraylg.matchmaker.matchmaker.service.generics.GenericJam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JamService {

    private final JamRepository jamsRepository;


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


    public String deleteJam(String id) {
        return jamsRepository.deleteJam(id);
    }

    public GenericJam getJamById(String id) {
        return jamsRepository.getJamById(id)
                .orElseThrow(() -> new RuntimeException("Jam no encontrada"));
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
    public GenericJam addPlayerToJam(String jamId, UsuarioInputDTO jugadorDTO) {
        return jamsRepository.addPlayerToJam(jamId, jugadorDTO);
    }

    public GenericJam removePlayerFromJam(String jamId, String steamIdToRemove) {
        return jamsRepository.removePlayerFromJam(jamId, steamIdToRemove);
    }


    // Obtener jams que ha hecho un usuario y en las que participa

    @GetMapping("/byCreator/")
    public List<GenericJam> getJamsByCreator(String id) {
        return jamsRepository.getJamsByCreator(id);
    }

    @GetMapping("/byUser/")
    public List<GenericJam> getJamsByUser(String id) {
        return jamsRepository.getJamsByUser(id);
    }

}
