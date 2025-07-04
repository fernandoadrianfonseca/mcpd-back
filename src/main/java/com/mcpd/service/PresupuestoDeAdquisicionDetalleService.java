package com.mcpd.service;

import com.mcpd.model.PresupuestoDeAdquisicionDetalle;
import com.mcpd.repository.PresupuestoDeAdquisicionDetalleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresupuestoDeAdquisicionDetalleService {

    private final PresupuestoDeAdquisicionDetalleRepository repository;

    public PresupuestoDeAdquisicionDetalleService(PresupuestoDeAdquisicionDetalleRepository repository) {
        this.repository = repository;
    }

    public List<PresupuestoDeAdquisicionDetalle> findAll() {
        return repository.findAll();
    }

    public Optional<PresupuestoDeAdquisicionDetalle> findById(Long id) {
        return repository.findById(id);
    }

    public PresupuestoDeAdquisicionDetalle save(PresupuestoDeAdquisicionDetalle detalle) {
        return repository.save(detalle);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
