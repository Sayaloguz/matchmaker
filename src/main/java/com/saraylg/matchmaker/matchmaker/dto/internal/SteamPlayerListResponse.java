package com.saraylg.matchmaker.matchmaker.dto.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saraylg.matchmaker.matchmaker.dto.input.SteamPlayerInputDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Representa el objeto "response" de la API de Steam.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SteamPlayerListResponse {

    @JsonProperty("players")
    private List<SteamPlayerInputDTO> players;
}
