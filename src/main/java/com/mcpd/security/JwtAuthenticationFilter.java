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

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

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
            } catch (Exception ex) {
                // Token inválido/expirado → limpiamos contexto y seguimos (dejará 401 más adelante)
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
