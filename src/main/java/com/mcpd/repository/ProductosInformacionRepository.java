package com.mcpd.repository;

import com.mcpd.model.ProductosInformacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosInformacionRepository extends JpaRepository<ProductosInformacion, Integer> {

    List<ProductosInformacion> findByProductoFlujo_Id(Integer productoFlujoId);

    @Query("""
        SELECT ns FROM ProductosInformacion ns
        WHERE ns.productoFlujo.productoStock.id = :productoStockId
          AND (:filtrarActivos IS FALSE OR ns.activo = true)
          AND (:empleadoCustodia IS NULL OR ns.empleadoCustodia.legajo = :empleadoCustodia)
    """)
    List<ProductosInformacion> findByProductoStockIdOpcionalActivoYEmpleado(
            @Param("productoStockId") Integer productoStockId,
            @Param("filtrarActivos") Boolean filtrarActivos,
            @Param("empleadoCustodia") Long empleadoCustodia
    );

    @Query("""
        SELECT ns FROM ProductosInformacion ns
        WHERE ns.productoFlujo.productoStock.id = :productoStockId
          AND ns.activo = true
          AND ns.empleadoCustodia IS NULL
    """)
    List<ProductosInformacion> findActivosSinCustodiaPorProductoStock(
            @Param("productoStockId") Integer productoStockId
    );

    boolean existsByActivoTrueAndNumeroDeSerie(String numeroDeSerie);
    boolean existsByActivoTrueAndCodigoAntiguo(String codigoAntiguo);
}
