package com.mcpd.controller;

import com.mcpd.dto.UsuarioDto;
import com.mcpd.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(@RequestParam String usuario, @RequestParam String password) {
        UsuarioDto usuarioDto = authService.login(usuario, password);
        if (usuarioDto != null) {
            return ResponseEntity.ok(usuarioDto);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}
