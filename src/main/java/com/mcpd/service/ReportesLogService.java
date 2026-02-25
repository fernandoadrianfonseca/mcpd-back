package com.mcpd.service;

import com.mcpd.model.ReportesLog;
import com.mcpd.repository.ReportesLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de dominio para la gestión de auditoría de reportes.
 *
 * <p>
 * Encapsula operaciones CRUD y búsquedas comunes sobre {@link ReportesLog},
 * permitiendo consultar la trazabilidad de reportes generados por:
 * - Usuario (legajo)
 * - Nombre de reporte (búsqueda parcial)
 * - Rango de fechas
 *
 * Este servicio no genera reportes; únicamente gestiona los registros
 * de auditoría generados por {@link com.mcpd.service.ReporteService}.
 */
@Service
public class ReportesLogService {

    private final ReportesLogRepository repository;

    @Autowired
    public ReportesLogService(ReportesLogRepository repository) {
        this.repository = repository;
    }

    /** Obtiene todos los registros de auditoría de reportes. */
    public List<ReportesLog> findAll() {
        return repository.findAll();
    }

    /**
     * Obtiene un log de reporte por id.
     *
     * @param id id del registro.
     * @return Optional con el log si existe.
     */
    public Optional<ReportesLog> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Persiste un registro de auditoría de reporte.
     *
     * @param entity log a guardar.
     * @return entidad persistida.
     */
    public ReportesLog save(ReportesLog entity) {
        return repository.save(entity);
    }

    /**
     * Elimina un registro de auditoría por id.
     *
     * @param id id del registro.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Obtiene logs de reportes generados por un usuario.
     *
     * @param reporteUsuario legajo del usuario.
     * @return lista de logs.
     */
    public List<ReportesLog> findByUsuario(Long reporteUsuario) {
        return repository.findByReporteUsuario(reporteUsuario);
    }

    /**
     * Busca logs por nombre de reporte (búsqueda parcial).
     *
     * @param nombre texto parcial.
     * @return lista de logs.
     */
    public List<ReportesLog> findByNombre(String nombre) {
        return repository.findByReporteNombreContaining(nombre);
    }

    /**
     * Obtiene logs dentro de un rango de fechas.
     *
     * @param inicio fecha/hora de inicio.
     * @param fin fecha/hora de fin.
     * @return lista de logs en el rango.
     */
    public List<ReportesLog> findByFechaEntre(LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByReporteFechaBetween(inicio, fin);
    }
}
