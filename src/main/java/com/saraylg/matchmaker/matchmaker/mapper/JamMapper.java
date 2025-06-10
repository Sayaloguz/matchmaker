package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.input.JamInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.JamOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.JamEntity;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericJam;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface JamMapper {
     JamEntity jamInputDtoToJam(JamInputDTO jamInputDTO);


     List<JamOutputDTO> genericListToOutput(List<GenericJam> genericJams);

     GenericJam entityToGeneric(JamEntity jamEntity);

     JamOutputDTO genericToOutput(GenericJam genericJam);

     List<GenericJam> entityListToGeneric(List<JamEntity> jamEntities);

     default Optional<GenericJam> entityOptionalToGeneric(Optional<JamEntity> jamEntity) {
         return jamEntity.map(this::entityToGeneric);
     }

}