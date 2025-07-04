package com.mcpd.repository;

import com.mcpd.model.PresupuestoDeAdquisicionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestoDeAdquisicionDetalleRepository extends JpaRepository<PresupuestoDeAdquisicionDetalle, Long> {
}
