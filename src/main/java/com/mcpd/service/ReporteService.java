package com.mcpd.service;

import com.mcpd.model.ReportesLog;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

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
            return JasperExportManager.exportReportToPdf(jasperPrint);
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
            return JasperExportManager.exportReportToPdf(jasperPrint );
        } catch (Exception e) {
            throw new RuntimeException("Error al generar reporte con lista: " + e.getMessage(), e);
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
