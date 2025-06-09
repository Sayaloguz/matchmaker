package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.input.SteamPlayerInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericUsuario;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioInputDTO steamPlayerToInput(SteamPlayerInputDTO steamPlayer);

    UsuarioEntity inputToEntity(UsuarioInputDTO usuarioDTO);

    GenericUsuario entityToGeneric(UsuarioEntity usuarioEntity);

    List<GenericUsuario> entityListToGeneric(List<UsuarioEntity> usuarioEntities);

    List<UsuarioOutputDTO> genericListToOutput(List<GenericUsuario> genericUsuarios);

    UsuarioOutputDTO genericToOutput(GenericUsuario genericUsuario);

    GenericUsuario inputToGeneric(UsuarioInputDTO usuarioInputDTO);

    UsuarioEntity genericToEntity(GenericUsuario genericUsuario);

    default Optional<UsuarioEntity> genericOptionalToEntity(Optional<GenericUsuario> genericUsuarioOpt) {
        return genericUsuarioOpt.map(this::genericToEntity);
    }

    default Optional<GenericUsuario> entityOptionalToGeneric(Optional<UsuarioEntity> entityOpt) {
        return entityOpt.map(this::entityToGeneric);
    }

}
