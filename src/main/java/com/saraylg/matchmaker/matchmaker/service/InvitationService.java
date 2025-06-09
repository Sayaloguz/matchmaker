package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.model.InvitationEntity;
import com.saraylg.matchmaker.matchmaker.repository.InvitationRepository;
import com.saraylg.matchmaker.matchmaker.repository.mongo.InvitationMongoRepository;
import com.saraylg.matchmaker.matchmaker.model.generic.GenericInvitation;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationRepository invRepo;
    private final InvitationMongoRepository invMongoRepo;

    public List<GenericInvitation> getFromUser(String userId) {
        return invRepo.getFromUser(userId);
    }

    public GenericInvitation createInvite(GenericInvitation genericInv) {
        return invRepo.createInvite(genericInv);
    }

    public GenericInvitation deleteInvite(String invId) {
        return invRepo.deleteInvite(invId);
    }



    public void deleteAllUserInvitations(String steamId) {
        // Eliminar invitaciones donde el usuario es el remitente o el receptor
        List<InvitationEntity> invitaciones = invMongoRepo.findBySenderIdOrReceiverId(steamId, steamId);

        for (InvitationEntity inv : invitaciones) {
            invRepo.deleteInvite(inv.getInvId());
        }
    }

}