package com.mcpd.controller;

import com.mcpd.model.ProductosCategoria;
import com.mcpd.service.ProductosCategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias")
public class ProductosCategoriaController extends AbstractCrudController<ProductosCategoria> {

    private final ProductosCategoriaService service;

    public ProductosCategoriaController(ProductosCategoriaService service) {
        this.service = service;
    }

    @Override
    protected List<ProductosCategoria> getAllEntities() {
        return service.getAll();
    }

    @Override
    protected Optional<ProductosCategoria> getEntityById(Integer id) {
        return service.getById(id);
    }

    @Override
    protected ProductosCategoria createOrUpdateEntity(ProductosCategoria entity) {
        return service.save(entity);
    }

    @Override
    protected void deleteEntity(Integer id) {
        service.delete(id);
    }

    @Override
    protected void setEntityId(ProductosCategoria entity, Integer id) {
        entity.setId(id);
    }
}
