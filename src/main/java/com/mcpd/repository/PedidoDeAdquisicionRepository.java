package com.mcpd.repository;

import com.mcpd.model.PedidoDeAdquisicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDeAdquisicionRepository extends JpaRepository<PedidoDeAdquisicion, Long> {
}
