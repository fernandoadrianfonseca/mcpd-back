package com.mcpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad de auditoría para el módulo de reportes.
 *
 * <p>
 * Registra cada ejecución de un reporte generado por el sistema,
 * almacenando:
 * <ul>
 *   <li>Identificador de operación (idReporte / código de operación)</li>
 *   <li>Nombre de la plantilla ejecutada</li>
 *   <li>Usuario que generó el reporte (legajo y nombre)</li>
 *   <li>Fecha y hora de generación</li>
 *   <li>Payload asociado (parámetros y datos utilizados)</li>
 * </ul>
 *
 * <p>
 * La finalidad principal es trazabilidad y auditoría:
 * qué reporte se generó, cuándo, por quién y con qué datos.
 */
@Entity
@Table(name = "reportes_log")
public class ReportesLog {

    /** Identificador interno autogenerado del registro de auditoría. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador de operación del reporte (código de trazabilidad).
     * Ejemplo: OP-<epochSecond>.
     */
    @Column(name = "id_reporte", nullable = false)
    private String idReporte;

    /** Nombre del reporte/plantilla ejecutada (nombre del JRXML sin extensión). */
    @Column(name = "reporte_nombre", nullable = false)
    private String reporteNombre;

    /** Legajo del usuario que generó el reporte. */
    @Column(name = "reporte_usuario", nullable = false)
    private Long reporteUsuario;

    /** Nombre visible del usuario que generó el reporte. */
    @Column(name = "reporte_usuario_nombre", nullable = false)
    private String reporteUsuarioNombre;

    /** Fecha y hora en que se generó el reporte. Se setea automáticamente en @PrePersist. */
    @Column(name = "reporte_fecha", nullable = false)
    private LocalDateTime reporteFecha;

    /**
     * Datos de auditoría asociados a la ejecución.
     *
     * Contiene representación serializada de parámetros y lista de datos.
     * Se almacena en NVARCHAR(MAX) para soportar payloads extensos.
     */
    @Column(name = "reporte_datos", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String reporteDatos;

    /**
     * Inicializa automáticamente la fecha de generación del reporte
     * al momento de persistir el registro.
     */
    @PrePersist
    public void prePersist() {
        this.reporteFecha = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public String getReporteNombre() {
        return reporteNombre;
    }

    public void setReporteNombre(String reporteNombre) {
        this.reporteNombre = reporteNombre;
    }

    public Long getReporteUsuario() {
        return reporteUsuario;
    }

    public void setReporteUsuario(Long reporteUsuario) {
        this.reporteUsuario = reporteUsuario;
    }

    public String getReporteUsuarioNombre() {
        return reporteUsuarioNombre;
    }

    public void setReporteUsuarioNombre(String reporteUsuarioNombre) {
        this.reporteUsuarioNombre = reporteUsuarioNombre;
    }

    public LocalDateTime getReporteFecha() {
        return reporteFecha;
    }

    public void setReporteFecha(LocalDateTime reporteFecha) {
        this.reporteFecha = reporteFecha;
    }

    public String getReporteDatos() {
        return reporteDatos;
    }

    public void setReporteDatos(String reporteDatos) {
        this.reporteDatos = reporteDatos;
    }
}
