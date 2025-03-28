package com.mcpd.controller;

import com.mcpd.model.Log;
import com.mcpd.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public List<Log> obtenerTodos() {
        return logService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> obtenerPorId(@PathVariable Long id) {
        Optional<Log> log = logService.obtenerPorId(id);
        return log.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Log guardar(@RequestBody Log log) {
        return logService.guardar(log);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        logService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

