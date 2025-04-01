package com.mcpd.controller;

import com.mcpd.model.Proveedor;
import com.mcpd.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> obtenerTodos() {
        return proveedorService.obtenerTodosConNombre();
    }

    @GetMapping("/{cuit}")
    public ResponseEntity<Proveedor> obtenerPorCuit(@PathVariable Long cuit) {
        Optional<Proveedor> proveedor = proveedorService.obtenerPorCuit(cuit);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return proveedorService.guardar(proveedor);
    }

    @PutMapping("/{cuit}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long cuit, @RequestBody Proveedor proveedor) {
        if (!proveedorService.obtenerPorCuit(cuit).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        proveedor.setCuit(cuit);
        return ResponseEntity.ok(proveedorService.guardar(proveedor));
    }

    @DeleteMapping("/{cuit}")
    public ResponseEntity<Void> eliminar(@PathVariable Long cuit) {
        proveedorService.eliminar(cuit);
        return ResponseEntity.noContent().build();
    }
}

