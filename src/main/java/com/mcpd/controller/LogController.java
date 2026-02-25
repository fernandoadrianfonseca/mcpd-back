package com.mcpd.controller;

import com.mcpd.model.Log;
import com.mcpd.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la gestión de logs heredados
 * del sistema legacy.
 *
 * <p>
 * Expone endpoints CRUD simples sobre la tabla
 * {@code seguridadoperadorlog}.
 *
 * <p>
 * Nota:
 * Este controller existe por compatibilidad histórica.
 * No debe confundirse con el nuevo sistema de auditoría
 * basado en {@link com.mcpd.model.ReportesLog}.
 */
@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    private LogService logService;

    /** Obtiene todos los logs legacy. */
    @GetMapping
    public List<Log> obtenerTodos() {
        return logService.obtenerTodos();
    }

    /**
     * Obtiene un log legacy por id.
     *
     * @param id identificador del registro.
     * @return ResponseEntity con el log o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Log> obtenerPorId(@PathVariable Long id) {
        Optional<Log> log = logService.obtenerPorId(id);
        return log.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo registro legacy.
     *
     * @param log entidad a guardar.
     * @return registro persistido.
     */
    @PostMapping
    public Log guardar(@RequestBody Log log) {
        return logService.guardar(log);
    }

    /**
     * Elimina un registro legacy por id.
     *
     * @param id identificador del registro.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

