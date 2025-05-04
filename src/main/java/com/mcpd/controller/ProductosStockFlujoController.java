package com.mcpd.controller;

import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.service.ProductosStockFlujoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock-flujo")
@CrossOrigin(origins = "*")
public class ProductosStockFlujoController extends AbstractCrudController<ProductosStockFlujo> {

    private final ProductosStockFlujoService service;

    public ProductosStockFlujoController(ProductosStockFlujoService service) {
        this.service = service;
    }

    @Override
    protected List<ProductosStockFlujo> getAllEntities() {
        return service.findAll();
    }

    @Override
    protected Optional<ProductosStockFlujo> getEntityById(Integer id) {
        return service.findById(id);
    }

    @Override
    protected ProductosStockFlujo createOrUpdateEntity(ProductosStockFlujo entity) {
        return service.save(entity);
    }

    @Override
    protected void deleteEntity(Integer id) {
        service.deleteById(id);
    }

    @Override
    protected void setEntityId(ProductosStockFlujo entity, Integer id) {
        entity.setId(id);
    }

    @GetMapping("/producto-stock/{id}")
    public List<ProductosStockFlujo> getByProductoStockId(@PathVariable Integer id) {
        return service.findByProductoStockId(id);
    }

    @GetMapping("/producto-stock/{id}/remitos")
    public List<ProductosStockFlujo> getRemitosByProductoStockId(@PathVariable Integer id) {
        return service.findRemitosByProductoStockId(id);
    }

    @GetMapping("/producto-stock/{id}/custodias-activas")
    public List<ProductosStockFlujo> getCustodiasActivasByProductoStockId(@PathVariable Integer id) {
        return service.findCustodiasPorProducto(id);
    }

    @GetMapping("/producto-stock/{id}/flujos-altas-bajas")
    public List<ProductosStockFlujo> getAltasYBajasByProductoStockId(
            @PathVariable Integer id,
            @RequestParam(required = false) Long legajoCustodia) {
        return service.findFlujosAltasYBajasByProductoStockId(id, legajoCustodia);
    }
}
