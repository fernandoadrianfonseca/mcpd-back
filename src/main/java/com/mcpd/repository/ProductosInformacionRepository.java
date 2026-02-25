package com.mcpd.repository;

import com.mcpd.model.ProductosInformacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link ProductosInformacion}.
 *
 * <p>
 * Gestiona el acceso a datos de las unidades individuales
 * de bienes inventariables (uno por uno).
 *
 * Permite:
 * <ul>
 *   <li>Consultar unidades por movimiento de flujo</li>
 *   <li>Filtrar por estado activo/inactivo</li>
 *   <li>Filtrar por empleado en custodia</li>
 *   <li>Obtener códigos disponibles para asignación</li>
 *   <li>Validar unicidad de número de serie y código antiguo</li>
 * </ul>
 *
 * Este repositorio trabaja sobre la capa unitaria del inventario,
 * complementando el modelo consolidado de {@link com.mcpd.model.ProductosStock}.
 */

@Repository
public interface ProductosInformacionRepository extends JpaRepository<ProductosInformacion, Integer> {

    /**
     * Obtiene todas las unidades asociadas a un movimiento específico
     * de {@link com.mcpd.model.ProductosStockFlujo}.
     *
     * @param productoFlujoId id del movimiento de flujo.
     * @return lista de unidades creadas en ese movimiento.
     */
    List<ProductosInformacion> findByProductoFlujo_Id(Integer productoFlujoId);

    /**
     * Obtiene unidades filtradas por productoStock con filtros opcionales.
     *
     * Permite:
     * - Filtrar solo activos (si filtrarActivos = true)
     * - Filtrar por empleado en custodia (si empleadoCustodia != null)
     *
     * @param productoStockId id del producto_stock.
     * @param filtrarActivos indica si deben retornarse solo activos.
     * @param empleadoCustodia legajo del empleado (opcional).
     * @return lista de unidades según filtros aplicados.
     */
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

    /**
     * Obtiene unidades activas de un producto_stock que
     * actualmente no se encuentran asignadas a ningún empleado.
     *
     * Se utiliza para:
     * - Seleccionar bienes disponibles para custodia
     * - Procesos de asignación unitaria
     *
     * @param productoStockId id del producto_stock.
     * @return lista de unidades activas sin custodia.
     */
    @Query("""
        SELECT ns FROM ProductosInformacion ns
        WHERE ns.productoFlujo.productoStock.id = :productoStockId
          AND ns.activo = true
          AND ns.empleadoCustodia IS NULL
    """)
    List<ProductosInformacion> findActivosSinCustodiaPorProductoStock(
            @Param("productoStockId") Integer productoStockId
    );

    /**
     * Obtiene una cantidad limitada de unidades activas sin custodia
     * para un producto_stock determinado.
     *
     * Utiliza consulta nativa con paginación OFFSET/FETCH
     * para seleccionar un subconjunto controlado.
     *
     * Permite excluir ids previamente seleccionados
     * (por ejemplo, en procesos de asignación parcial).
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima de unidades a obtener.
     * @param idsYaElegidos ids a excluir de la selección.
     * @return lista limitada de unidades disponibles.
     */
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

    /**
     * Obtiene una cantidad limitada de unidades activas
     * que actualmente se encuentran en custodia de un empleado.
     *
     * Se utiliza en procesos de devolución o transferencia
     * para seleccionar unidades específicas.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima de unidades a obtener.
     * @param legajo legajo del empleado.
     * @param idsYaElegidos ids a excluir de la selección.
     * @return lista limitada de unidades en custodia del empleado.
     */
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

    /**
     * Obtiene unidades activas disponibles para un producto_stock,
     * limitando la cantidad de resultados y excluyendo ids específicos.
     *
     * Similar a findCodigosLibres pero utilizada en otros contextos
     * donde no se requiere diferenciar custodia.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima de unidades a obtener.
     * @param idsYaElegidos ids a excluir.
     * @return lista limitada de unidades activas.
     */
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

    /**
     * Verifica si existe una unidad activa con el número de serie indicado.
     *
     * Se utiliza para evitar duplicidad de número de serie
     * dentro del inventario vigente.
     *
     * @param numeroDeSerie número de serie a validar.
     * @return true si ya existe una unidad activa con ese número.
     */
    boolean existsByActivoTrueAndNumeroDeSerie(String numeroDeSerie);

    /**
     * Verifica si existe una unidad activa con el código patrimonial antiguo indicado.
     *
     * Permite evitar duplicidad en procesos de migración o carga histórica.
     *
     * @param codigoAntiguo código antiguo a validar.
     * @return true si ya existe una unidad activa con ese código.
     */
    boolean existsByActivoTrueAndCodigoAntiguo(String codigoAntiguo);
}
