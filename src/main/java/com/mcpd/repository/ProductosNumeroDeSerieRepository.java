package com.mcpd.repository;

import com.mcpd.model.ProductosNumeroDeSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosNumeroDeSerieRepository extends JpaRepository<ProductosNumeroDeSerie, Integer> {

    List<ProductosNumeroDeSerie> findByProductoFlujo_Id(Integer productoFlujoId);

    @Query("""
        SELECT ns FROM ProductosNumeroDeSerie ns
        WHERE ns.productoFlujo.productoStock.id = :productoStockId
          AND (:filtrarActivos IS FALSE OR ns.activo = true)
          AND (:empleadoCustodia IS NULL OR ns.empleadoCustodia.legajo = :empleadoCustodia)
    """)
    List<ProductosNumeroDeSerie> findByProductoStockIdOpcionalActivoYEmpleado(
            @Param("productoStockId") Integer productoStockId,
            @Param("filtrarActivos") Boolean filtrarActivos,
            @Param("empleadoCustodia") Long empleadoCustodia
    );

    @Query("""
        SELECT ns FROM ProductosNumeroDeSerie ns
        WHERE ns.productoFlujo.productoStock.id = :productoStockId
          AND ns.activo = true
          AND ns.empleadoCustodia IS NULL
    """)
    List<ProductosNumeroDeSerie> findActivosSinCustodiaPorProductoStock(
            @Param("productoStockId") Integer productoStockId
    );
}
