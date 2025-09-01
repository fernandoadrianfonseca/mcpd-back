// com.mcpd.security.JwtService.java
package com.mcpd.security;

import com.mcpd.dto.UsuarioDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMillis;
    private final String issuer;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-minutes}") long expirationMinutes,
            @Value("${app.jwt.issuer}") String issuer) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMinutes * 60_000L;
        this.issuer = issuer;
    }

    public String generateToken(UsuarioDto u) {
        Map<String, Object> claims = new HashMap<>();
        // Incluí claims útiles para autorización en el filtro
        claims.put("perfil", u.getPerfil());         // int
        claims.put("modulo", u.getModulo());         // int
        claims.put("legajo", u.getLegajo());         // long
        claims.put("dependencia", u.getDependencia());
        claims.put("secretaria", u.getSecretaria());
        claims.put("administracion", u.getAdministracion());

        Instant now = Instant.now();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(u.getNombre() != null ? u.getNombre() : String.valueOf(u.getLegajo()))
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validate(String token) throws JwtException {
        return Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
