package com.mcpd.controller;

import com.mcpd.model.PedidoDeAdquisicionDetalle;
import com.mcpd.service.PedidoDeAdquisicionDetalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos-detalle")
@CrossOrigin(origins = "*")
public class PedidoDeAdquisicionDetalleController {

    private final PedidoDeAdquisicionDetalleService service;

    public PedidoDeAdquisicionDetalleController(PedidoDeAdquisicionDetalleService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDeAdquisicionDetalle> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDeAdquisicionDetalle> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PedidoDeAdquisicionDetalle create(@RequestBody PedidoDeAdquisicionDetalle detalle) {
        return service.save(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDeAdquisicionDetalle> update(@PathVariable Long id, @RequestBody PedidoDeAdquisicionDetalle detalle) {
        return service.findById(id).map(existing -> {
            detalle.setId(id);
            return ResponseEntity.ok(service.save(detalle));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
