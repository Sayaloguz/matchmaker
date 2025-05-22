package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamModifyDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.JamsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JamMapper {
     JamOutputDTO jamToOutputDto(JamsEntity jamsEntity);
     JamOutputDTO jamInputToOutputDto(JamInputDTO jamInputDTO);
     JamOutputDTO jamModifyToJamOutput(JamModifyDTO jamModifyDTO);

     JamsEntity jamInputDtoToJam(JamInputDTO jamInputDTO);
     JamsEntity jamOutputDtoToJam(JamOutputDTO jamOutputDTO);
     JamsEntity jamModifyDtoToJam(JamModifyDTO jamModifyDTO);

     JamInputDTO jamOutputToJamInput(JamOutputDTO jamOutputDTO);
     JamInputDTO JamsEntityToJamInputDTO(JamsEntity jamsEntity);
     JamInputDTO JamModifyDTOToJamInputDTO(JamModifyDTO jamModifyDTO);

     JamModifyDTO jamInputDtoToJamModifyDto(JamInputDTO jamInputDTO);
     JamModifyDTO jamOutputDtoToJamModifyDto(JamOutputDTO jamOutputDTO);
     JamModifyDTO jamEntityToJamModifyDto(JamsEntity jamsEntity);


}


/*
    UsuarioInputDTO steamPlayerToDto(SteamPlayer steamPlayer);
    UsuarioEntity dtoToUsuariosEntity(UsuarioInputDTO usuarioDTO);
    UsuarioOutputDTO steamPlayerToOutputDto(SteamPlayer steamPlayer);
    UsuarioOutputDTO dtoToOutputDto(UsuarioInputDTO usuarioDTO);

    UsuarioOutputDTO entityToOutputDto(UsuarioEntity entity);

 */