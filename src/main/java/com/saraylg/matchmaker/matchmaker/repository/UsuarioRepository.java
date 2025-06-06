package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.mapper.UsuarioMapper;
import com.saraylg.matchmaker.matchmaker.model.InvitationEntity;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import com.saraylg.matchmaker.matchmaker.repository.mongo.InvitationMongoRepository;
import com.saraylg.matchmaker.matchmaker.repository.mongo.JamMongoRepository;
import com.saraylg.matchmaker.matchmaker.repository.mongo.UsuarioMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final JamMongoRepository jamMongoRepository;
    private final JamRepository jamRepository;
    private final InvitationMongoRepository invMongoRepo;
    private final InvitationRepository invRepository;

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
    public UsuarioOutputDTO updateUserIfChanged(String steamId, UsuarioInputDTO updatedDto) {
        // Reutilizamos método ya existente
        UsuarioEntity existing = findUserById(steamId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean hayCambios = false;

        if (!updatedDto.getName().equals(existing.getName())) {
            existing.setName(updatedDto.getName());
            hayCambios = true;
        }

        if (!updatedDto.getAvatar().equals(existing.getAvatar())) {
            existing.setAvatar(updatedDto.getAvatar());
            hayCambios = true;
        }

        if (!updatedDto.getProfileUrl().equals(existing.getProfileUrl())) {
            existing.setProfileUrl(updatedDto.getProfileUrl());
            hayCambios = true;
        }

        if (!updatedDto.getTimeCreated().equals(existing.getTimeCreated())) {
            existing.setTimeCreated(updatedDto.getTimeCreated());
            hayCambios = true;
        }

        if (hayCambios) {
            return usuarioMapper.entityToOutputDto(usuarioMongoRepository.save(existing));
        } else {
            return usuarioMapper.entityToOutputDto(existing);
        }
    }


    /**
     * Elimina un usuario de la base de datos según su Steam ID.
     * @param steamId ID del usuario a eliminar.
     * @return Mensaje de éxito.
     */

    public String deleteUser(String steamId) {
        deleteJamsCreatedByUser(steamId);
        deleteJamsCreatedByUser(steamId);
        removeUserFromAllJams(steamId);
        deleteAllUserInvitations(steamId);
        usuarioMongoRepository.deleteById(steamId);

        return "Usuario y todos sus datos relacionados eliminados con éxito";
    }

    private void deleteJamsCreatedByUser(String steamId) {
        // Obtener todas las jams creadas por el usuario
        List<JamEntity> jamsCreadas = jamMongoRepository.findByCreatedBy_SteamId(steamId);

        // Eliminar cada jam
        for (JamEntity jam : jamsCreadas) {
            jamRepository.deleteJam(jam.getId());
        }
    }

    private void removeUserFromAllJams(String steamId) {
        // Obtener todas las jams en las que participa el usuario
        List<JamEntity> jamsParticipadas = jamMongoRepository.findByPlayers_SteamId(steamId)
                .stream()
                .filter(jamEntity -> jamEntity.getState() != JamState.FINISHED)
                .toList();

        // Eliminar al usuario de cada jam
        for (JamEntity jam : jamsParticipadas) {
            jamRepository.removePlayerFromJam(jam.getId(), steamId);
        }
    }

    private void deleteAllUserInvitations(String steamId) {
        // Eliminar invitaciones donde el usuario es el remitente o el receptor
        List<InvitationEntity> invitaciones = invMongoRepo.findBySenderIdOrReceiverId(steamId, steamId);

        for (InvitationEntity inv : invitaciones) {
            invRepository.deleteInvite(inv.getInvId());
        }
    }
}
