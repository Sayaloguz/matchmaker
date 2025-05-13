package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.JwtService;
import com.saraylg.matchmaker.matchmaker.service.UsuariosService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final JwtService jwtService;

    @GetMapping("/prueba")
    public String prueba() {
        return "Prueba de autenticación exitosa";
    }

    @GetMapping("/steam/login")
    public void steamLogin(HttpServletResponse response) throws IOException {
        String steamUrl = "https://steamcommunity.com/openid/login?" +
                "openid.ns=http://specs.openid.net/auth/2.0&" +
                "openid.mode=checkid_setup&" +
                "openid.return_to=http://localhost:8080/auth/steam/callback&" +
                "openid.realm=http://localhost:8080&" +
                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select";

        response.sendRedirect(steamUrl);
    }

    @GetMapping("/steam/callback")
    public void steamCallback(@RequestParam Map<String, String> params, HttpServletResponse servletResponse) throws IOException {
        System.out.println("Callback de Steam recibido: " + params);

        if (verificarRespuestaSteam(params)) {
            String claimedId = params.get("openid.claimed_id");
            String steamId = claimedId.substring(claimedId.lastIndexOf("/") + 1);

            UsuarioOutputDTO user = usuariosService.getAndSavePlayer(steamId);
            String token = jwtService.generateToken(user);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // Cambia a true si usas HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 día en segundos

            servletResponse.addCookie(cookie);
            servletResponse.sendRedirect("http://localhost:3000/perfil?id=" + steamId);
        } else {
            servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticación con Steam fallida.");
        }
    }


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
