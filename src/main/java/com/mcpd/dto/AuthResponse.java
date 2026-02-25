package com.mcpd.dto;

/**
 * DTO de respuesta del endpoint de autenticación (/auth/login).
 *
 * <p>
 * Contiene:
 * <ul>
 *   <li>El token JWT generado para el usuario autenticado</li>
 *   <li>La información del usuario ({@link UsuarioDto})</li>
 * </ul>
 *
 * <p>
 * Este objeto se devuelve al frontend luego de una autenticación exitosa.
 * El token debe enviarse en requests posteriores dentro del header:
 *
 * <pre>
 * Authorization: Bearer &lt;token&gt;
 * </pre>
 *
 * El objeto {@code usuario} permite al frontend inicializar el contexto
 * de sesión (perfil, módulo, dependencia, etc.) sin necesidad de consultar
 * nuevamente al backend.
 */
public class AuthResponse {

    /**
     * Token JWT firmado que autentica al usuario en requests posteriores.
     */
    private String token;

    /**
     * Información contextual del usuario autenticado.
     */
    private UsuarioDto usuario;

    public AuthResponse(String token, UsuarioDto usuario) {
        this.token = token; this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }
}