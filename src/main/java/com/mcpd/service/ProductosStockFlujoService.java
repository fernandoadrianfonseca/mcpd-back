package com.mcpd.service;

import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.repository.ProductosStockFlujoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosStockFlujoService {

    private final ProductosStockFlujoRepository repository;

    public ProductosStockFlujoService(ProductosStockFlujoRepository repository) {
        this.repository = repository;
    }

    public List<ProductosStockFlujo> findAll() {
        return repository.findAll();
    }

    public Optional<ProductosStockFlujo> findById(Integer id) {
        return repository.findById(id);
    }

    public ProductosStockFlujo save(ProductosStockFlujo entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<ProductosStockFlujo> findByProductoStockId(Integer productoStockId) {
        return repository.findByProductoStock_Id(productoStockId);
    }

    public List<ProductosStockFlujo> findRemitosByProductoStockId(Integer productoStockId) {
        return repository.findRemitosByProductoStockId(productoStockId);
    }

    public List<ProductosStockFlujo> findCustodiasPorProducto(Integer productoStockId) {
        return repository.findCustodiasPorProducto(productoStockId);
    }

    public List<ProductosStockFlujo> findFlujosAltasYBajasByProductoStockId(Integer productoStockId, Long legajoCustodia) {
        return repository.findFlujosAltasYBajasByProductoStockId(productoStockId, legajoCustodia);
    }
}
