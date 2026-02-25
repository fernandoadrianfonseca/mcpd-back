package com.mcpd.controller;

import com.mcpd.model.ReportesLog;
import com.mcpd.service.ReportesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la consulta y administración de registros
 * de auditoría de reportes ({@link ReportesLog}).
 *
 * <p>
 * Expone endpoints para:
 * <ul>
 *   <li>Consultar todos los registros</li>
 *   <li>Buscar por id</li>
 *   <li>Filtrar por usuario (legajo)</li>
 *   <li>Filtrar por nombre de reporte</li>
 *   <li>Filtrar por rango de fechas</li>
 * </ul>
 *
 * <p>
 * Este controller no genera reportes, únicamente permite
 * consultar y administrar la trazabilidad almacenada
 * por {@link com.mcpd.service.ReporteService}.
 */
@RestController
@RequestMapping("/reportes-log")
@CrossOrigin(origins = "*")
public class ReportesLogController {

    private final ReportesLogService service;

    @Autowired
    public ReportesLogController(ReportesLogService service) {
        this.service = service;
    }

    /**
     * Obtiene todos los registros de auditoría de reportes.
     *
     * @return lista completa de {@link ReportesLog}.
     */
    @GetMapping
    public List<ReportesLog> getAll() {
        return service.findAll();
    }

    /**
     * Obtiene un registro de auditoría por id.
     *
     * @param id identificador del log.
     * @return Optional con el registro si existe.
     */
    @GetMapping("/{id}")
    public Optional<ReportesLog> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * Crea manualmente un registro de auditoría.
     *
     * <p>
     * Normalmente los registros son creados automáticamente
     * por {@link com.mcpd.service.ReporteService}, pero este
     * endpoint permite inserciones manuales si fuera necesario.
     *
     * @param reportesLog entidad a persistir.
     * @return registro guardado.
     */
    @PostMapping
    public ReportesLog create(@RequestBody ReportesLog reportesLog) {
        return service.save(reportesLog);
    }

    /**
     * Actualiza un registro de auditoría existente.
     *
     * @param id identificador del registro.
     * @param reportesLog entidad con los datos actualizados.
     * @return registro persistido.
     */
    @PutMapping("/{id}")
    public ReportesLog update(@PathVariable Long id, @RequestBody ReportesLog reportesLog) {
        reportesLog.setId(id);
        return service.save(reportesLog);
    }

    /**
     * Elimina un registro de auditoría por id.
     *
     * @param id identificador del registro.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    /**
     * Obtiene los registros de auditoría generados por un usuario específico.
     *
     * @param usuarioId legajo del usuario.
     * @return lista de reportes generados por el usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    public List<ReportesLog> getByUsuario(@PathVariable Long usuarioId) {
        return service.findByUsuario(usuarioId);
    }

    /**
     * Busca registros de auditoría por nombre de reporte (búsqueda parcial).
     *
     * @param nombre texto parcial del nombre del reporte.
     * @return lista de registros coincidentes.
     */
    @GetMapping("/buscar")
    public List<ReportesLog> searchByNombre(@RequestParam String nombre) {
        return service.findByNombre(nombre);
    }

    /**
     * Obtiene registros de auditoría dentro de un rango de fechas.
     *
     * <p>
     * Los parámetros deben enviarse en formato ISO-8601 compatible
     * con {@link java.time.LocalDateTime#parse(CharSequence)}.
     *
     * Ejemplo:
     * 2026-02-25T00:00:00
     *
     * @param inicio fecha/hora inicial (inclusive).
     * @param fin fecha/hora final (inclusive).
     * @return lista de registros dentro del rango.
     */
    @GetMapping("/fecha")
    public List<ReportesLog> getByFecha(@RequestParam String inicio, @RequestParam String fin) {
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return service.findByFechaEntre(fechaInicio, fechaFin);
    }
}
