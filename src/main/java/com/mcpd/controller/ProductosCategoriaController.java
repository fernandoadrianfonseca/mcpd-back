package com.mcpd.controller;

import com.mcpd.model.ProductosCategoria;
import com.mcpd.service.ProductosCategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la administración de categorías de productos.
 *
 * <p>
 * Expone endpoints CRUD bajo la ruta base {@code /categorias}.
 * </p>
 *
 * Permite:
 * <ul>
 *   <li>Listar categorías</li>
 *   <li>Consultar por id</li>
 *   <li>Crear nuevas categorías</li>
 *   <li>Actualizar existentes</li>
 *   <li>Eliminar registros</li>
 * </ul>
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias")
public class ProductosCategoriaController {

    private final ProductosCategoriaService service;

    public ProductosCategoriaController(ProductosCategoriaService service) {
        this.service = service;
    }

    /** Obtiene todas las categorías. */
    @GetMapping
    public List<ProductosCategoria> getAll() {
        return service.getAll();
    }

    /**
     * Obtiene una categoría por id.
     *
     * @param id identificador.
     * @return 200 si existe, 404 si no.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductosCategoria> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva categoría.
     *
     * @param entity entidad a crear.
     * @return categoría persistida.
     */
    @PostMapping
    public ProductosCategoria create(@RequestBody ProductosCategoria entity) {
        return service.save(entity);
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id identificador de la categoría.
     * @param entity datos actualizados.
     * @return 200 si se actualiza correctamente, 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductosCategoria> update(@PathVariable Integer id, @RequestBody ProductosCategoria entity) {
        Optional<ProductosCategoria> existing = service.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    /**
     * Elimina una categoría por id.
     *
     * @param id identificador.
     * @return 204 si se elimina correctamente, 404 si no existe.
     */
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
