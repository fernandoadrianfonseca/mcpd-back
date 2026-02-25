package com.mcpd.repository;

import com.mcpd.model.ProductosStockFlujo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link ProductosStockFlujo}.
 *
 * <p>
 * Gestiona el acceso a datos del libro contable de movimientos de stock,
 * incluyendo:
 *
 * <ul>
 *   <li>Ingresos ("alta")</li>
 *   <li>Bajas definitivas ("baja")</li>
 *   <li>Asignaciones a empleados ("custodia_alta")</li>
 *   <li>Devoluciones ("custodia_baja")</li>
 * </ul>
 *
 * <h3>Responsabilidades principales</h3>
 *
 * <ul>
 *   <li>Consultar historial de movimientos por producto</li>
 *   <li>Determinar custodias activas</li>
 *   <li>Obtener el último movimiento de custodia por empleado</li>
 *   <li>Calcular préstamos pendientes de devolución</li>
 *   <li>Recuperar flujos ordenados para procesamiento FIFO</li>
 * </ul>
 *
 * Algunas consultas implementan lógica basada en:
 * - Subconsultas NOT EXISTS para obtener el último estado válido
 * - Sumas acumuladas de "custodia_baja" posteriores a una "custodia_alta"
 *   para determinar cantidad pendiente de devolución
 *
 * Este repositorio no aplica reglas de negocio,
 * pero sí encapsula consultas complejas necesarias
 * para el cálculo del estado actual de custodia.
 */

@Repository
public interface ProductosStockFlujoRepository extends JpaRepository<ProductosStockFlujo, Integer> {

    /**
     * Obtiene todos los registros de flujo asociados a un producto de stock, sin aplicar filtros adicionales.
     */
    List<ProductosStockFlujo> findByProductoStock_Id(Integer productoStockId);

    /**
     * Obtiene los movimientos de ingreso (alta) de un producto que poseen número de remito registrado.
     */
    @Query("SELECT f FROM ProductosStockFlujo f WHERE f.productoStock.id = :productoStockId AND f.tipo = 'alta' AND f.remito IS NOT NULL AND f.remito <> ''")
    List<ProductosStockFlujo> findRemitosByProductoStockId(@Param("productoStockId") Integer productoStockId);

    /**
     * Obtiene el historial de flujos (alta/baja/custodia) de un producto, con filtro opcional por legajo, ordenado por fecha descendente.
     */
    @Query("""
        SELECT f FROM ProductosStockFlujo f
        WHERE f.productoStock.id = :productoStockId
          AND f.tipo IN ('alta','baja','custodia_alta','custodia_baja')
          AND (:legajo IS NULL OR f.empleadoCustodia = :legajo)
        ORDER BY f.fecha DESC
    """)
    List<ProductosStockFlujo> findFlujosAltasYBajasByProductoStockId(
            @Param("productoStockId") Integer productoStockId,
            @Param("legajo") Long legajo
    );

    /**
     * Obtiene la custodia actual por empleado para un productoStock dado,
     * devolviendo el último movimiento de custodia (alta/baja) de cada empleado cuyo totalLegajoCustodia quedó mayor a 0.
     */
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

    /**
     * Obtiene el último movimiento de custodia (alta o baja) registrado para un producto específico y un empleado determinado, ordenado por fecha descendente.
     */
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

    /**
     * Devuelve las custodia_alta con fechaDevolucion para un empleado,
     * cuyo total de custodia_baja posteriores (mismo producto y empleado) no alcanza a cubrir la cantidad prestada, ordenadas por fecha.
     */
    @Query("""
        SELECT f
        FROM ProductosStockFlujo f
        WHERE f.tipo = 'custodia_alta'
          AND f.empleadoCustodia = :legajo
          AND f.fechaDevolucion IS NOT NULL
          AND CAST((
            SELECT COALESCE(SUM(b.cantidad), 0)
            FROM ProductosStockFlujo b
            WHERE b.tipo = 'custodia_baja'
              AND b.empleadoCustodia = f.empleadoCustodia
              AND b.productoStock = f.productoStock
              AND b.fecha > f.fecha
          ) as Long) < f.cantidad
        ORDER BY f.fecha
    """)
    List<ProductosStockFlujo> findPrestamosPendientesDeDevolucionPorLegajo(@Param("legajo") Long legajo);

    /**
     * Devuelve el flujo de movimientos de custodia (altas y bajas) de un empleado,
     * excluyendo las asignaciones activas sin fecha de devolución, ordenado por producto y fecha.
     */
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
