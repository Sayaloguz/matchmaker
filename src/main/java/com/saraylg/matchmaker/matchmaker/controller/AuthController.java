package com.saraylg.matchmaker.matchmaker.controller;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import com.saraylg.matchmaker.matchmaker.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación con Steam y gestión de sesiones")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Redirige al login de Steam")
    @GetMapping("/steam/login")
    public void loginSteam(HttpServletResponse response) throws IOException
    {
        authService.redirigirSteam(response);
    }


    @Operation(summary = "Procesa el callback de Steam después del login")
    @GetMapping("/steam/callback")
    public void callbackSteam(
            @Parameter(description = "Parámetros de autenticación OpenID")
            @RequestParam Map<String, String> params, HttpServletResponse response) throws IOException
    {
        authService.steamCallback(params, response);
    }


    @Operation(summary = "Obtiene los datos del usuario autenticado desde el token JWT")
    @GetMapping("/me")
    public ResponseEntity<UsuarioOutputDTO> cookieMe(
            @Parameter(description = "JWT almacenado en una cookie")
            @CookieValue(name = "jwt", required = false) String token
    ) {
        return authService.getUsuarioDesdeToken(token);
    }


    @Operation(summary = "Cerrar sesión del usuario eliminando la cookie JWT")
    @GetMapping("/logout")
    public ResponseEntity<Void> salir(HttpServletResponse response) {
        authService.cerrarSesion(response);
        return ResponseEntity.noContent().build(); // 204
    }
}
