package com.mcpd.repository;

import com.mcpd.model.OrdenDeCompraAdquisicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenDeCompraAdquisicionRepository extends JpaRepository<OrdenDeCompraAdquisicion, String> {

    List<OrdenDeCompraAdquisicion> findByInstrumento(String instrumento);
}
