package com.mcpd.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración de seguridad Spring Security para el backend.
 *
 * <p>
 * Define un esquema stateless basado en JWT con dos cadenas de filtros:
 * <ol>
 *   <li><b>Whitelist</b> (Order 0): permite sin autenticación endpoints públicos (por ejemplo /auth/** y /logs).</li>
 *   <li><b>API protegida</b> (Order 1): requiere JWT para el resto de endpoints.</li>
 * </ol>
 *
 * <h3>Características</h3>
 * <ul>
 *   <li>CSRF deshabilitado (API stateless)</li>
 *   <li>CORS habilitado por configuración global</li>
 *   <li>SessionCreationPolicy.STATELESS</li>
 *   <li>EntryPoint 401 cuando falta auth</li>
 *   <li>AccessDenied 403 cuando no tiene permisos</li>
 *   <li>Inserta {@link JwtAuthenticationFilter} antes del UsernamePasswordAuthenticationFilter</li>
 * </ul>
 *
 * <p>
 * Nota: se incluye {@link org.springframework.security.core.userdetails.UserDetailsService}
 * en memoria para compatibilidad/testing, aunque la autenticación principal del sistema
 * se realiza vía JWT emitido por el módulo /auth.
 */
@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Cadena de seguridad para endpoints públicos (sin filtro JWT).
     *
     * <p>
     * Permite libre acceso a rutas de autenticación y logging.
     *
     * @param http configuración de seguridad.
     * @return SecurityFilterChain whitelist.
     */
    // 1) Cadena WHITELIST: SOLO /auth/** y /logs (sin filtro JWT)
    @Bean
    @Order(0)
    public SecurityFilterChain authWhitelist(HttpSecurity http) throws Exception {
        http
                .securityMatcher(req -> {
                    String p = req.getServletPath();
                    return p.startsWith("/auth/") || p.equals("/auth") || p.startsWith("/logs");
                })
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(reg -> reg.anyRequest().permitAll());
        return http.build();
    }

    /**
     * Cadena de seguridad principal para la API protegida (JWT obligatorio).
     *
     * <p>
     * Requiere autenticación para cualquier request que no esté en la whitelist.
     * Configura manejo de errores estándar:
     * - 401 si no autenticado
     * - 403 si autenticado sin permisos
     *
     * @param http configuración de seguridad.
     * @return SecurityFilterChain protegida.
     */
    // 2) Cadena API: para TODO lo demás (con JWT)
    @Bean
    @Order(1)
    public SecurityFilterChain apiChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) ->
                                res.setStatus(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED))
                        .accessDeniedHandler((req, res, e) ->
                                res.setStatus(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN))
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtService),
                        org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configuración CORS global.
     *
     * <p>
     * Actualmente permite cualquier origen/headers/métodos (útil en desarrollo).
     * En producción se recomienda restringir {@code allowedOriginPatterns} al dominio real.
     *
     * @return fuente de configuración CORS.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.setAllowedOriginPatterns(List.of(CorsConfiguration.ALL)); // en prod: tu dominio
        cors.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        cors.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cors.setExposedHeaders(List.of("Authorization","Content-Type"));
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cors);
        return src;
    }

    /**
     * UserDetailsService en memoria (soporte para pruebas/compatibilidad).
     *
     * No reemplaza el mecanismo principal del sistema (JWT).
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("pa$$word"))
                .roles("USER")
                .build());
        return manager;
    }

    /**
     * PasswordEncoder utilizado por el UserDetailsService en memoria.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/**"));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOriginPattern(CorsConfiguration.ALL);
        corsConfig.addAllowedMethod(CorsConfiguration.ALL);
        corsConfig.addAllowedHeader(CorsConfiguration.ALL);
        corsConfig.addExposedHeader(CorsConfiguration.ALL);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("pa$$word"))
                .roles("USER")
                .build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}
