package com.mcpd.controller;

import com.mcpd.dto.ProductosNumeroDeSerieDto;
import com.mcpd.model.ProductosNumeroDeSerie;
import com.mcpd.service.ProductosNumeroDeSerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/numeros-de-serie")
@CrossOrigin(origins = "*")
public class ProductosNumeroDeSerieController {

    private final ProductosNumeroDeSerieService service;

    public ProductosNumeroDeSerieController(ProductosNumeroDeSerieService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductosNumeroDeSerie> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosNumeroDeSerie> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosNumeroDeSerie create(@RequestBody ProductosNumeroDeSerie entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosNumeroDeSerie> update(@PathVariable Integer id, @RequestBody ProductosNumeroDeSerie entity) {
        Optional<ProductosNumeroDeSerie> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosNumeroDeSerie> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/flujo/{id}")
    public List<ProductosNumeroDeSerie> getByProductoFlujoId(@PathVariable Integer id) {
        return service.findByProductoFlujoId(id);
    }

    @GetMapping("/producto-stock/{productoStockId}")
    public List<ProductosNumeroDeSerie> getByProductoStockId(
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
    public List<ProductosNumeroDeSerie> crearEnLote(@RequestBody List<ProductosNumeroDeSerie> lista) {
        return service.saveAll(lista);
    }

    @GetMapping("/producto-stock/{productoStockId}/sin-custodia")
    public List<ProductosNumeroDeSerie> getActivosSinCustodiaPorProductoStock(@PathVariable Integer productoStockId) {
        return service.obtenerNumerosDeSerieActivosSinCustodia(productoStockId);
    }

    @PutMapping("/asignar-custodia")
    public List<ProductosNumeroDeSerieDto> asignarCustodia(
            @RequestBody List<Integer> ids,
            @RequestParam(value = "legajo", required = false) Long legajo) {
        return service.asignarCustodia(ids, legajo);
    }

    @PutMapping("/darDeBaja")
    public List<ProductosNumeroDeSerie> darDeBajaNumerosDeSerie(@RequestBody List<Integer> ids) {
        return service.darDeBajaNumerosDeSerie(ids);
    }
}
