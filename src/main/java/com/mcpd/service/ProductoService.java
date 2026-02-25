package com.mcpd.service;

import com.mcpd.model.Producto;
import com.mcpd.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de productos.
 *
 * <p>
 * Actúa como capa intermedia entre el controller y el repository,
 * encapsulando operaciones CRUD básicas sobre {@link Producto}.
 * </p>
 *
 * <p>
 * Puede ampliarse en el futuro para incluir validaciones adicionales,
 * como restricciones antes de eliminar un producto con stock asociado.
 * </p>
 */
@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    /** Obtiene todos los productos registrados. */
    public List<Producto> getAll() {
        return repository.findAll();
    }

    /**
     * Obtiene un producto por su identificador.
     *
     * @param id identificador del producto.
     * @return Optional con el producto si existe.
     */
    public Optional<Producto> getById(Integer id) {
        return repository.findById(id);
    }

    /**
     * Guarda o actualiza un producto.
     *
     * @param producto entidad a persistir.
     * @return producto persistido.
     */
    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    /**
     * Elimina un producto por id.
     *
     * @param id identificador del producto.
     */
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}