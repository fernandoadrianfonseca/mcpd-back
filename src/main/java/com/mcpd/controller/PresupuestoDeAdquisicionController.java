package com.mcpd.controller;

import com.mcpd.model.PresupuestoDeAdquisicion;
import com.mcpd.service.PresupuestoDeAdquisicionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presupuestos")
@CrossOrigin(origins = "*")
public class PresupuestoDeAdquisicionController {

    private final PresupuestoDeAdquisicionService service;

    public PresupuestoDeAdquisicionController(PresupuestoDeAdquisicionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PresupuestoDeAdquisicion> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresupuestoDeAdquisicion> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PresupuestoDeAdquisicion create(@RequestBody PresupuestoDeAdquisicion presupuesto) {
        return service.save(presupuesto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresupuestoDeAdquisicion> update(@PathVariable Long id, @RequestBody PresupuestoDeAdquisicion presupuesto) {
        return service.findById(id).map(existing -> {
            presupuesto.setNumero(id);
            return ResponseEntity.ok(service.save(presupuesto));
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

    @GetMapping("/porPedido/{numeroPedido}")
    public List<PresupuestoDeAdquisicion> getByNumeroPedido(@PathVariable Long numeroPedido) {
        return service.findByPedidoNumero(numeroPedido);
    }
}
