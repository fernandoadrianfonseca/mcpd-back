// com.mcpd.security.JwtAuthenticationFilter.java
package com.mcpd.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticación JWT para proteger endpoints del backend.
 *
 * <p>
 * Implementa un filtro {@link org.springframework.web.filter.OncePerRequestFilter} que:
 * <ol>
 *   <li>Ignora OPTIONS (CORS preflight)</li>
 *   <li>Ignora endpoints públicos (por ejemplo /auth/** y /logs)</li>
 *   <li>Lee el header Authorization: Bearer &lt;token&gt;</li>
 *   <li>Valida el JWT con {@link JwtService}</li>
 *   <li>Construye un Authentication y lo coloca en {@link org.springframework.security.core.context.SecurityContextHolder}</li>
 * </ol>
 *
 * <h3>Authorities</h3>
 * <p>
 * Se realiza un mapeo simple:
 * <ul>
 *   <li>perfil == 1 → ROLE_ADMIN</li>
 *   <li>caso contrario → ROLE_USER</li>
 * </ul>
 *
 * <p>
 * Los claims se guardan en {@code auth.setDetails(claims)} para acceso posterior.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Aplica la autenticación por JWT si el request no pertenece a la whitelist.
     *
     * <p>
     * Si el token es inválido o expiró, se limpia el SecurityContext y se continúa
     * el chain para que la configuración de security resuelva el 401/403.
     *
     * @param request request HTTP.
     * @param response response HTTP.
     * @param filterChain cadena de filtros.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()) || servletPath.startsWith("/auth/") || servletPath.startsWith("/logs")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Jws<Claims> jws = jwtService.validate(token);
                Claims claims = jws.getBody();

                // Mapear perfil->ROLE_*
                int perfil = claims.get("perfil", Integer.class) != null ? claims.get("perfil", Integer.class) : 0;
                // ejemplo simple: perfil 1=ADMIN, sino USER (ajustá a tu negocio)
                List<SimpleGrantedAuthority> authorities =
                        perfil == 1 ?
                                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) :
                                List.of(new SimpleGrantedAuthority("ROLE_USER"));

                AbstractAuthenticationToken auth =
                        new AbstractAuthenticationToken(authorities) {
                            @Override public Object getCredentials() { return token; }
                            @Override public Object getPrincipal() { return claims.getSubject(); }
                        };
                auth.setAuthenticated(true);
                // Podés guardar claims extras en detalles
                auth.setDetails(claims);

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (io.jsonwebtoken.JwtException | IllegalArgumentException ex) {
                // Token inválido o expirado → limpiamos contexto
                SecurityContextHolder.clearContext();
            } catch (Exception ex) {
                // Si fue otro tipo de excepción, no limpiar el contexto
                // para que el backend pueda manejarla correctamente
                throw ex;
            }
        }

        filterChain.doFilter(request, response);
    }
}
