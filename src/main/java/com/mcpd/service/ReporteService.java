package com.mcpd.service;

import com.mcpd.model.ReportesLog;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class ReporteService {

    @Value("${reports.savefiles:false}")
    private boolean guardarArchivos;

    @Value("${reports.isproduction:false}")
    private boolean esProduccion;

    @Value("${reports.output-directory:}")
    private String rutaProduccion;

    private final ReportesLogService reportesLogService;

    public ReporteService(ReportesLogService reportesLogService) {
        this.reportesLogService = reportesLogService;
    }

    public byte[] generarReporte(String nombreReporte,
                                 String generaReporteLegajo,
                                 String generaReporteNombre,
                                 Map<String, Object> parametros,
                                 Integer cantidadCopias) {
        try {
            int copias = (cantidadCopias != null && cantidadCopias > 0) ? cantidadCopias : 1;
            parametros.put("REPORT_CLASS_LOADER", Thread.currentThread().getContextClassLoader());
            parametros.putIfAbsent("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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
            throw new RuntimeException("Error al generar reporte: " + e.getMessage(), e);
        }
    }

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
            throw new RuntimeException("Error al generar reporte con lista: " + e.getMessage(), e);
        }
    }

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
