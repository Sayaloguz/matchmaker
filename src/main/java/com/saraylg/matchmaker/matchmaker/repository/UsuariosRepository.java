package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.mongo.UsuarioMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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

    public List<UsuarioEntity> findAllUsers() {
        return usuarioMongoRepository.findAll();
    }

    public Optional<UsuarioEntity> findUserById(String steamId) {
        return usuarioMongoRepository.findById(steamId);
    }

    public UsuarioOutputDTO updateUser(String steamId, UsuarioInputDTO updatedDto) {
        UsuarioEntity existing = usuarioMongoRepository.findById(steamId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existing.setName(updatedDto.getName());
        existing.setAvatar(updatedDto.getAvatar());
        existing.setProfileUrl(updatedDto.getProfileUrl());
        existing.setTimeCreated(updatedDto.getTimeCreated());

        return usuarioMapper.entityToOutputDto(usuarioMongoRepository.save(existing));
    }

    public String deleteUser(String steamId) {
        usuarioMongoRepository.deleteById(steamId);

        return "Usuario eliminado con éxito";
    }


}
