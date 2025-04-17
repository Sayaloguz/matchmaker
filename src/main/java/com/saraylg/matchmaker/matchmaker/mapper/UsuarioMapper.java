package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.SteamPlayer;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioInputDTO steamPlayerToDto(SteamPlayer steamPlayer);
    UsuarioEntity dtoToUsuariosEntity(UsuarioInputDTO usuarioDTO);
    UsuarioOutputDTO steamPlayerToOutputDto(SteamPlayer steamPlayer);
    UsuarioOutputDTO dtoToOutputDto(UsuarioInputDTO usuarioDTO);

    UsuarioOutputDTO entityToOutputDto(UsuarioEntity entity);

}
