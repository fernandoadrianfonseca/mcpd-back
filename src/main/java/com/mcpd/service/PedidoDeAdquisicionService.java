package com.mcpd.service;

import com.mcpd.model.PedidoDeAdquisicion;
import com.mcpd.repository.PedidoDeAdquisicionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoDeAdquisicionService {

    private final PedidoDeAdquisicionRepository repository;

    public PedidoDeAdquisicionService(PedidoDeAdquisicionRepository repository) {
        this.repository = repository;
    }

    /*public List<PedidoDeAdquisicion> findAll() {
        return repository.findAll();
    }*/

    public List<PedidoDeAdquisicion> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "fechaSolicitud"));
    }

    public Optional<PedidoDeAdquisicion> findById(Long id) {
        return repository.findById(id);
    }

    public PedidoDeAdquisicion save(PedidoDeAdquisicion pedido) {
        return repository.save(pedido);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
