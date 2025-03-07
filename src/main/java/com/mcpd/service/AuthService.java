package com.mcpd.service;

import com.mcpd.dto.UsuarioDto;
import com.mcpd.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public UsuarioDto login(String usuario, String password) {
        return authRepository.autenticarUsuario(usuario, password);
    }
}
