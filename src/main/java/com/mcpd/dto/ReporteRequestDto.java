package com.mcpd.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReporteRequestDto {
    private String nombreReporte;
    private String generaReporteLegajo;
    private String generaReporteNombre;
    private Integer cantidadCopias;
    private Map<String, Object> parametros = new HashMap<>();
    private List<?> datos;

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getGeneraReporteLegajo() {
        return generaReporteLegajo;
    }

    public void setGeneraReporteLegajo(String generaReporteLegajo) {
        this.generaReporteLegajo = generaReporteLegajo;
    }

    public String getGeneraReporteNombre() {
        return generaReporteNombre;
    }

    public void setGeneraReporteNombre(String generaReporteNombre) {
        this.generaReporteNombre = generaReporteNombre;
    }

    public Integer getCantidadCopias() {
        return cantidadCopias;
    }

    public void setCantidadCopias(Integer cantidadCopias) {
        this.cantidadCopias = cantidadCopias;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }

    public List<?> getDatos() {
        return datos;
    }

    public void setDatos(List<?> datos) {
        this.datos = datos;
    }
}
