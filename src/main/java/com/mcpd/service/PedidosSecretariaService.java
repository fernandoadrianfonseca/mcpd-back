package com.mcpd.service;

import com.mcpd.model.PedidosSecretaria;
import com.mcpd.repository.PedidosSecretariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosSecretariaService {

    private final PedidosSecretariaRepository repository;

    public PedidosSecretariaService(PedidosSecretariaRepository repository) {
        this.repository = repository;
    }

    public List<PedidosSecretaria> findAll() {
        return repository.findAll();
    }
}
