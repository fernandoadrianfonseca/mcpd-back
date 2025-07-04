package com.mcpd.repository;

import com.mcpd.model.PedidoDeAdquisicionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDeAdquisicionDetalleRepository extends JpaRepository<PedidoDeAdquisicionDetalle, Long> {
}
