package com.mcpd.repository;

import com.mcpd.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Proveedor}.
 *
 * <p>
 * Proporciona operaciones CRUD básicas y una consulta personalizada
 * para obtener el nombre del contribuyente asociado.
 * </p>
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Obtiene todos los proveedores junto con el nombre
     * del contribuyente asociado.
     *
     * @return Lista de Object[] donde:
     *         [0] = Proveedor
     *         [1] = nombre del contribuyente
     */
    @Query("SELECT p, c.nombre FROM Proveedor p JOIN p.contribuyente c")
    List<Object[]> findAllWithNombreContribuyente();
}
