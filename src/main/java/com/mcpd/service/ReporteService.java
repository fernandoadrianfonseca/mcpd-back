package com.mcpd.service;

import com.mcpd.model.ReportesLog;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Servicio de generación de reportes PDF basado en JasperReports (.jrxml).
 *
 * <p>
 * Responsabilidades:
 * <ul>
 *   <li>Localizar plantillas JRXML por nombre en {@code classpath:reportes/}</li>
 *   <li>Compilar JRXML a {@link net.sf.jasperreports.engine.JasperReport}</li>
 *   <li>Ejecutar el fill con parámetros y datasource (vacío o lista)</li>
 *   <li>Exportar a PDF (byte[])</li>
 *   <li>Registrar auditoría en {@link com.mcpd.model.ReportesLog}</li>
 *   <li>(Opcional) Guardar el PDF en disco según configuración</li>
 * </ul>
 *
 * <h3>Convenciones relevantes</h3>
 * <ul>
 *   <li>Si {@code cantidadCopias} es null o <= 0, se asume 1.</li>
 *   <li>Se fuerza {@code REPORT_CLASS_LOADER} para resolver recursos/clases en Jasper.</li>
 *   <li>Se agrega parámetro {@code fecha} si no viene informado (formato dd/MM/yyyy).</li>
 *   <li>Si {@code fechaDevolucion} viene en parámetros, se parsea como Instant ISO-8601 y se formatea a dd/MM/yyyy (America/Argentina/Buenos_Aires).</li>
 *   <li>Se genera {@code codigoOperacion} para trazabilidad (OP-epochSecond).</li>
 * </ul>
 *
 * Basado en la documentación oficial del módulo de reportes. :contentReference[oaicite:1]{index=1}
 */
@Service
public class ReporteService {

    @Value("${reports.savefiles:false}")
    private boolean guardarArchivos;

    @Value("${reports.isproduction:false}")
    private boolean esProduccion;

    @Value("${reports.output-directory:}")
    private String rutaProduccion;

    private final ReportesLogService reportesLogService;
    private static final Logger logger = LoggerFactory.getLogger(ReporteService.class);

    public ReporteService(ReportesLogService reportesLogService) {
        this.reportesLogService = reportesLogService;
    }

