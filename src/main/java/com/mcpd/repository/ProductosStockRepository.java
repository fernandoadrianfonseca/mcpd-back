package com.mcpd.repository;

import com.mcpd.dto.StockCategoriaDto;
import com.mcpd.dto.StockConCustodiaDto;
import com.mcpd.dto.StockProductoDto;
import com.mcpd.model.ProductosStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link ProductosStock}.
 *
 * <p>
 * Proporciona operaciones de acceso a datos para:
 * - Estado actual del inventario
 * - Cálculo de stock disponible
 * - Consolidación por categoría y producto
 * - Consulta de stock en custodia por empleado
 *
 * Algunas consultas utilizan lógica basada en
 * {@link com.mcpd.model.ProductosStockFlujo}
 * para determinar el estado más reciente de custodia.
 *
 * Este repositorio no contiene reglas de negocio,
 * únicamente consultas optimizadas a nivel de persistencia.
 */

@Repository
public interface ProductosStockRepository extends JpaRepository<ProductosStock, Integer> {

    /**
     * Obtiene el stock actualmente en custodia de un empleado.
     *
     * <p>
     * La consulta:
     * - Considera movimientos "custodia_alta" y "custodia_baja".
     * - Toma únicamente el último movimiento por producto y empleado.
     * - Devuelve solo aquellos cuyo total en custodia sea mayor a cero.
     *
     * Utiliza una subconsulta NOT EXISTS para asegurar
     * que se trate del registro más reciente.
     *
     * @param legajoCustodia Legajo del empleado.
     * @return Lista de stock actualmente asignado al empleado.
     */
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

    /**
     * Obtiene el stock actualmente en custodia,
     * excluyendo el legajo indicado.
     *
     * <p>
     * Se utiliza principalmente para procesos de transferencia,
     * donde se necesita visualizar stock asignado a otros empleados.
     *
     * Aplica la misma lógica de último movimiento
     * que {@link #findStockActualPorCustodia(Long)}.
     *
     * @param legajoExcluir Legajo a excluir de la consulta.
     * @return Lista de stock en custodia de otros empleados.
     */
    @Query("""
        SELECT new com.mcpd.dto.StockConCustodiaDto(f.productoStock, f.totalLegajoCustodia)
        FROM ProductosStockFlujo f
        WHERE f.tipo IN ('custodia_alta', 'custodia_baja')
          AND f.totalLegajoCustodia > 0
          AND f.empleadoCustodia <> :legajoExcluir
          AND NOT EXISTS (
              SELECT 1
              FROM ProductosStockFlujo f2
              WHERE f2.productoStock.id = f.productoStock.id
                AND f2.empleadoCustodia = f.empleadoCustodia
                AND f2.tipo IN ('custodia_alta', 'custodia_baja')
                AND f2.fecha > f.fecha
          )
    """)
    List<StockConCustodiaDto> findStockActualExcluyendoCustodia(@Param("legajoExcluir") Long legajoExcluir);

    /**
     * Obtiene los productos de stock que poseen unidades disponibles
     * para nuevas asignaciones.
     *
     * <p>
     * Se consideran disponibles aquellos donde:
     *
     * cantidad - cantidadCustodia > 0
     *
     * @return Lista de ítems con stock disponible.
     */
    @Query("""
        SELECT ps
        FROM ProductosStock ps
        WHERE (ps.cantidad - ps.cantidadCustodia) > 0
    """)
    List<ProductosStock> findStockDisponibleParaAsignar();

    /**
     * Devuelve un resumen consolidado del stock agrupado por categoría.
     *
     * <p>
     * Calcula:
     * - Total físico
     * - Total en custodia
     * - Total disponible
     *
     * Utiliza agregaciones SUM sobre la tabla ProductosStock.
     *
     * @return Lista de resumen de stock por categoría.
     */
    @Query("""
    SELECT 
        new com.mcpd.dto.StockCategoriaDto(
            ps.categoria.id,
            ps.categoriaNombre,
            SUM(ps.cantidad),
            SUM(ps.cantidadCustodia),
            SUM(ps.cantidad - ps.cantidadCustodia)
        )
    FROM ProductosStock ps
    GROUP BY ps.categoria.id, ps.categoriaNombre
    """)
    List<StockCategoriaDto> obtenerStockPorCategoria();

    /**
     * Devuelve un resumen consolidado del stock agrupado por producto.
     *
     * <p>
     * Calcula:
     * - Total físico
     * - Total en custodia
     * - Total disponible
     *
     * Permite obtener una vista global del inventario
     * sin necesidad de procesar datos en el backend.
     *
     * @return Lista de resumen de stock por producto.
     */
    @Query("""
    SELECT 
        new com.mcpd.dto.StockProductoDto(
            ps.producto.id,
            ps.productoNombre,
            ps.categoriaNombre,
            SUM(ps.cantidad),
            SUM(ps.cantidadCustodia),
            SUM(ps.cantidad - ps.cantidadCustodia)
        )
    FROM ProductosStock ps
    GROUP BY ps.producto.id, ps.productoNombre, ps.categoriaNombre
    """)
    List<StockProductoDto> obtenerStockPorProducto();
}
