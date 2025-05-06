package com.mcpd.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReporteService {

    public byte[] generarReporte(String nombreReporte, Map<String, Object> parametros) {
        try {
            InputStream jrxmlStream = new ClassPathResource("reportes/" + nombreReporte + ".jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar reporte: " + e.getMessage(), e);
        }
    }

    public byte[] generarReporteConLista(String nombreReporte, Map<String, Object> parametros, java.util.List<?> datos) {
        try {

            parametros.putIfAbsent("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            //parametros.putIfAbsent("codigoOperacion", "OP-" + Instant.now().toEpochMilli());
            parametros.putIfAbsent("codigoOperacion", "OP-" + Instant.now().getEpochSecond());

            List<Object> duplicados = new ArrayList<>();
            for (Object item : datos) {
                duplicados.add(item);

            }
            
            InputStream jrxmlStream = new ClassPathResource("reportes/" + nombreReporte + ".jrxml").getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(duplicados);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar reporte con lista: " + e.getMessage(), e);
        }
    }
}
