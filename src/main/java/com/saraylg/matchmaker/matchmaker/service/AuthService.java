package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.output.UsuarioOutputDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;


    public void redirigirSteam(HttpServletResponse response) throws IOException {
        String API_URL = "https://matchmakerapi.onrender.com";
        String steamUrl = "https://steamcommunity.com/openid/login?" +
                "openid.ns=http://specs.openid.net/auth/2.0&" +
                "openid.mode=checkid_setup&" +
                "openid.return_to=" + API_URL + "/auth/steam/callback&" +
                "openid.realm="+ API_URL + "&" +
                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select";

        response.sendRedirect(steamUrl);
    }

    public void steamCallback(Map<String, String> params, HttpServletResponse response) throws IOException {
        System.out.println("Callback de Steam recibido: " + params);

        if (verificarSteam(params)) {
            String steamId = extraerSteamId(params.get("openid.claimed_id"));

            UsuarioOutputDTO usuario = usuarioService.getAndSavePlayer(steamId);
            String token = jwtService.generateToken(usuario);

            String cookieString = "jwt=" + token +
                    "; Max-Age=86400" +
                    "; Path=/" +
                    "; Secure" +
                    "; HttpOnly" +
                    "; SameSite=None";

            response.addHeader("Set-Cookie", cookieString);

            String FRONT_URL = "https://mm-vercel-ten.vercel.app";
            response.sendRedirect(FRONT_URL + "/perfil?id=" + steamId);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticación con Steam fallida.");
        }
    }

    /*
    public void steamCallback(Map<String, String> params, HttpServletResponse response) throws IOException {
        System.out.println("Callback de Steam recibido: " + params);

        if (verificarSteam(params)) {
            String steamId = extraerSteamId(params.get("openid.claimed_id"));

            UsuarioOutputDTO usuario = usuarioService.getAndSavePlayer(steamId);
            String token = jwtService.generateToken(usuario);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // True si HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 día

            response.addCookie(cookie);
            // private final String API_URL = "http://localhost:8080";
            String FRONT_URL = "https://mm-vercel-ten.vercel.app";
            response.sendRedirect(FRONT_URL + "/perfil?id=" + steamId);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Autenticación con Steam fallida.");
        }
    }
    */

    public ResponseEntity<UsuarioOutputDTO> getUsuarioDesdeToken(String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            UsuarioOutputDTO usuario = jwtService.parseToken(token);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public void cerrarSesion(HttpServletResponse response) {
        String cookieDelete = "jwt=; Max-Age=0; Path=/; Secure; HttpOnly; SameSite=None";
        response.addHeader("Set-Cookie", cookieDelete);
    }

    /*
    public void cerrarSesion(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // True si HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

    }
    */

    private String extraerSteamId(String claimedId) {
        return claimedId.substring(claimedId.lastIndexOf("/") + 1);
    }

    private boolean verificarSteam(Map<String, String> params) {
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
