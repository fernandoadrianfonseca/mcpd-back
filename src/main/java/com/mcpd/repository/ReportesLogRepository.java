package com.mcpd.repository;

import com.mcpd.model.ReportesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link ReportesLog}.
 *
 * <p>
 * Provee consultas de auditoría para:
 * - Buscar reportes generados por un usuario (legajo)
 * - Filtrar por rango de fechas
 * - Buscar por nombre de reporte (búsqueda parcial)
 */
public interface ReportesLogRepository extends JpaRepository<ReportesLog, Long> {

    /**
     * Obtiene los logs de reportes generados por un usuario específico.
     *
     * @param reporteUsuario legajo del usuario.
     * @return lista de logs del usuario.
     */
    List<ReportesLog> findByReporteUsuario(Long reporteUsuario);

    /**
     * Obtiene los logs generados dentro de un rango de fechas (inclusive).
     *
     * @param fechaInicio fecha/hora de inicio.
     * @param fechaFin fecha/hora de fin.
     * @return lista de logs dentro del rango.
     */
    List<ReportesLog> findByReporteFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * Busca logs cuyo nombre de reporte contenga el texto indicado.
     *
     * @param reporteNombre texto parcial del nombre del reporte.
     * @return lista de logs que coinciden.
     */
    List<ReportesLog> findByReporteNombreContaining(String reporteNombre);
}
