package com.mcpd.service;

import com.mcpd.model.ProductosNumeroDeSerie;
import com.mcpd.repository.ProductosNumeroDeSerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosNumeroDeSerieService {

    private final ProductosNumeroDeSerieRepository repository;

    public ProductosNumeroDeSerieService(ProductosNumeroDeSerieRepository repository) {
        this.repository = repository;
    }

    public List<ProductosNumeroDeSerie> findAll() {
        return repository.findAll();
    }

    public Optional<ProductosNumeroDeSerie> findById(Integer id) {
        return repository.findById(id);
    }

    public ProductosNumeroDeSerie save(ProductosNumeroDeSerie entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<ProductosNumeroDeSerie> findByProductoFlujoId(Integer flujoId) {
        return repository.findByProductoFlujo_Id(flujoId);
    }

    public List<ProductosNumeroDeSerie> findByProductoStockId(Integer productoStockId, Boolean activo, Long empleadoCustodiaId) {
        return repository.findByProductoStockIdOpcionalActivoYEmpleado(productoStockId, activo, empleadoCustodiaId);
    }

    public List<ProductosNumeroDeSerie> saveAll(List<ProductosNumeroDeSerie> lista) {
        return repository.saveAll(lista);
    }
}
