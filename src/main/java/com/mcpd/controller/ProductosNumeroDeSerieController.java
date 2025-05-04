package com.mcpd.controller;

import com.mcpd.model.ProductosNumeroDeSerie;
import com.mcpd.service.ProductosNumeroDeSerieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/numeros-de-serie")
@CrossOrigin(origins = "*")
public class ProductosNumeroDeSerieController extends AbstractCrudController<ProductosNumeroDeSerie> {

    private final ProductosNumeroDeSerieService service;

    public ProductosNumeroDeSerieController(ProductosNumeroDeSerieService service) {
        this.service = service;
    }

    @Override
    protected List<ProductosNumeroDeSerie> getAllEntities() {
        return service.findAll();
    }

    @Override
    protected Optional<ProductosNumeroDeSerie> getEntityById(Integer id) {
        return service.findById(id);
    }

    @Override
    protected ProductosNumeroDeSerie createOrUpdateEntity(ProductosNumeroDeSerie entity) {
        return service.save(entity);
    }

    @Override
    protected void deleteEntity(Integer id) {
        service.deleteById(id);
    }

    @Override
    protected void setEntityId(ProductosNumeroDeSerie entity, Integer id) {
        entity.setId(id);
    }

    @GetMapping("/flujo/{id}")
    public List<ProductosNumeroDeSerie> getByProductoFlujoId(@PathVariable Integer id) {
        return service.findByProductoFlujoId(id);
    }

    @GetMapping("/producto-stock/{productoStockId}")
    public List<ProductosNumeroDeSerie> getByProductoStockId(
            @PathVariable Integer productoStockId,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Long empleadoCustodia) {

        return service.findByProductoStockId(productoStockId,
                activo != null ? activo : true,
                empleadoCustodia);
    }

    @PostMapping("/lote")
    public List<ProductosNumeroDeSerie> crearEnLote(@RequestBody List<ProductosNumeroDeSerie> lista) {
        return service.saveAll(lista);
    }
}
