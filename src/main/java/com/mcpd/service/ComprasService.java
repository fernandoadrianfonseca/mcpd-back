package com.mcpd.service;

import com.mcpd.dto.ComprasImputacionDto;
import com.mcpd.repository.ComprasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComprasService {

    private final ComprasRepository repository;

    public ComprasService(ComprasRepository repository) {
        this.repository = repository;
    }

    public List<ComprasImputacionDto> obtenerImputaciones() {
        return repository.obtenerImputaciones();
    }
}
