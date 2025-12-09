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

    @Query(value = """
        SELECT pi.*
        FROM productos_informacion pi
        JOIN productos_stock_flujo pf ON pf.id = pi.id_producto_flujo
        WHERE pf.producto_stock = :idStock
          AND pi.empleado_custodia IS NULL
          AND pi.activo = 1
          AND pi.id NOT IN (:idsYaElegidos)  
        ORDER BY pi.id
        OFFSET 0 ROWS
        FETCH NEXT :cantidad ROWS ONLY
    """, nativeQuery = true)
    List<ProductosInformacion> findCodigosLibres(@Param("idStock") Integer idStock,
                                    @Param("cantidad") Integer cantidad,
                                    @Param("idsYaElegidos") List<Integer> idsYaElegidos);

    @Query(value = """
        SELECT pi.*
        FROM productos_informacion pi
        JOIN productos_stock_flujo pf ON pf.id = pi.id_producto_flujo
        WHERE pf.producto_stock = :idStock
          AND pi.empleado_custodia = :legajo
          AND pi.activo = 1
          AND pi.id NOT IN (:idsYaElegidos)  
        ORDER BY pi.id
        OFFSET 0 ROWS
        FETCH NEXT :cantidad ROWS ONLY
    """, nativeQuery = true)
    List<ProductosInformacion> findCodigosEnCustodiaXEmpleado(@Param("idStock") Integer idStock,
                                                 @Param("cantidad") Integer cantidad,
                                                 @Param("legajo") Integer legajo,
                                                 @Param("idsYaElegidos") List<Integer> idsYaElegidos);

    @Query(value = """
        SELECT pi.*
        FROM productos_informacion pi
        JOIN productos_stock_flujo pf ON pf.id = pi.id_producto_flujo
        WHERE pf.producto_stock = :idStock
          AND pi.empleado_custodia IS NULL
          AND pi.activo = 1
          AND pi.id NOT IN (:idsYaElegidos)  
        ORDER BY pi.id
        OFFSET 0 ROWS
        FETCH NEXT :cantidad ROWS ONLY
    """, nativeQuery = true)
    List<ProductosInformacion> findActivosXStockId(@Param("idStock") Integer idStock,
                                                  @Param("cantidad") Integer cantidad,
                                                  @Param("idsYaElegidos") List<Integer> idsYaElegidos);

    boolean existsByActivoTrueAndNumeroDeSerie(String numeroDeSerie);
    boolean existsByActivoTrueAndCodigoAntiguo(String codigoAntiguo);
}
