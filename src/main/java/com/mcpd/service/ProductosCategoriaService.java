package com.mcpd.service;

import com.mcpd.model.ProductosCategoria;
import com.mcpd.repository.ProductosCategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de categorías de productos.
 *
 * <p>
 * Actúa como capa intermedia entre el controller y el repository,
 * encapsulando operaciones CRUD básicas.
 * </p>
 *
 * Actualmente no contiene lógica de negocio adicional,
 * pero permite centralizar validaciones futuras si fueran necesarias.
 */
@Service
public class ProductosCategoriaService {

    private final ProductosCategoriaRepository repository;

    public ProductosCategoriaService(ProductosCategoriaRepository repository) {
        this.repository = repository;
    }

    /** Obtiene todas las categorías registradas. */
    public List<ProductosCategoria> getAll() {
        return repository.findAll();
    }

    /**
     * Obtiene una categoría por su identificador.
     *
     * @param id identificador de la categoría.
     * @return Optional con la categoría si existe.
     */
    public Optional<ProductosCategoria> getById(Integer id) {
        return repository.findById(id);
    }

    /**
     * Guarda o actualiza una categoría.
     *
     * @param categoria entidad a persistir.
     * @return categoría persistida.
     */
    public ProductosCategoria save(ProductosCategoria categoria) {
        return repository.save(categoria);
    }

    /**
     * Elimina una categoría por id.
     *
     * @param id identificador de la categoría.
     */
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
