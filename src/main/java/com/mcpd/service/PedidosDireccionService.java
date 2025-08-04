package com.mcpd.service;

import com.mcpd.model.PedidosDireccion;
import com.mcpd.repository.PedidosDireccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosDireccionService {

    private final PedidosDireccionRepository repository;

    public PedidosDireccionService(PedidosDireccionRepository repository) {
        this.repository = repository;
    }

    public List<PedidosDireccion> findAll() {
        return repository.findAll();
    }
}
