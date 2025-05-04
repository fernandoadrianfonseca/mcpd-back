package com.mcpd.repository;

import com.mcpd.dto.StockConCustodiaDto;
import com.mcpd.model.ProductosStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductosStockRepository extends JpaRepository<ProductosStock, Integer> {

    @Query("""
        SELECT new com.mcpd.dto.StockConCustodiaDto(f.productoStock, f.totalLegajoCustodia)
        FROM ProductosStockFlujo f
        WHERE f.tipo IN ('custodia_alta', 'custodia_baja')
          AND f.totalLegajoCustodia > 0
          AND f.empleadoCustodia = :legajoCustodia
          AND NOT EXISTS (
              SELECT 1
              FROM ProductosStockFlujo f2
              WHERE f2.productoStock.id = f.productoStock.id
                AND f2.empleadoCustodia = f.empleadoCustodia
                AND f2.tipo IN ('custodia_alta', 'custodia_baja')
                AND f2.fecha > f.fecha
          )
    """)
    List<StockConCustodiaDto> findStockActualPorCustodia(@Param("legajoCustodia") Long legajoCustodia);

    @Query("""
        SELECT ps
        FROM ProductosStock ps
        WHERE (ps.cantidad - ps.cantidadCustodia) > 0
    """)
    List<ProductosStock> findStockDisponibleParaAsignar();
}
