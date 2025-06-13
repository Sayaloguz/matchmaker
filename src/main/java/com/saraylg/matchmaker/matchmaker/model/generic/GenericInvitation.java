package com.saraylg.matchmaker.matchmaker.model.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericInvitation {

    private String invId;

    private String jamId;

    private String receiverId;

    private String senderId;

    private Date sentDate;

}
