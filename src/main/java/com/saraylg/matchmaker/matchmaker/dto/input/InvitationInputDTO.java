package com.saraylg.matchmaker.matchmaker.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvitationInputDTO {

    private String invId;

    @NotBlank(message = "El ID de la invitación es obligatorio")
    private String jamId;

    @NotBlank(message = "El ID del usuario emisor es obligatorio")
    private String senderId;

    @NotBlank(message = "El ID del usuario receptor es obligatorio")
    private String receiverId;

    private Date sentDate;

}
