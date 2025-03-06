package com.mcpd.controller;

import com.mcpd.model.Producto;
import com.mcpd.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productos")
public class ProductoController extends AbstractCrudController<Producto> {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Override
    protected List<Producto> getAllEntities() {
        List<Producto> list=service.getAll();
        return list;
    }

    @Override
    protected Optional<Producto> getEntityById(Integer id) {
        return service.getById(id);
    }

    @Override
    protected Producto createOrUpdateEntity(Producto entity) {
        return service.save(entity);
    }

    @Override
    protected void deleteEntity(Integer id) {
        service.delete(id);
    }

    @Override
    protected void setEntityId(Producto entity, Integer id) {
        entity.setId(id);
    }
}