    /**
     * Genera un reporte Jasper sin datasource (sin lista), utilizando {@link net.sf.jasperreports.engine.JREmptyDataSource}.
     *
     * <p>
     * Flujo:
     * <ol>
     *   <li>Normaliza copias (default 1)</li>
     *   <li>Enriquece parámetros (REPORT_CLASS_LOADER, fecha, fechaDevolucion formateada, codigoOperacion)</li>
     *   <li>Registra auditoría en {@link ReportesLog}</li>
     *   <li>Compila y ejecuta el JRXML</li>
     *   <li>Duplica páginas para simular copias si corresponde</li>
     *   <li>Exporta a PDF y retorna {@code byte[]}</li>
     *   <li>(Opcional) guarda el archivo en disco según configuración</li>
     * </ol>
     *
     * @param nombreReporte nombre del JRXML (sin extensión) en {@code resources/reportes}.
     * @param generaReporteLegajo legajo del usuario que genera el reporte.
     * @param generaReporteNombre nombre del usuario que genera el reporte.
     * @param parametros parámetros declarados en el JRXML.
     * @param cantidadCopias cantidad de copias (páginas duplicadas) del PDF.
     * @return PDF generado en formato {@code byte[]}.
     * @throws RuntimeException si ocurre un error al compilar/llenar/exportar el reporte.
     */
    public byte[] generarReporte(String nombreReporte,
                                 String generaReporteLegajo,
                                 String generaReporteNombre,
                                 Map<String, Object> parametros,
                                 Integer cantidadCopias) {
        try {
            int copias = (cantidadCopias != null && cantidadCopias > 0) ? cantidadCopias : 1;
            parametros.put("REPORT_CLASS_LOADER", Thread.currentThread().getContextClassLoader());
            parametros.putIfAbsent("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            String fechaDevolucionString = (String) parametros.get("fechaDevolucion");
            if (parametros.get("fechaDevolucion") != null) {
                Instant instant = Instant.parse(fechaDevolucionString);
                LocalDate fecha = instant.atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDate();
                String formateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                parametros.put("fechaDevolucion", formateada);
            }
            //parametros.putIfAbsent("codigoOperacion", "OP-" + Instant.now().toEpochMilli());
            String codigoOperacion = "OP-" + Instant.now().getEpochSecond();
            parametros.putIfAbsent("codigoOperacion", codigoOperacion);
            guardarLogReporte(codigoOperacion, nombreReporte, generaReporteLegajo, generaReporteNombre, parametros, new ArrayList<>());
            InputStream jrxmlStream = new ClassPathResource("reportes/" + nombreReporte + ".jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            for (int i = 1; i < copias; i++) {
                jasperPrint.getPages().addAll(jasperPrint.getPages());
            }
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            guardarArchivoEnDisco(nombreReporte, codigoOperacion, pdfBytes);
            return pdfBytes;
        } catch (Exception e) {
            logger.error("Error al generar reporte: " + e.getMessage(), e);
            throw new RuntimeException("Error al generar reporte: " + e.getMessage(), e);
        }
    }

    /**
     * Genera un reporte Jasper con datasource basado en una lista de beans.
     *
     * <p>
     * Utiliza {@link JRBeanCollectionDataSource} para exponer los datos como fields
     * en el JRXML (vía {@code $F{...}}).
     *
     * <p>
     * Además de los pasos de {@link #generarReporte(String, String, String, Map, Integer)},
     * registra la auditoría incluyendo los datos recibidos y ejecuta el fill con datasource.
     *
     * @param nombreReporte nombre del JRXML (sin extensión) en {@code resources/reportes}.
     * @param generaReporteLegajo legajo del usuario que genera el reporte.
     * @param generaReporteNombre nombre del usuario que genera el reporte.
     * @param parametros parámetros declarados en el JRXML.
     * @param datos lista de beans utilizada como datasource.
     * @param cantidadCopias cantidad de copias (páginas duplicadas) del PDF.
     * @return PDF generado en formato {@code byte[]}.
     * @throws RuntimeException si ocurre un error al compilar/llenar/exportar el reporte.
     */
    public byte[] generarReporteConLista(String nombreReporte,
                                         String generaReporteLegajo,
                                         String generaReporteNombre,
                                         Map<String, Object> parametros,
                                         java.util.List<?> datos,
                                         Integer cantidadCopias) {
        try {

            int copias = (cantidadCopias != null && cantidadCopias > 0) ? cantidadCopias : 1;
            parametros.put("REPORT_CLASS_LOADER", Thread.currentThread().getContextClassLoader());
            parametros.putIfAbsent("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            String fechaDevolucionString = (String) parametros.get("fechaDevolucion");
            if (parametros.get("fechaDevolucion") != null) {
                Instant instant = Instant.parse(fechaDevolucionString);
                LocalDate fecha = instant.atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDate();
                String formateada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                parametros.put("fechaDevolucion", formateada);
            }
            //parametros.putIfAbsent("codigoOperacion", "OP-" + Instant.now().toEpochMilli());
            String codigoOperacion = "OP-" + Instant.now().getEpochSecond();
            parametros.putIfAbsent("codigoOperacion", codigoOperacion);

            guardarLogReporte(codigoOperacion, nombreReporte, generaReporteLegajo, generaReporteNombre, parametros, datos);

            List<Object> duplicados = new ArrayList<>();
            for (Object item : datos) {
                duplicados.add(item);
            }
            InputStream jrxmlStream = new ClassPathResource("reportes/" + nombreReporte + ".jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(duplicados);
            JasperPrint jasperPrint  = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            for (int i = 1; i < copias; i++) {
                jasperPrint.getPages().addAll(jasperPrint.getPages());
            }
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            guardarArchivoEnDisco(nombreReporte, codigoOperacion, pdfBytes);
            return pdfBytes;
        } catch (Exception e) {
            logger.error("Error al generar reporte: " + e.getMessage(), e);
            throw new RuntimeException("Error al generar reporte con lista: " + e.getMessage(), e);
        }
    }

    /**
     * Guarda el PDF generado en disco si la propiedad {@code reports.savefiles} está habilitada.
     *
     * <p>
     * - En producción ({@code reports.isproduction=true}) guarda en {@code reports.output-directory/<yyyy-MM-dd>/}.
     * - En desarrollo guarda en {@code src/main/resources/reportes/generados/<yyyy-MM-dd>/}.
     *
     * El nombre del archivo se construye como:
     * {@code <nombreReporte>-<codigoOperacion>.pdf}
     *
     * @param nombreReporte nombre del reporte ejecutado.
     * @param codigoOperacion código único de operación asociado al reporte.
     * @param pdfBytes contenido PDF.
     */
    private void guardarArchivoEnDisco(String nombreReporte, String codigoOperacion, byte[] pdfBytes) {
        if (!guardarArchivos) return;

        try {
            String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String nombreArchivo = nombreReporte + "-" + codigoOperacion + ".pdf";
            String rutaCarpeta;

            if (esProduccion) {
                // Producción: usa ruta externa configurada
                rutaCarpeta = Paths.get(rutaProduccion, fechaActual).toString();
            } else {
                // Desarrollo: usa carpeta dentro de resources
                rutaCarpeta = Paths.get("src/main/resources/reportes/generados", fechaActual).toString();
            }

            File carpeta = new File(rutaCarpeta);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File(carpeta, nombreArchivo);
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                fos.write(pdfBytes);
            }
        } catch (Exception e) {
            System.err.println("Error al guardar el archivo del reporte: " + e.getMessage());
        }
    }

    /**
     * Guarda el PDF generado en disco si la propiedad {@code reports.savefiles} está habilitada.
     *
     * <p>
     * - En producción ({@code reports.isproduction=true}) guarda en {@code reports.output-directory/<yyyy-MM-dd>/}.
     * - En desarrollo guarda en {@code src/main/resources/reportes/generados/<yyyy-MM-dd>/}.
     *
     * El nombre del archivo se construye como:
     * {@code <nombreReporte>-<codigoOperacion>.pdf}
     *
     * @param nombreReporte nombre del reporte ejecutado.
     * @param codigoOperacion código único de operación asociado al reporte.
     * @param pdfBytes contenido PDF.
     */
    private void guardarLogReporte(String idReporte,
                                   String nombreReporte,
                                   String legajo,
                                   String nombre,
                                   Map<String, Object> parametros,
                                   List<?> datos) {

        // Convertir parámetros y datos a JSON (puedes usar cualquier librería de JSON)
        String parametrosJson = parametros.toString();
        String datosJson = datos.toString();

        ReportesLog log = new ReportesLog();
        log.setIdReporte(idReporte);
        log.setReporteNombre(nombreReporte);
        log.setReporteUsuario(Long.parseLong(legajo));
        log.setReporteUsuarioNombre(nombre);
        log.setReporteDatos("{ \"parametros\": " + parametrosJson + ", \"datos\": " + datosJson + " }");
        log.setReporteFecha(LocalDateTime.now());

        // ✅ Guardar en base de datos
        reportesLogService.save(log);
    }
}
