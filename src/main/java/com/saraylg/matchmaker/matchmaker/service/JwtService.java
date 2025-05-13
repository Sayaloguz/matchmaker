package com.saraylg.matchmaker.matchmaker.service;

import com.saraylg.matchmaker.matchmaker.dto.UsuarioOutputDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private Key key;

    @PostConstruct
    public void init() {
        // Genera una clave secreta aleatoria (idealmente usa una fija desde env en producción)
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(UsuarioOutputDTO user) {
        return Jwts.builder()
                .setSubject(user.getSteamId())
                .claim("name", user.getName())
                .claim("profileUrl", user.getProfileUrl())
                .claim("avatar", user.getAvatar())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(key)
                .compact();
    }

    public UsuarioOutputDTO parseToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new UsuarioOutputDTO(
                claims.getSubject(),
                (String) claims.get("name"),
                (String) claims.get("profileUrl"),
                (String) claims.get("avatar"),
                null // puedes agregar timeCreated si lo incluyes también como claim
        );
    }
}
