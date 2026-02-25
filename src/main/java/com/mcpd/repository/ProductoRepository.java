package com.mcpd.repository;

/**
 * Repositorio JPA para la entidad {@link Producto}.
 *
 * <p>
 * Extiende {@link org.springframework.data.jpa.repository.JpaRepository}
 * para proporcionar operaciones CRUD estándar.
 * </p>
 *
 * Actualmente no define consultas personalizadas adicionales.
 */
import com.mcpd.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
