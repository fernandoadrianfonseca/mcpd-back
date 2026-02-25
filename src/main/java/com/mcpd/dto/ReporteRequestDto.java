package com.mcpd.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO de request utilizado para solicitar la generación de reportes PDF con JasperReports.
 *
 * <p>
 * El frontend construye este objeto y lo envía al backend para indicar:
 * - Qué plantilla JRXML se debe ejecutar
 * - Qué parámetros simples se inyectan en el reporte
 * - Qué lista de datos se utiliza como datasource (si corresponde)
 * - Quién generó el reporte y cuántas copias se requieren
 *
 * <h3>Contrato</h3>
 * <ul>
 *   <li><b>nombreReporte</b>: Nombre del archivo .jrxml (sin extensión) dentro de {@code resources/reportes}.</li>
 *   <li><b>generaReporteLegajo</b>: Legajo del usuario que genera el reporte (auditoría).</li>
 *   <li><b>generaReporteNombre</b>: Nombre del usuario que genera el reporte (auditoría).</li>
 *   <li><b>cantidadCopias</b>: Cantidad de copias/duplicación de páginas dentro del PDF (default 1).</li>
 *   <li><b>parametros</b>: Mapa key/value con parámetros declarados en el JRXML.</li>
 *   <li><b>datos</b>: Lista de beans para {@link net.sf.jasperreports.engine.data.JRBeanCollectionDataSource}.</li>
 * </ul>
 *
 * <p>
 * Nota: el backend compila el JRXML en tiempo de ejecución y devuelve el PDF como {@code byte[]}.
 */

public class ReporteRequestDto {

    /** Nombre del reporte JRXML (sin extensión) ubicado en {@code resources/reportes}. */
    private String nombreReporte;

    /** Legajo del usuario que genera el reporte (auditoría / logging). */
    private String generaReporteLegajo;

    /** Nombre del usuario que genera el reporte (auditoría / logging). */
    private String generaReporteNombre;

    /** Cantidad de copias del PDF (duplicación de páginas). Si es null o <= 0, se asume 1. */
    private Integer cantidadCopias;

    /** Parámetros simples a inyectar al JRXML (deben existir como $P{...} en la plantilla). */
    private Map<String, Object> parametros = new HashMap<>();

    /** Lista de datos para el reporte (se utiliza como datasource si el JRXML declara fields). */
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
