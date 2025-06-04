package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.input.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JamMapper {
     JamOutputDTO jamToOutputDto(JamEntity jamsEntity);
     JamEntity jamInputDtoToJam(JamInputDTO jamInputDTO);
}