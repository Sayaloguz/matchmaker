package com.saraylg.matchmaker.matchmaker.repository.mongo;

import com.saraylg.matchmaker.matchmaker.model.InvitationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvitationMongoRepository extends MongoRepository<InvitationEntity, String> {

    List<InvitationEntity> findInvitationEntitiesByReceiverId(String receiverId);

    List<InvitationEntity> findBySenderIdOrReceiverId(String senderId, String receiverId);

}
