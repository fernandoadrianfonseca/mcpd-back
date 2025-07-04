package com.mcpd.service;

import com.mcpd.model.PedidoDeAdquisicionDetalle;
import com.mcpd.repository.PedidoDeAdquisicionDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoDeAdquisicionDetalleService {

    private final PedidoDeAdquisicionDetalleRepository repository;

    public PedidoDeAdquisicionDetalleService(PedidoDeAdquisicionDetalleRepository repository) {
        this.repository = repository;
    }

    public List<PedidoDeAdquisicionDetalle> findAll() {
        return repository.findAll();
    }

    public Optional<PedidoDeAdquisicionDetalle> findById(Long id) {
        return repository.findById(id);
    }

    public PedidoDeAdquisicionDetalle save(PedidoDeAdquisicionDetalle detalle) {
        return repository.save(detalle);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
