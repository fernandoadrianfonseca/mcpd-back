package com.mcpd.service;

import com.mcpd.model.ProductosCategoria;
import com.mcpd.repository.ProductosCategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosCategoriaService {

    private final ProductosCategoriaRepository repository;

    public ProductosCategoriaService(ProductosCategoriaRepository repository) {
        this.repository = repository;
    }

    public List<ProductosCategoria> getAll() {
        return repository.findAll();
    }

    public Optional<ProductosCategoria> getById(Integer id) {
        return repository.findById(id);
    }

    public ProductosCategoria save(ProductosCategoria categoria) {
        return repository.save(categoria);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
