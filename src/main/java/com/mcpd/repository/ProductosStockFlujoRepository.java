package com.mcpd.repository;

import com.mcpd.dto.PrestamoPendienteDto;
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

    @Query("""
        SELECT f
        FROM ProductosStockFlujo f
        WHERE f.tipo = 'custodia_alta'
          AND f.empleadoCustodia = :legajo
          AND f.fechaDevolucion IS NOT NULL
          AND (
            SELECT COALESCE(SUM(b.cantidad), 0)
            FROM ProductosStockFlujo b
            WHERE b.tipo = 'custodia_baja'
              AND b.empleadoCustodia = f.empleadoCustodia
              AND b.productoStock = f.productoStock
              AND b.fecha > f.fecha
          ) < f.cantidad
        ORDER BY f.fecha
    """)
    List<ProductosStockFlujo> findPrestamosPendientesDeDevolucionPorLegajo(@Param("legajo") Long legajo);

    @Query("""
        SELECT f
        FROM ProductosStockFlujo f
        WHERE f.tipo IN ('custodia_alta', 'custodia_baja')
          AND f.empleadoCustodia = :legajo
          AND (f.tipo != 'custodia_alta' OR f.fechaDevolucion IS NOT NULL)
        ORDER BY f.productoStock.id, f.fecha
    """)
    List<ProductosStockFlujo> findFlujoDeStockConFechaDevolucionPorLegajo(@Param("legajo") Long legajo);
}
