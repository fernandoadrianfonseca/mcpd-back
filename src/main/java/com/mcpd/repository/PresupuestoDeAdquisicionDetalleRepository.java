package com.mcpd.repository;

import com.mcpd.model.PresupuestoDeAdquisicionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresupuestoDeAdquisicionDetalleRepository extends JpaRepository<PresupuestoDeAdquisicionDetalle, Long> {

    List<PresupuestoDeAdquisicionDetalle> findByComprasadquisicionpresupuestoNumero(Long numeroPresupuesto);

    @Modifying
    @Query("DELETE FROM PresupuestoDeAdquisicionDetalle p WHERE p.comprasadquisicionpresupuesto.numero = :numeroPresupuesto")
    void deleteByNumeroPresupuesto(@Param("numeroPresupuesto") Long numeroPresupuesto);
}
