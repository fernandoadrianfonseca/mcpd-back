package com.mcpd.controller;

import com.mcpd.dto.CustodiaItem;
import com.mcpd.dto.StockCategoriaDto;
import com.mcpd.dto.StockProductoDto;
import com.mcpd.model.ProductosStock;
import com.mcpd.service.ProductosStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stock")
public class ProductosStockController {

    private final ProductosStockService productosStockService;

    public ProductosStockController(ProductosStockService productosStockService) {
        this.productosStockService = productosStockService;
    }

    @GetMapping
    public List<ProductosStock> getAll() {
        return productosStockService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosStock> getById(@PathVariable Integer id) {
        return productosStockService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosStock create(@RequestBody ProductosStock entity) {
        return productosStockService.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosStock> update(@PathVariable Integer id, @RequestBody ProductosStock entity) {
        Optional<ProductosStock> existing = productosStockService.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(productosStockService.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosStock> existing = productosStockService.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productosStockService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/custodia/{legajoCustodia}")
    public List<ProductosStock> getStockActualPorCustodia(@PathVariable Long legajoCustodia) {
        return productosStockService.getStockActualPorCustodia(legajoCustodia);
    }

    @GetMapping("/excluyendo-custodia/{legajoCustodia}")
    public List<ProductosStock> getStockActualExcluyendoCustodia(@PathVariable Long legajoCustodia) {
        return productosStockService.getStockActualExcluyendoCustodia(legajoCustodia);
    }

    @GetMapping("/disponible-asignar")
    public List<ProductosStock> getStockDisponibleParaAsignar() {
        return productosStockService.getStockDisponibleParaAsignar();
    }

    @PostMapping("/asignar-custodia")
    public void asignarCustodia(@RequestBody List<CustodiaItem> items,
                                @RequestParam("legajoCustodia") Long legajoCustodia,
                                @RequestParam("legajoCarga") Long legajoCarga) {
        productosStockService.asignarCustodia(items, legajoCustodia, legajoCarga);
    }

    @PostMapping("/quitar-custodia")
    public void quitarCustodia(@RequestBody List<CustodiaItem> items,
                               @RequestParam("legajoCustodia") Long legajoCustodia,
                               @RequestParam("legajoCarga") Long legajoCarga) {
        productosStockService.quitarCustodia(items, legajoCustodia, legajoCarga);
    }

    @PostMapping("/transferir-custodia")
    public void transferirCustodia(
            @RequestBody List<CustodiaItem> items,
            @RequestParam("legajoOrigen") Long legajoOrigen,
            @RequestParam("legajoDestino") Long legajoDestino,
            @RequestParam("legajoCarga") Long legajoCarga
    ) {
        productosStockService.transferirCustodia(items, legajoOrigen, legajoDestino, legajoCarga);
    }

    @GetMapping("/por-categorias")
    public List<StockCategoriaDto> obtenerStockPorCategoria() {
        return productosStockService.obtenerStockPorCategoria();
    }

    @GetMapping("/por-producto")
    public List<StockProductoDto> stockPorProducto() {
        return productosStockService.obtenerStockPorProducto();
    }
}
