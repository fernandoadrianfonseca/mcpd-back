package com.mcpd.repository;

import com.mcpd.model.ProductosStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosStockRepository extends JpaRepository<ProductosStock, Integer> {
}
