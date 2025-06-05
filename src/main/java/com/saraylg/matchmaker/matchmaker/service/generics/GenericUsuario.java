package com.saraylg.matchmaker.matchmaker.service.generics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericUsuario {

    private String steamId;

    private String name;
    private String profileUrl;
    private String avatar;
    private String timeCreated;

}
