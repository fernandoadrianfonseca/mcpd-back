package com.mcpd.controller;

import com.mcpd.dto.CustodiaItem;
import com.mcpd.model.ProductosStock;
import com.mcpd.service.ProductosStockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stock")
public class ProductosStockController extends AbstractCrudController<ProductosStock> {

    private final ProductosStockService productosStockService;

    public ProductosStockController(ProductosStockService productosStockService) {
        this.productosStockService = productosStockService;
    }

    @Override
    protected List<ProductosStock> getAllEntities() {
        return productosStockService.getAll();
    }

    @Override
    protected Optional<ProductosStock> getEntityById(Integer id) {
        return productosStockService.getById(id);
    }

    @Override
    protected ProductosStock createOrUpdateEntity(ProductosStock entity) {
        return productosStockService.save(entity);
    }

    @Override
    protected void deleteEntity(Integer id) {
        productosStockService.delete(id);
    }

    @Override
    protected void setEntityId(ProductosStock entity, Integer id) {
        entity.setId(id);
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
}
