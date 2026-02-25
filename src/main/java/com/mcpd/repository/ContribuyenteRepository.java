package com.mcpd.repository;

import com.mcpd.model.Contribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link Contribuyente}.
 *
 * <p>
 * Proporciona operaciones CRUD estándar utilizando
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 * </p>
 *
 * No incluye consultas personalizadas actualmente.
 */
@Repository
public interface ContribuyenteRepository extends JpaRepository<Contribuyente, Long> {
}