package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.UsuariosService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UsuariosService usuariosService;

    @GetMapping("/prueba")
    public String prueba() {
        return "Prueba de autenticación exitosa";
    }
    @GetMapping("/steam/login")
    public void steamLogin(HttpServletResponse response) throws IOException {
        // URL de login de Steam con parámetros básicos
        String steamUrl = "https://steamcommunity.com/openid/login?" +
                "openid.ns=http://specs.openid.net/auth/2.0&" +
                "openid.mode=checkid_setup&" +
                "openid.return_to=http://localhost:8080/auth/steam/callback&" +
                "openid.realm=http://localhost:8080&" +
                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select";

        response.sendRedirect(steamUrl);
    }
    // V3
    @GetMapping("/steam/callback")
    public ResponseEntity<?> steamCallback(@RequestParam Map<String, String> params) {
        System.out.println("Callback de Steam recibido: " + params);

        if (verificarRespuestaSteam(params)) {
            String claimedId = params.get("openid.claimed_id");
            String steamId = claimedId.substring(claimedId.lastIndexOf("/") + 1);

            // Aquí llamamos directamente al servicio de usuarios para registrar si no existe
            UsuarioOutputDTO user = usuariosService.getAndSavePlayer(steamId);

            return ResponseEntity.status(302)
                    .header("Location", "http://localhost:3000/perfil?id=" + steamId)
                    .build();
        } else {
            return ResponseEntity.status(401).body("Autenticación con Steam fallida.");
        }
    }

    /*
    // V2
    @GetMapping("/steam/callback")
    public String steamCallback(@RequestParam Map<String, String> params) {
        System.out.println("Callback de Steam recibido: " + params);

        if (verificarRespuestaSteam(params)) {
            String claimedId = params.get("openid.claimed_id");
            String steamId = claimedId.substring(claimedId.lastIndexOf("/") + 1);
            return "Autenticación válida. SteamID: " + steamId;
        } else {
            return "Autenticación fallida.";
        }
    }
    */
    // V1
    /*

    @GetMapping("/steam/callback")
    public String steamCallback(@RequestParam Map<String, String> params) {
        // Aquí deberías validar la respuesta de Steam.
        System.out.println("Callback de Steam recibido: " + params);
        return "Autenticación con Steam completada (aún sin validar)";
    }*/


    private boolean verificarRespuestaSteam(Map<String, String> params) {
        try {
            URL url = new URL("https://steamcommunity.com/openid/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringJoiner body = new StringJoiner("&");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                body.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            body.add("openid.mode=check_authentication");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.toString().getBytes());
            }

            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains("is_valid:true")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}