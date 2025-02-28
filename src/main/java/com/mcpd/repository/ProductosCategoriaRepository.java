package com.mcpd.repository;

import com.mcpd.model.ProductosCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosCategoriaRepository extends JpaRepository<ProductosCategoria, Integer> {
}
