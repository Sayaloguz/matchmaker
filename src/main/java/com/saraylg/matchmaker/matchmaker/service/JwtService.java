package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Servicio responsable de generar y validar JSON Web Tokens (JWT)
 * para la autenticación de usuarios.
 */
@Service
public class JwtService {

    /** Clave secreta cargada desde application.properties */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /** Clave criptográfica derivada de jwtSecret para firmar/validar tokens */
    private Key key;


    /**
     * Inicializa la clave una vez que Spring haya inyectado jwtSecret.
     * HS256 necesita una clave de al menos 256 bits (32 caracteres).
     */
    @PostConstruct
    public void init() {
        System.out.println("CLAVE JWT INYECTADA: " + jwtSecret);
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Genera un JWT con un día de validez a partir de los datos del usuario.
     *
     * @param user DTO con la información pública del usuario.
     * @return token JWT firmado.
     */
    public String generateToken(UsuarioOutputDTO user) {
        return Jwts.builder()
                .setSubject(user.getSteamId())              // Identificador principal
                .claim("name", user.getName())              // Nombre visible
                .claim("profileUrl", user.getProfileUrl())  // URL de perfil
                .claim("avatar", user.getAvatar())          // Avatar
                .setIssuedAt(new Date())                    // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 1 día
                .signWith(key, SignatureAlgorithm.HS256)    // Firma HS256
                .compact();
    }

    /**
     * Valida el JWT recibido y devuelve un DTO con los datos extraídos.
     *
     * @param token JWT a validar.
     * @return UsuarioOutputDTO con la info contenida en el token.
     * @throws io.jsonwebtoken.JwtException si el token es inválido o está expirado.
     */
    public UsuarioOutputDTO parseToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new UsuarioOutputDTO(
                claims.getSubject(),                // steamId
                claims.get("name", String.class),   // name
                claims.get("profileUrl", String.class),
                claims.get("avatar", String.class),
                null                                // timeCreated si lo incluyes como claim adicional
        );
    }
}
