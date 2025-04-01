package com.mcpd.repository;

import com.mcpd.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query("SELECT p, c.nombre FROM Proveedor p JOIN p.contribuyente c")
    List<Object[]> findAllWithNombreContribuyente();
}
