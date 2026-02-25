package com.mcpd.service;

import com.mcpd.dto.UsuarioDto;
import com.mcpd.repository.AuthRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación y gestión de credenciales.
 *
 * <p>
 * Esta capa orquesta el acceso a {@link com.mcpd.repository.AuthRepository},
 * que implementa la autenticación y mantenimiento de usuarios mediante
 * procedimientos almacenados en base de datos (SQL Server).
 *
 * <h3>Responsabilidades</h3>
 * <ul>
 *   <li>Autenticar credenciales y devolver {@link com.mcpd.dto.UsuarioDto}</li>
 *   <li>Modificar contraseña de un usuario</li>
 *   <li>Blanquear/resetear credenciales de un usuario</li>
 * </ul>
 *
 * Esta clase no genera tokens ni aplica reglas HTTP; eso corresponde al controller.
 */
@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Autentica un usuario contra la base de datos.
     *
     * @param usuario nombre de usuario.
     * @param password contraseña en texto plano recibida del cliente.
     * @return {@link UsuarioDto} si las credenciales son válidas; {@code null} si no lo son.
     */
    public UsuarioDto login(String usuario, String password) {
        return authRepository.autenticarUsuario(usuario, password);
    }

    /**
     * Modifica la contraseña de un usuario.
     *
     * @param usuario nombre de usuario.
     * @param password nueva contraseña.
     */
    public void modificarUsuario(String usuario, String password) {
        authRepository.modificarUsuario(usuario, password);
    }

    /**
     * Blanquea/resetea la contraseña de un usuario (según procedimiento almacenado).
     *
     * @param usuario nombre de usuario.
     */
    public void blanquearUsuario(String usuario) {
        authRepository.blanquearUsuario(usuario);
    }
}
