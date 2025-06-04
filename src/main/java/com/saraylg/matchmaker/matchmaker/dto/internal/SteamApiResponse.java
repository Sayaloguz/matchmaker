package com.saraylg.matchmaker.matchmaker.dto.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa la respuesta raíz completa de la API de Steam.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SteamApiResponse {

    @JsonProperty("response")
    private SteamPlayerListResponse response;

}
