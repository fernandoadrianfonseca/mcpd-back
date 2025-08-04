package com.mcpd.repository;

import com.mcpd.model.PedidoDeAdquisicionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PedidoDeAdquisicionDetalleRepository extends JpaRepository<PedidoDeAdquisicionDetalle, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comprasadquisicionpedidodetalle WHERE comprasadquisicionpedido = :pedidoId", nativeQuery = true)
    void eliminarDetallesNativo(@Param("pedidoId") Long pedidoId);

    @Modifying
    @Query("DELETE FROM PedidoDeAdquisicionDetalle d WHERE d.pedido.numero = :numeroPedido")
    void eliminarDetalles(@Param("numeroPedido") Long numeroPedido);
}
