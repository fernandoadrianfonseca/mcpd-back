package com.mcpd.controller;

import com.mcpd.model.ProductosCategoria;
import com.mcpd.service.ProductosCategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias")
public class ProductosCategoriaController {

    private final ProductosCategoriaService service;

    public ProductosCategoriaController(ProductosCategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductosCategoria> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosCategoria> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosCategoria create(@RequestBody ProductosCategoria entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosCategoria> update(@PathVariable Integer id, @RequestBody ProductosCategoria entity) {
        Optional<ProductosCategoria> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosCategoria> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
