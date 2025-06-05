package com.saraylg.matchmaker.matchmaker.repository;

import com.saraylg.matchmaker.matchmaker.exceptions.InvitationNotFoundException;
import com.saraylg.matchmaker.matchmaker.mapper.InvitationMapper;
import com.saraylg.matchmaker.matchmaker.model.InvitationEntity;
import com.saraylg.matchmaker.matchmaker.repository.mongo.InvitationMongoRepository;
import com.saraylg.matchmaker.matchmaker.service.generics.GenericInvitation;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class InvitationRepository {

    private final InvitationMongoRepository invMongoRepo;
    private final InvitationMapper invMapper;




    public List<GenericInvitation> getFromUser(String userId) {
        return invMapper.entityListToGenericList(invMongoRepo.findInvitationEntitiesByReceiverId(userId));
    }

    public GenericInvitation createInvite(GenericInvitation genericInv) {
        InvitationEntity newInvitation = invMapper.genericToEntity(genericInv);

        newInvitation.setSentDate(new Date());

        invMongoRepo.save(newInvitation);

        return invMapper.entityToGeneric(newInvitation);
    }


    public GenericInvitation deleteInvite(String invId) {
        InvitationEntity existing = invMongoRepo.findById(invId)
                .orElseThrow(() -> new InvitationNotFoundException(invId));

            invMongoRepo.delete(existing);
            return invMapper.entityToGeneric(existing);
    }
}
