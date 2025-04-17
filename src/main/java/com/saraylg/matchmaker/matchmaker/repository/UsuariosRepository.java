package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.mongo.UsuarioMongoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class UsuariosRepository {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioMongoRepository usuarioMongoRepository;

    public String saveUser(UsuarioDTO userDto) {

        UsuarioEntity usuarioNuevo = usuarioMapper.dtoToUsuariosEntity(userDto);

        usuarioMongoRepository.save(usuarioNuevo);

        return "Usuario guardado con éxito";
    }

    /*
    public Optional<UsuarioDTO> getUser(String steamId) {
        return usuarioMongoRepository.findById(steamId)
                .map(usuarioMapper::usuariosEntityToDto);
    }
    */


}
