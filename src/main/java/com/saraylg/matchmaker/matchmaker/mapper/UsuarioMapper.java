package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.input.SteamPlayerInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.input.UsuarioInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioInputDTO steamPlayerToDto(SteamPlayerInputDTO steamPlayer);

    UsuarioEntity dtoToUsuariosEntity(UsuarioInputDTO usuarioDTO);

    UsuarioOutputDTO dtoToOutputDto(UsuarioInputDTO usuarioDTO);

    UsuarioOutputDTO entityToOutputDto(UsuarioEntity entity);

}
