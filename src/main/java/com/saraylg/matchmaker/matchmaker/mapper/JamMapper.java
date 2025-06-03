package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.enums.JamState;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JamMapper {
     JamOutputDTO jamToOutputDto(JamEntity jamsEntity);
     JamEntity jamInputDtoToJam(JamInputDTO jamInputDTO);
}