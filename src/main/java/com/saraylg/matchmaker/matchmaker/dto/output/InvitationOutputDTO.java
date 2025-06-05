package com.saraylg.matchmaker.matchmaker.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationOutputDTO {
    private String invId;
    private String jamId;
    private String senderId;
    private String receiverId;
    private Date sentDate;
}
