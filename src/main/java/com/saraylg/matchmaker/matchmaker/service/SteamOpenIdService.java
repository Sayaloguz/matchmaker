package com.saraylg.matchmaker.matchmaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamOpenIdService {
    /*

    private final String STEAM_OPENID_URL = "https://steamcommunity.com/openid/login";
    private final String API_KEY = "5116DC9A8927605697AC1142B237E41D";

    public String buildLoginUrl() {
        System.out.println("HOLA");
        Map<String, String> params = new HashMap<>();
        params.put("openid.ns", "http://specs.openid.net/auth/2.0");
        params.put("openid.mode", "checkid_setup");
        params.put("openid.return_to", "http://localhost:8080/auth/steam/callback");
        params.put("openid.realm", "http://localhost:8080");
        params.put("openid.identity", "http://specs.openid.net/auth/2.0/identifier_select");
        params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");

        return STEAM_OPENID_URL + "?" + params.entrySet().stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    public boolean validateLogin(String claimedId) {
        // Implementa la validación del OpenID response de Steam
        // Esto requiere hacer una solicitud a Steam para verificar los parámetros
        // Consulta la documentación de OpenID para implementar esto correctamente
        return claimedId != null && claimedId.contains("steamcommunity.com");
    }

    public String extractSteamId(String claimedId) {
        // El claimedId tiene formato: https://steamcommunity.com/openid/id/7656119...
        String[] parts = claimedId.split("/");
        return parts[parts.length - 1];
    }
    */

}