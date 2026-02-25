package com.mcpd.controller;

import com.mcpd.model.Producto;
import com.mcpd.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la administración del catálogo de productos.
 *
 * <p>
 * Expone endpoints CRUD bajo la ruta base {@code /productos}.
 * </p>
 *
 * Permite:
 * <ul>
 *   <li>Listar productos</li>
 *   <li>Consultar por id</li>
 *   <li>Crear nuevos productos</li>
 *   <li>Actualizar productos existentes</li>
 *   <li>Eliminar productos</li>
 * </ul>
 *
 * <p>
 * No gestiona stock ni custodia, ya que esas responsabilidades
 * corresponden al módulo {@code ProductosStock}.
 * </p>
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /** Obtiene todos los productos. */
    @GetMapping
    public List<Producto> getAll() {
        return service.getAll();
    }

    /**
     * Obtiene un producto por id.
     *
     * @param id identificador.
     * @return 200 si existe, 404 si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo producto.
     *
     * @param entity entidad a crear.
     * @return producto persistido.
     */
    @PostMapping
    public Producto create(@RequestBody Producto entity) {
        return service.save(entity);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id identificador del producto.
     * @param entity datos actualizados.
     * @return 200 si se actualiza correctamente, 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Integer id, @RequestBody Producto entity) {
        Optional<Producto> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    /**
     * Elimina un producto por id.
     *
     * @param id identificador.
     * @return 204 si se elimina correctamente, 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Producto> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
