package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.SteamPlayer;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioDTO;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    SteamPlayer dtoToSteamPlayer(UsuarioDTO usuarioDTO);
    UsuarioDTO steamPlayerToDto(SteamPlayer steamPlayer);

    UsuarioEntity steamPlayerToUsuariosEntity(SteamPlayer steamPlayer);
    UsuarioEntity dtoToUsuariosEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO usuariosEntityToDto(UsuarioEntity usuariosEntity);

}
