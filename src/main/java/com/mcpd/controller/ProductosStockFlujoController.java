package com.mcpd.controller;

import com.mcpd.dto.PrestamoPendienteDto;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.service.ProductosStockFlujoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock-flujo")
@CrossOrigin(origins = "*")
public class ProductosStockFlujoController {

    private final ProductosStockFlujoService service;

    public ProductosStockFlujoController(ProductosStockFlujoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductosStockFlujo> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosStockFlujo> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosStockFlujo create(@RequestBody ProductosStockFlujo entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosStockFlujo> update(@PathVariable Integer id, @RequestBody ProductosStockFlujo entity) {
        Optional<ProductosStockFlujo> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosStockFlujo> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto-stock/{id}")
    public List<ProductosStockFlujo> getByProductoStockId(@PathVariable Integer id) {
        return service.findByProductoStockId(id);
    }

    @GetMapping("/producto-stock/{id}/remitos")
    public List<ProductosStockFlujo> getRemitosByProductoStockId(@PathVariable Integer id) {
        return service.findRemitosByProductoStockId(id);
    }

    @GetMapping("/producto-stock/{id}/custodias-activas")
    public List<ProductosStockFlujo> getCustodiasActivasByProductoStockId(@PathVariable Integer id) {
        return service.findCustodiasPorProducto(id);
    }

    @GetMapping("/producto-stock/{id}/flujos-altas-bajas")
    public List<ProductosStockFlujo> getAltasYBajasByProductoStockId(
            @PathVariable Integer id,
            @RequestParam(required = false) Long legajoCustodia) {
        return service.findFlujosAltasYBajasByProductoStockId(id, legajoCustodia);
    }

    @GetMapping("/pendientes-devolucion")
    public List<PrestamoPendienteDto> getPrestamosPendientesPorLegajo(@RequestParam Long legajo) {
        return service.getPrestamosPendientesPorLegajo(legajo);
    }
}
