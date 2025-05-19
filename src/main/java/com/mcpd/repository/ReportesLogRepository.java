package com.mcpd.repository;

import com.mcpd.model.ReportesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportesLogRepository extends JpaRepository<ReportesLog, Long> {
    List<ReportesLog> findByReporteUsuario(Long reporteUsuario);
    List<ReportesLog> findByReporteFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<ReportesLog> findByReporteNombreContaining(String reporteNombre);
}
