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

/**
 * Servicio de emisión y validación de tokens JWT.
 *
 * <p>
 * Genera tokens firmados (HS256) utilizando una clave simétrica configurada
 * vía propiedades:
 * <ul>
 *   <li>{@code app.jwt.secret}</li>
 *   <li>{@code app.jwt.expiration-minutes}</li>
 *   <li>{@code app.jwt.issuer}</li>
 * </ul>
 *
 * <p>
 * El token incluye claims utilizados para autorización y contexto:
 * <ul>
 *   <li>perfil</li>
 *   <li>modulo</li>
 *   <li>legajo</li>
 *   <li>dependencia</li>
 *   <li>secretaria</li>
 *   <li>administracion</li>
 * </ul>
 *
 * <p>
 * La validación exige el issuer configurado y verifica firma/expiración.
 */
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

    /**
     * Genera un JWT para el usuario autenticado.
     *
     * <p>
     * El {@code subject} se establece como:
     * <ul>
     *   <li>{@code u.nombre} si está disponible</li>
     *   <li>caso contrario, {@code u.legajo}</li>
     * </ul>
     *
     * @param u usuario autenticado.
     * @return token JWT compacto (String).
     */
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

    /**
     * Valida un token JWT verificando firma, expiración e issuer.
     *
     * @param token token JWT (sin el prefijo "Bearer ").
     * @return {@link Jws} con {@link Claims} si el token es válido.
     * @throws JwtException si el token es inválido/expiró/falla validación.
     */
    public Jws<Claims> validate(String token) throws JwtException {
        return Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
