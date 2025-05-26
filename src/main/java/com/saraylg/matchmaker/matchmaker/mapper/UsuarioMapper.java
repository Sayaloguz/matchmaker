package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.SteamPlayerDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioInputDTO steamPlayerToDto(SteamPlayerDTO steamPlayer);

    UsuarioEntity dtoToUsuariosEntity(UsuarioInputDTO usuarioDTO);

    UsuarioOutputDTO dtoToOutputDto(UsuarioInputDTO usuarioDTO);

    UsuarioOutputDTO entityToOutputDto(UsuarioEntity entity);

}
