package com.mcpd.controller;

import com.mcpd.model.Contribuyente;
import com.mcpd.service.ContribuyenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contribuyentes")
@CrossOrigin(origins = "*") // Permite llamadas desde cualquier frontend
public class ContribuyenteController {

    @Autowired
    private ContribuyenteService contribuyenteService;

    @GetMapping
    public List<Contribuyente> obtenerTodos() {
        return contribuyenteService.obtenerTodos();
    }

    @GetMapping("/{cuit}")
    public ResponseEntity<Contribuyente> obtenerPorCuit(@PathVariable Long cuit) {
        Optional<Contribuyente> contribuyente = contribuyenteService.obtenerPorCuit(cuit);
        return contribuyente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contribuyente guardar(@RequestBody Contribuyente contribuyente) {
        return contribuyenteService.guardar(contribuyente);
    }

    @PutMapping("/{cuit}")
    public ResponseEntity<Contribuyente> actualizar(@PathVariable Long cuit, @RequestBody Contribuyente contribuyente) {
        Optional<Contribuyente> actualizado = contribuyenteService.actualizar(cuit, contribuyente);
        return actualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cuit}")
    public ResponseEntity<Void> eliminar(@PathVariable Long cuit) {
        contribuyenteService.eliminar(cuit);
        return ResponseEntity.noContent().build();
    }
}
