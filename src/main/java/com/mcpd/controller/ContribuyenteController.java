package com.mcpd.controller;

import com.mcpd.model.Contribuyente;
import com.mcpd.service.ContribuyenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la administración de contribuyentes.
 *
 * <p>
 * Expone endpoints CRUD bajo la ruta base {@code /contribuyentes}.
 * </p>
 *
 * Permite:
 * <ul>
 *   <li>Listar contribuyentes</li>
 *   <li>Consultar por CUIT</li>
 *   <li>Crear nuevos registros</li>
 *   <li>Actualizar datos existentes</li>
 *   <li>Eliminar registros</li>
 * </ul>
 *
 * <p>
 * Es utilizado indirectamente por el módulo {@link Proveedor}.
 * </p>
 */
@RestController
@RequestMapping("/contribuyentes")
@CrossOrigin(origins = "*") // Permite llamadas desde cualquier frontend
public class ContribuyenteController {

    @Autowired
    private ContribuyenteService contribuyenteService;

    /** Obtiene todos los contribuyentes. */
    @GetMapping
    public List<Contribuyente> obtenerTodos() {
        return contribuyenteService.obtenerTodos();
    }

    /**
     * Obtiene un contribuyente por CUIT.
     *
     * @param cuit identificador único.
     * @return 200 si existe, 404 si no.
     */
    @GetMapping("/{cuit}")
    public ResponseEntity<Contribuyente> obtenerPorCuit(@PathVariable Long cuit) {
        Optional<Contribuyente> contribuyente = contribuyenteService.obtenerPorCuit(cuit);
        return contribuyente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo contribuyente.
     *
     * @param contribuyente entidad a crear.
     * @return contribuyente persistido.
     */
    @PostMapping
    public Contribuyente guardar(@RequestBody Contribuyente contribuyente) {
        return contribuyenteService.guardar(contribuyente);
    }

    /**
     * Actualiza un contribuyente existente.
     *
     * @param cuit identificador.
     * @param contribuyente datos actualizados.
     * @return 200 si se actualiza correctamente, 404 si no existe.
     */
    @PutMapping("/{cuit}")
    public ResponseEntity<Contribuyente> actualizar(@PathVariable Long cuit, @RequestBody Contribuyente contribuyente) {
        Optional<Contribuyente> actualizado = contribuyenteService.actualizar(cuit, contribuyente);
        return actualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina un contribuyente por CUIT.
     *
     * @param cuit identificador.
     * @return 204 si se elimina correctamente.
     */
    @DeleteMapping("/{cuit}")
    public ResponseEntity<Void> eliminar(@PathVariable Long cuit) {
        contribuyenteService.eliminar(cuit);
        return ResponseEntity.noContent().build();
    }
}
