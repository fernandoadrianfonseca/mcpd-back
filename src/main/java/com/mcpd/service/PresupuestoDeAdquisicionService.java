package com.mcpd.service;

import com.mcpd.model.PresupuestoDeAdquisicion;
import com.mcpd.repository.PresupuestoDeAdquisicionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresupuestoDeAdquisicionService {

    private final PresupuestoDeAdquisicionRepository repository;

    public PresupuestoDeAdquisicionService(PresupuestoDeAdquisicionRepository repository) {
        this.repository = repository;
    }

    public List<PresupuestoDeAdquisicion> findAll() {
        return repository.findAll();
    }

    public Optional<PresupuestoDeAdquisicion> findById(Long id) {
        return repository.findById(id);
    }

    public PresupuestoDeAdquisicion save(PresupuestoDeAdquisicion presupuesto) {
        return repository.save(presupuesto);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<PresupuestoDeAdquisicion> findByPedidoNumero(Long numeroPedido) {
        return repository.findByPedido(numeroPedido);
    }

    public void actualizarTotalPresupuesto(Long numeroPresupuesto, double total) {
        repository.findById(numeroPresupuesto).ifPresent(presupuesto -> {
            presupuesto.setTotal(total);
            repository.save(presupuesto);
        });
    }
}
