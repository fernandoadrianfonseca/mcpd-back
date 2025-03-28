package com.mcpd.repository;

import com.mcpd.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query("SELECT e, c.nombre FROM Empleado e LEFT JOIN Contribuyente c ON e.cuil = c.cuit")
    List<Object[]> findAllWithContribuyente();
}