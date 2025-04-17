package com.saraylg.matchmaker.matchmaker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String steamId;

    private String name;
    private String profileUrl;
    private String avatar;
    private String timeCreated;
}
