package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.mongo.UsuarioMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class UsuariosRepository {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioMongoRepository usuarioMongoRepository;

    public String saveUser(UsuarioInputDTO userDto) {

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
