package com.mcpd.controller;

import com.mcpd.model.ReportesLog;
import com.mcpd.service.ReportesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reportes-log")
@CrossOrigin(origins = "*")
public class ReportesLogController {

    private final ReportesLogService service;

    @Autowired
    public ReportesLogController(ReportesLogService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReportesLog> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ReportesLog> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ReportesLog create(@RequestBody ReportesLog reportesLog) {
        return service.save(reportesLog);
    }

    @PutMapping("/{id}")
    public ReportesLog update(@PathVariable Long id, @RequestBody ReportesLog reportesLog) {
        reportesLog.setId(id);
        return service.save(reportesLog);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ReportesLog> getByUsuario(@PathVariable Long usuarioId) {
        return service.findByUsuario(usuarioId);
    }

    @GetMapping("/buscar")
    public List<ReportesLog> searchByNombre(@RequestParam String nombre) {
        return service.findByNombre(nombre);
    }

    @GetMapping("/fecha")
    public List<ReportesLog> getByFecha(@RequestParam String inicio, @RequestParam String fin) {
        LocalDateTime fechaInicio = LocalDateTime.parse(inicio);
        LocalDateTime fechaFin = LocalDateTime.parse(fin);
        return service.findByFechaEntre(fechaInicio, fechaFin);
    }
}
