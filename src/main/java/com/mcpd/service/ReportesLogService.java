package com.mcpd.service;

import com.mcpd.model.ReportesLog;
import com.mcpd.repository.ReportesLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportesLogService {

    private final ReportesLogRepository repository;

    @Autowired
    public ReportesLogService(ReportesLogRepository repository) {
        this.repository = repository;
    }

    public List<ReportesLog> findAll() {
        return repository.findAll();
    }

    public Optional<ReportesLog> findById(Long id) {
        return repository.findById(id);
    }

    public ReportesLog save(ReportesLog entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<ReportesLog> findByUsuario(Long reporteUsuario) {
        return repository.findByReporteUsuario(reporteUsuario);
    }

    public List<ReportesLog> findByNombre(String nombre) {
        return repository.findByReporteNombreContaining(nombre);
    }

    public List<ReportesLog> findByFechaEntre(LocalDateTime inicio, LocalDateTime fin) {
        return repository.findByReporteFechaBetween(inicio, fin);
    }
}
