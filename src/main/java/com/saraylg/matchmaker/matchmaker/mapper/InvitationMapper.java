package com.saraylg.matchmaker.matchmaker.mapper;

import com.saraylg.matchmaker.matchmaker.dto.input.InvitationInputDTO;
import com.saraylg.matchmaker.matchmaker.dto.output.InvitationOutputDTO;
import com.saraylg.matchmaker.matchmaker.model.InvitationEntity;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericInvitation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    InvitationEntity genericToEntity(GenericInvitation genericInv);

    GenericInvitation entityToGeneric(InvitationEntity invEntity);

    GenericInvitation inputToGeneric(InvitationInputDTO invInputDTO);

    InvitationOutputDTO genericToOutput(GenericInvitation genericInvitation);

    List<GenericInvitation> entityListToGenericList(List<InvitationEntity> invEntityList);

    List<InvitationOutputDTO> genericListToOutputList(List<GenericInvitation> invGenericList);

}
