package com.mcpd.repository;

import com.mcpd.model.ProductosCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link ProductosCategoria}.
 *
 * <p>
 * Extiende {@link org.springframework.data.jpa.repository.JpaRepository}
 * para proporcionar operaciones CRUD estándar.
 * </p>
 *
 * No define consultas personalizadas adicionales.
 */
@Repository
public interface ProductosCategoriaRepository extends JpaRepository<ProductosCategoria, Integer> {
}
