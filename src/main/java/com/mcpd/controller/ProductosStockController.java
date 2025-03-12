package com.mcpd.controller;

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
}
