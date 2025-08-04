package com.mcpd.service;

import com.mcpd.model.PedidosDestino;
import com.mcpd.repository.PedidosDestinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosDestinoService {

    private final PedidosDestinoRepository repository;

    public PedidosDestinoService(PedidosDestinoRepository repository) {
        this.repository = repository;
    }

    public List<PedidosDestino> findAll() {
        return repository.findAll();
    }
}
