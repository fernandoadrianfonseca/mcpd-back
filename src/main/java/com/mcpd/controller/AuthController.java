package com.mcpd.controller;

import com.mcpd.dto.AuthResponse;
import com.mcpd.dto.UsuarioDto;
import com.mcpd.security.JwtService;
import com.mcpd.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/modificar")
    public ResponseEntity<Void> modificarUsuario(@RequestParam String usuario, @RequestParam String password) {
        authService.modificarUsuario(usuario, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/blanquear")
    public ResponseEntity<Void> blanquearUsuario(@RequestParam String usuario) {
        authService.blanquearUsuario(usuario);
        return ResponseEntity.ok().build();
    }
}
