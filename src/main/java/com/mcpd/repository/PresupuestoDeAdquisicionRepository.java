package com.mcpd.repository;

import com.mcpd.model.PresupuestoDeAdquisicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestoDeAdquisicionRepository extends JpaRepository<PresupuestoDeAdquisicion, Long> {
}
