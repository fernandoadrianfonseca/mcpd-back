package com.mcpd.controller;

import com.mcpd.dto.ReporteRequestDto;
import com.mcpd.service.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST encargado de la generación dinámica de reportes PDF.
 *
 * <p>
 * Expone un endpoint unificado que recibe un {@link ReporteRequestDto}
 * y delega en {@link ReporteService} la compilación y ejecución de la
 * plantilla JasperReports (.jrxml).
 *
 * <p>
 * El PDF generado se devuelve como {@code byte[]} con:
 * <ul>
 *   <li>Content-Type: application/pdf</li>
 *   <li>Content-Disposition: inline</li>
 * </ul>
 *
 * <p>
 * Este controller no contiene lógica de negocio, únicamente:
 * <ul>
 *   <li>Valida cantidad de copias</li>
 *   <li>Determina si el reporte se ejecuta con o sin datasource</li>
 *   <li>Configura la respuesta HTTP</li>
 * </ul>
 */
@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * Genera un reporte PDF en base a una plantilla JRXML.
     *
     * <p>
     * Comportamiento:
     * <ul>
     *   <li>Si {@code dto.datos} no es null ni vacío → ejecuta el reporte con lista (datasource).</li>
     *   <li>Si {@code dto.datos} es null o vacío → ejecuta el reporte sin datasource.</li>
     *   <li>Normaliza {@code cantidadCopias} (default 1).</li>
     * </ul>
     *
     * <p>
     * El archivo se devuelve inline en el navegador con nombre:
     * {@code <nombreReporte>.pdf}
     *
     * @param dto objeto de request que contiene nombre de plantilla,
     *            parámetros, datos y usuario generador.
     * @return PDF generado como {@code byte[]} con Content-Type application/pdf.
     */
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generarReporte(@RequestBody ReporteRequestDto dto) {
        byte[] pdf;

        Integer cantidadCopias = (dto.getCantidadCopias() != null && dto.getCantidadCopias() > 0) ? dto.getCantidadCopias() : 1;

        if (dto.getDatos() != null && !dto.getDatos().isEmpty()) {
            pdf = reporteService.generarReporteConLista(dto.getNombreReporte(),
                                                        dto.getGeneraReporteLegajo(),
                                                        dto.getGeneraReporteNombre(),
                                                        dto.getParametros(),
                                                        dto.getDatos(),
                                                        cantidadCopias);
        } else {
            pdf = reporteService.generarReporte(dto.getNombreReporte(),
                                                dto.getGeneraReporteLegajo(),
                                                dto.getGeneraReporteNombre(),
                                                dto.getParametros(),
                                                cantidadCopias);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + dto.getNombreReporte() + ".pdf\"")
                .body(pdf);
    }
}
