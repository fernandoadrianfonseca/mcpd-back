package com.mcpd.controller;

import com.mcpd.model.Proveedor;
import com.mcpd.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la administración de proveedores.
 *
 * <p>
 * Expone endpoints CRUD bajo la ruta base {@code /proveedores}.
 * </p>
 *
 * Permite:
 * <ul>
 *   <li>Listar proveedores (con nombre de contribuyente).</li>
 *   <li>Consultar proveedor por CUIT.</li>
 *   <li>Crear nuevos proveedores.</li>
 *   <li>Actualizar proveedores existentes.</li>
 *   <li>Eliminar proveedores.</li>
 * </ul>
 */
@RestController
@RequestMapping("/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Obtiene todos los proveedores con nombre enriquecido.
     *
     * @return lista de proveedores.
     */
    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodosConNombre();
    }

    /**
     * Obtiene un proveedor por CUIT.
     *
     * @param cuit identificador único.
     * @return 200 si existe, 404 si no.
     */
    @GetMapping("/{cuit}")
    public ResponseEntity<Proveedor> obtenerPorCuit(@PathVariable Long cuit) {
        Optional<Proveedor> proveedor = proveedorService.obtenerPorCuit(cuit);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo proveedor.
     *
     * @param proveedor entidad a crear.
     * @return proveedor persistido.
     */
    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    /**
     * Actualiza un proveedor existente.
     *
     * @param cuit identificador.
     * @param proveedor datos actualizados.
     * @return 200 si se actualiza correctamente, 404 si no existe.
     */
    @PutMapping("/{cuit}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long cuit, @RequestBody Proveedor proveedor) {
        if (!proveedorService.obtenerPorCuit(cuit).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        proveedor.setCuit(cuit);
        return ResponseEntity.ok(proveedorService.guardar(proveedor));
    }

    /**
     * Elimina un proveedor por CUIT.
     *
     * @param cuit identificador.
     * @return 204 si se elimina correctamente.
     */
    @DeleteMapping("/{cuit}")
    public ResponseEntity<Void> eliminar(@PathVariable Long cuit) {
        proveedorService.eliminar(cuit);
        return ResponseEntity.noContent().build();
    }
}

