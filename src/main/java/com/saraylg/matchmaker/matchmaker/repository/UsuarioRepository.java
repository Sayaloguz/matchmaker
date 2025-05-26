package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.repository.mongo.UsuarioMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio que se encarga de manejar las operaciones de base de datos
 * relacionadas con los usuarios, utilizando MongoDB.
 * Este repositorio actúa como puente entre el servicio y el repositorio de Mongo.
 */
@RequiredArgsConstructor // Genera un constructor con los campos finales (Mapper y MongoRepository)
@Repository // Marca esta clase como componente Spring para inyección de dependencias
public class UsuarioRepository {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioMongoRepository usuarioMongoRepository;

    /**
     * Guarda un nuevo usuario en la base de datos a partir de un DTO de entrada.
     * @param userDto DTO con los datos del usuario.
     * @return Mensaje de éxito.
     */
    public String saveUser(UsuarioInputDTO userDto) {
        UsuarioEntity usuarioNuevo = usuarioMapper.dtoToUsuariosEntity(userDto);
        usuarioMongoRepository.save(usuarioNuevo);
        return "Usuario guardado con éxito";
    }

    /*
    // Este método está comentado. Serviría para devolver un usuario en formato DTO
    public Optional<UsuarioDTO> getUser(String steamId) {
        return usuarioMongoRepository.findById(steamId)
                .map(usuarioMapper::usuariosEntityToDto);
    }
    */

    /**
     * Obtiene todos los usuarios guardados en la base de datos.
     * @return Lista de entidades de usuarios.
     */
    public List<UsuarioEntity> findAllUsers() {
        return usuarioMongoRepository.findAll();
    }

    /**
     * Busca un usuario por su Steam ID.
     * @param steamId ID de Steam del usuario.
     * @return Un Optional con el usuario si existe.
     */
    public Optional<UsuarioEntity> findUserById(String steamId) {
        return usuarioMongoRepository.findById(steamId);
    }

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     * @param steamId ID del usuario a actualizar.
     * @param updatedDto DTO con los nuevos datos.
     * @return DTO de salida con los datos actualizados.
     */
    public UsuarioOutputDTO updateUser(String steamId, UsuarioInputDTO updatedDto) {
        UsuarioEntity existing = usuarioMongoRepository.findById(steamId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Se actualizan los campos que pueden modificarse
        existing.setName(updatedDto.getName());
        existing.setAvatar(updatedDto.getAvatar());
        existing.setProfileUrl(updatedDto.getProfileUrl());
        existing.setTimeCreated(updatedDto.getTimeCreated());

        // Se guarda el usuario actualizado y se transforma a DTO de salida
        return usuarioMapper.entityToOutputDto(usuarioMongoRepository.save(existing));
    }

    /**
     * Elimina un usuario de la base de datos según su Steam ID.
     * @param steamId ID del usuario a eliminar.
     * @return Mensaje de éxito.
     */
    public String deleteUser(String steamId) {
        usuarioMongoRepository.deleteById(steamId);
        return "Usuario eliminado con éxito";
    }
}
