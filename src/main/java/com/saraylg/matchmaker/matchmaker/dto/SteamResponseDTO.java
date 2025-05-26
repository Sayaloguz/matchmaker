package com.saraylg.matchmaker.matchmaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SteamResponseDTO {

    @JsonProperty("players")
    private List<SteamPlayerDTO> players;
}
