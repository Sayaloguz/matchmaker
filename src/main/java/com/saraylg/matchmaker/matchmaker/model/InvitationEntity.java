package com.saraylg.matchmaker.matchmaker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invitations")
public class InvitationEntity {

    @Id
    private String invId;
    private String jamId;
    private String senderId;
    private String receiverId;
    private Date sentDate;

}
