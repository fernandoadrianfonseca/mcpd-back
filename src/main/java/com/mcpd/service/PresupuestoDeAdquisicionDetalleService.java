package com.mcpd.service;

import com.mcpd.model.PresupuestoDeAdquisicionDetalle;
import com.mcpd.repository.PresupuestoDeAdquisicionDetalleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<PresupuestoDeAdquisicionDetalle> findByNumeroPresupuesto(Long numeroPresupuesto) {
        return repository.findByComprasadquisicionpresupuestoNumero(numeroPresupuesto);
    }

    public List<PresupuestoDeAdquisicionDetalle> saveAll(List<PresupuestoDeAdquisicionDetalle> detalles) {
        return repository.saveAll(detalles);
    }

    @Transactional
    public void deleteByNumeroPresupuesto(Long numeroPresupuesto) {
        repository.deleteByNumeroPresupuesto(numeroPresupuesto);
    }
}
