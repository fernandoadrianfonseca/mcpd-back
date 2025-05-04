package com.mcpd.controller;

import com.mcpd.dto.ReporteRequestDto;
import com.mcpd.service.ReporteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generarReporte(@RequestBody ReporteRequestDto dto) {
        byte[] pdf;

        if (dto.getDatos() != null && !dto.getDatos().isEmpty()) {
            pdf = reporteService.generarReporteConLista(dto.getNombreReporte(), dto.getParametros(), dto.getDatos());
        } else {
            pdf = reporteService.generarReporte(dto.getNombreReporte(), dto.getParametros());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + dto.getNombreReporte() + ".pdf\"")
                .body(pdf);
    }
}
