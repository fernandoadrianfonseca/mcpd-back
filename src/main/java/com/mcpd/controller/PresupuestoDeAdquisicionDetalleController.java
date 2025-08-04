package com.mcpd.controller;

import com.mcpd.model.PresupuestoDeAdquisicionDetalle;
import com.mcpd.service.PresupuestoDeAdquisicionDetalleService;
import com.mcpd.service.PresupuestoDeAdquisicionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presupuestos-detalle")
@CrossOrigin(origins = "*")
public class PresupuestoDeAdquisicionDetalleController {

    private final PresupuestoDeAdquisicionDetalleService service;
    private final PresupuestoDeAdquisicionService presupuestoService;

    public PresupuestoDeAdquisicionDetalleController(PresupuestoDeAdquisicionDetalleService service, PresupuestoDeAdquisicionService presupuestoService) {
        this.service = service;
        this.presupuestoService = presupuestoService;
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

    @GetMapping("/por-presupuesto/{numeroPresupuesto}")
    public List<PresupuestoDeAdquisicionDetalle> getByPresupuesto(@PathVariable Long numeroPresupuesto) {
        return service.findByNumeroPresupuesto(numeroPresupuesto);
    }

    @PostMapping("/lote")
    public List<PresupuestoDeAdquisicionDetalle> createLote(@RequestBody List<PresupuestoDeAdquisicionDetalle> detalles) {

        if (detalles.isEmpty()) return List.of();

        Long numeroPresupuesto = detalles.get(0).getComprasadquisicionpresupuesto().getNumero();

        // Borrar los existentes
        service.deleteByNumeroPresupuesto(numeroPresupuesto);

        // Guardar los nuevos
        List<PresupuestoDeAdquisicionDetalle> nuevos = service.saveAll(detalles);

        // Actualizar total
        double total = nuevos.stream()
                .mapToDouble(d -> d.getMontoUnitario() * d.getCantidad())
                .sum();

        presupuestoService.actualizarTotalPresupuesto(numeroPresupuesto, total);

        return nuevos;
    }
}
