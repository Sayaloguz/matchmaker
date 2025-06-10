package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.exceptions.UserNotFoundException;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.repository.mongo.UsuarioMongoRepository;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio que se encarga de manejar las operaciones de base de datos
 * relacionadas con los usuarios, utilizando MongoDB.
 * Este repositorio actúa como puente entre el servicio y el repositorio de Mongo.
 */
@RequiredArgsConstructor
@Repository
public class UsuarioRepository {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioMongoRepository usuarioMongoRepository;

    /**
     * Guarda un nuevo usuario en la base de datos a partir de un usuario de entrada.
     *
     * @param usuario Genérico de usuario con los datos del nuevo usuario.
     * @return Mensaje de éxito.
     */
    public GenericUsuario saveUser(GenericUsuario usuario) {
        UsuarioEntity usuarioNuevo = usuarioMapper.genericToEntity(usuario);
        usuarioMongoRepository.save(usuarioNuevo);
        return usuarioMapper.entityToGeneric(usuarioNuevo);
    }


    /**
     * Obtiene todos los usuarios guardados en la base de datos.
     *
     * @return Lista de entidades de usuarios.
     */
    public List<GenericUsuario> findAllUsers() {

        return usuarioMapper.entityListToGeneric(usuarioMongoRepository.findAll());
    }

    /**
     * Busca un usuario por su Steam ID.
     *
     * @param steamId ID de Steam del usuario.
     * @return Un Optional con el usuario si existe.
     */
    public Optional<GenericUsuario> findUserById(String steamId) {
        return usuarioMapper.entityOptionalToGeneric(usuarioMongoRepository.findById(steamId));
    }

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     *
     * @param steamId    ID del usuario a actualizar.
     * @param updatedUser Usuario genérico con los nuevos datos.
     * @return Usuario genérico con los datos actualizados.
     */

    public GenericUsuario updateUserIfChanged(String steamId, GenericUsuario updatedUser) {
        UsuarioEntity existing = usuarioMapper.genericOptionalToEntity(findUserById(steamId))
                .orElseThrow(() -> new UserNotFoundException(steamId));

        boolean hayCambios = false;

        if (!updatedUser.getName().equals(existing.getName())) {
            existing.setName(updatedUser.getName());
            hayCambios = true;
        }

        if (!updatedUser.getAvatar().equals(existing.getAvatar())) {
            existing.setAvatar(updatedUser.getAvatar());
            hayCambios = true;
        }

        if (!updatedUser.getProfileUrl().equals(existing.getProfileUrl())) {
            existing.setProfileUrl(updatedUser.getProfileUrl());
            hayCambios = true;
        }

        if (!updatedUser.getTimeCreated().equals(existing.getTimeCreated())) {
            existing.setTimeCreated(updatedUser.getTimeCreated());
            hayCambios = true;
        }

        if (hayCambios) {
            return usuarioMapper.entityToGeneric(usuarioMongoRepository.save(existing));
        } else {
            return usuarioMapper.entityToGeneric(existing);
        }
    }

    /**
     * Borra un usuario de la base de datos por su Steam ID.
     *
     * @param steamId    ID del usuario a borrar.
     * @return Usuario genérico borrado.
     */
    public GenericUsuario deleteUser(String steamId) {
        GenericUsuario deletedUser = findUserById(steamId)
                .orElseThrow(() -> new UserNotFoundException(steamId));

        usuarioMongoRepository.deleteById(steamId);

        return deletedUser;
    }
}
