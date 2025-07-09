package com.mcpd.repository;

import com.mcpd.model.OrdenDePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDePagoRepository extends JpaRepository<OrdenDePago, String> {

    boolean existsByNumero(String numero);
}
