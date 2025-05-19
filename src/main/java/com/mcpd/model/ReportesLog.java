package com.mcpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes_log")
public class ReportesLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_reporte", nullable = false)
    private String idReporte;

    @Column(name = "reporte_nombre", nullable = false)
    private String reporteNombre;

    @Column(name = "reporte_usuario", nullable = false)
    private Long reporteUsuario;

    @Column(name = "reporte_usuario_nombre", nullable = false)
    private String reporteUsuarioNombre;

    @Column(name = "reporte_fecha", nullable = false)
    private LocalDateTime reporteFecha;

    @Column(name = "reporte_datos", columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String reporteDatos;

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
