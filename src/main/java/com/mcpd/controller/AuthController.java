package com.mcpd.controller;

import com.mcpd.dto.AuthResponse;
import com.mcpd.dto.UsuarioDto;
import com.mcpd.security.JwtService;
import com.mcpd.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST de autenticación.
 *
 * <p>
 * Expone endpoints para:
 * <ul>
 *   <li>Login: valida credenciales y emite JWT</li>
 *   <li>Modificar contraseña</li>
 *   <li>Blanquear/resetear contraseña</li>
 * </ul>
 *
 * La autenticación de credenciales se delega a {@link AuthService} y la emisión
 * del token a {@link com.mcpd.security.JwtService}.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Autentica un usuario y devuelve un JWT junto con los datos del usuario.
     *
     * POST /auth/login
     * ↓
     * AuthController
     * ↓
     * AuthService
     * ↓
     * AuthRepository (SP seguridadVerificaUsuario)
     * ↓
     * UsuarioDto
     * ↓
     * JwtService.generateToken()
     * ↓
     * AuthResponse(token + usuario)
     *
     * <p>
     * Si las credenciales son inválidas retorna HTTP 401.
     *
     * @param usuario nombre de usuario.
     * @param password contraseña.
     * @return {@link com.mcpd.dto.AuthResponse} con token JWT y {@link UsuarioDto}.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam String usuario,
            @RequestParam String password) {

        UsuarioDto u = authService.login(usuario, password);
        if (u == null) {
            return ResponseEntity.status(401).build(); // credenciales inválidas
        }
        String token = jwtService.generateToken(u);
        return ResponseEntity.ok(new AuthResponse(token, u));
    }

    /**
     * Modifica la contraseña de un usuario.
     *
     * @param usuario nombre de usuario.
     * @param password nueva contraseña.
     * @return 200 OK si la operación se ejecuta correctamente.
     */
    @PostMapping("/modificar")
    public ResponseEntity<Void> modificarUsuario(@RequestParam String usuario, @RequestParam String password) {
        authService.modificarUsuario(usuario, password);
        return ResponseEntity.ok().build();
    }

    /**
     * Blanquea/resetea la contraseña de un usuario.
     *
     * @param usuario nombre de usuario.
     * @return 200 OK si la operación se ejecuta correctamente.
     */
    @PostMapping("/blanquear")
    public ResponseEntity<Void> blanquearUsuario(@RequestParam String usuario) {
        authService.blanquearUsuario(usuario);
        return ResponseEntity.ok().build();
    }
}
