package com.mcpd.controller;

import com.mcpd.model.Empleado;
import com.mcpd.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleadoService.obtenerTodos();
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<Empleado> obtenerPorLegajo(@PathVariable Long legajo) {
        Optional<Empleado> empleado = empleadoService.obtenerPorLegajo(legajo);
        return empleado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @PutMapping("/{legajo}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long legajo, @RequestBody Empleado empleado) {
        Optional<Empleado> actualizado = empleadoService.actualizar(legajo, empleado);
        return actualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{legajo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long legajo) {
        empleadoService.eliminar(legajo);
        return ResponseEntity.noContent().build();
    }
}
