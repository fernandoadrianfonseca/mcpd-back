package com.mcpd.repository;

import com.mcpd.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Empleado}.
 *
 * Incluye una consulta personalizada para obtener
 * el nombre del empleado desde {@link Contribuyente}.
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    /**
     * Obtiene todos los empleados junto con el nombre del contribuyente.
     *
     * @return Lista de Object[] donde:
     *         [0] = Empleado
     *         [1] = nombre del contribuyente (si existe)
     */
    @Query("SELECT e, c.nombre FROM Empleado e LEFT JOIN Contribuyente c ON e.cuil = c.cuit")
    List<Object[]> findAllWithContribuyente();
}