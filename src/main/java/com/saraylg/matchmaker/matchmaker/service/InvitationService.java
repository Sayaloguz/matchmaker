package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.repository.InvitationRepository;
import com.saraylg.matchmaker.matchmaker.service.generics.GenericInvitation;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class InvitationService {

    private final InvitationRepository invRepo;

    public List<GenericInvitation> getFromUser(String userId) {
        return invRepo.getFromUser(userId);
    }

    public GenericInvitation createInvite(GenericInvitation genericInv) {
        System.out.println("LLEGA" + genericInv.getInvId());
        return invRepo.createInvite(genericInv);
    }

    public GenericInvitation deleteInvite(String invId) {
        return invRepo.deleteInvite(invId);
    }

}