package com.mcpd.repository;

import com.mcpd.model.ProveedorFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<ProveedorFactura, String> {

    List<ProveedorFactura> findByOrdenCompraContratoCertificacion(String ordenCompraContratoCertificacion);
}
