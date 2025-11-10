package com.mcpd.controller;

import com.mcpd.dto.ProductosInformacionDto;
import com.mcpd.model.ProductosInformacion;
import com.mcpd.service.ProductosInformacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/informacion")
@CrossOrigin(origins = "*")
public class ProductosInformacionController {

    private final ProductosInformacionService service;

    public ProductosInformacionController(ProductosInformacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductosInformacion> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosInformacion> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosInformacion create(@RequestBody ProductosInformacion entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosInformacion> update(@PathVariable Integer id, @RequestBody ProductosInformacion entity) {
        Optional<ProductosInformacion> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosInformacion> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flujo/{id}")
    public List<ProductosInformacion> getByProductoFlujoId(@PathVariable Integer id) {
        return service.findByProductoFlujoId(id);
    }

    @GetMapping("/producto-stock/{productoStockId}")
    public List<ProductosInformacion> getByProductoStockId(
            @PathVariable Integer productoStockId,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Long empleadoCustodia) {

        return service.findByProductoStockId(
                productoStockId,
                activo != null ? activo : true,
                empleadoCustodia
        );
    }

    @PostMapping("/lote")
    public List<ProductosInformacion> crearEnLote(@RequestBody List<ProductosInformacion> lista) {
        return service.saveAll(lista);
    }

    @GetMapping("/producto-stock/{productoStockId}/sin-custodia")
    public List<ProductosInformacion> getActivosSinCustodiaPorProductoStock(@PathVariable Integer productoStockId) {
        return service.obtenerNumerosDeSerieActivosSinCustodia(productoStockId);
    }

    @PutMapping("/asignar-custodia")
    public List<ProductosInformacionDto> asignarCustodia(
            @RequestBody List<Integer> ids,
            @RequestParam(value = "legajo", required = false) Long legajo) {
        return service.asignarCustodia(ids, legajo);
    }

    @PutMapping("/darDeBaja")
    public List<ProductosInformacion> darDeBajaNumerosDeSerie(@RequestBody List<Integer> ids) {
        return service.darDeBajaNumerosDeSerie(ids);
    }
}
