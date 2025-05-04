package com.mcpd.repository;

import com.mcpd.model.ProductosStock;
import com.mcpd.model.ProductosStockFlujo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosStockFlujoRepository extends JpaRepository<ProductosStockFlujo, Integer> {

    List<ProductosStockFlujo> findByProductoStock_Id(Integer productoStockId);

    @Query("SELECT f FROM ProductosStockFlujo f WHERE f.productoStock.id = :productoStockId AND f.tipo = 'alta' AND f.remito IS NOT NULL AND f.remito <> ''")
    List<ProductosStockFlujo> findRemitosByProductoStockId(@Param("productoStockId") Integer productoStockId);

    @Query("""
        SELECT f FROM ProductosStockFlujo f
        WHERE f.productoStock.id = :productoStockId
          AND (f.tipo = 'alta' OR f.tipo = 'baja' OR f.tipo = 'custodia_alta' OR f.tipo = 'custodia_baja')
          AND (:legajo IS NULL OR f.empleadoCustodia = :legajo)
        ORDER BY f.fecha DESC
    """)
    List<ProductosStockFlujo> findFlujosAltasYBajasByProductoStockId(
            @Param("productoStockId") Integer productoStockId,
            @Param("legajo") Long legajo
    );

    @Query("""
        SELECT f
        FROM ProductosStockFlujo f
        WHERE f.productoStock.id = :productoStockId
          AND f.tipo IN ('custodia_alta', 'custodia_baja')
          AND f.totalLegajoCustodia > 0
          AND NOT EXISTS (
              SELECT 1
              FROM ProductosStockFlujo f2
              WHERE f2.productoStock.id = f.productoStock.id
                AND f2.empleadoCustodia = f.empleadoCustodia
                AND f2.tipo IN ('custodia_alta', 'custodia_baja')
                AND f2.fecha > f.fecha
        )
    """)
    List<ProductosStockFlujo> findCustodiasPorProducto(@Param("productoStockId") Integer productoStockId);

    @Query("""
        SELECT f
        FROM ProductosStockFlujo f
        WHERE f.productoStock.id = :productoStockId
          AND f.empleadoCustodia = :empleadoCustodia
          AND f.tipo IN ('custodia_alta', 'custodia_baja')
        ORDER BY f.fecha DESC
        LIMIT 1
    """)
    ProductosStockFlujo findUltimoFlujoCustodia(@Param("productoStockId") Integer productoStockId, @Param("empleadoCustodia") Long empleadoCustodia);

}
