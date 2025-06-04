package com.saraylg.matchmaker.matchmaker.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un jugador individual devuelto por la API de Steam.
 * Esta clase se alinea con los nombres de campos originales del JSON.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SteamPlayerInputDTO {

    @JsonProperty("steamid")
    private String steamId;

    @JsonProperty("personaname")
    private String name;

    @JsonProperty("profileurl")
    private String profileUrl;

    @JsonProperty("avatarfull")
    private String avatar;

    @JsonProperty("timecreated")
    private Long timeCreated;

}
