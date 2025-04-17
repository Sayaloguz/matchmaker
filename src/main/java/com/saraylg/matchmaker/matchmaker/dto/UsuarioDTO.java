package com.saraylg.matchmaker.matchmaker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotBlank (message = "Steam ID cannot be blank")
    private String steamId;

    @NotBlank (message = "Name cannot be blank")
    private String name;

    @NotBlank (message = "Profile URL cannot be blank")
    private String profileUrl;

    @NotBlank (message = "Avatar cannot be blank")
    private String avatar;

    @NotBlank (message = "Time created cannot be blank")
    private String timeCreated;

}
