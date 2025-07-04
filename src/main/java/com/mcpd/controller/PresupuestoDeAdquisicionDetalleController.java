package com.mcpd.controller;

import com.mcpd.model.PresupuestoDeAdquisicionDetalle;
import com.mcpd.service.PresupuestoDeAdquisicionDetalleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presupuestos-detalle")
@CrossOrigin(origins = "*")
public class PresupuestoDeAdquisicionDetalleController {

    private final PresupuestoDeAdquisicionDetalleService service;

    public PresupuestoDeAdquisicionDetalleController(PresupuestoDeAdquisicionDetalleService service) {
        this.service = service;
    }

    @GetMapping
    public List<PresupuestoDeAdquisicionDetalle> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresupuestoDeAdquisicionDetalle> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PresupuestoDeAdquisicionDetalle create(@RequestBody PresupuestoDeAdquisicionDetalle detalle) {
        return service.save(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PresupuestoDeAdquisicionDetalle> update(@PathVariable Long id, @RequestBody PresupuestoDeAdquisicionDetalle detalle) {
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
