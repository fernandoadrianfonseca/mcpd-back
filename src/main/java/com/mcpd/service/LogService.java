package com.mcpd.service;

import com.mcpd.model.Log;
import com.mcpd.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> obtenerTodos() {
        return logRepository.findAll();
    }

    public Optional<Log> obtenerPorId(Long id) {
        return logRepository.findById(id);
    }

    public Log guardar(Log log) {
        return logRepository.save(log);
    }

    public void eliminar(Long id) {
        logRepository.deleteById(id);
    }
}
