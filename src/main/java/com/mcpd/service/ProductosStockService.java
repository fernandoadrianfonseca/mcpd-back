package com.mcpd.service;

import com.mcpd.model.ProductosStock;
import com.mcpd.repository.ProductosStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosStockService {

    private final ProductosStockRepository productosStockRepository;

    public ProductosStockService(ProductosStockRepository productosStockRepository) {
        this.productosStockRepository = productosStockRepository;
    }

    public List<ProductosStock> getAll() {
        return productosStockRepository.findAll();
    }

    public Optional<ProductosStock> getById(Integer id) {
        return productosStockRepository.findById(id);
    }

    public ProductosStock save(ProductosStock productosStock) {
        return productosStockRepository.save(productosStock);
    }

    public void delete(Integer id) {
        productosStockRepository.deleteById(id);
    }
}
