package com.mcpd.dto;

/**
 * DTO que representa un resumen consolidado de stock por producto.
 *
 * Producto
 *    ↓
 * SUM(stock total)
 *    ↓
 * SUM(stock en custodia)
 *    ↓
 * Stock disponible
 *
 * <p>
 * Se utiliza en consultas agregadas dentro del módulo
 * {@code ProductosStock} para obtener totales agrupados
 * por producto.
 * </p>
 *
 * <h3>Finalidad</h3>
 * <ul>
 *   <li>Mostrar stock total por producto</li>
 *   <li>Diferenciar unidades en custodia y disponibles</li>
 *   <li>Servir como base para reportes o vistas de dashboard</li>
 * </ul>
 *
 * <p>
 * No representa una entidad persistente, sino el resultado
 * de consultas agregadas (por ejemplo con GROUP BY).
 * </p>
 *
 * <p>
 * Complementa a {@code StockCategoriaDto}, pero a nivel
 * de producto específico.
 * </p>
 */
public class StockProductoDto {

    private Integer productoId;
    private String productoNombre;
    private String categoriaNombre;
    private Long total;
    private Long totalCustodia;
    private Long totalDisponible;

    public StockProductoDto(Integer productoId, String productoNombre, String categoriaNombre,
                            Long total, Long totalCustodia, Long totalDisponible) {

        /** Identificador del producto. */
        this.productoId = productoId;

        /** Nombre descriptivo del producto. */
        this.productoNombre = productoNombre;

        /** Nombre de la categoría a la que pertenece el producto. */
        this.categoriaNombre = categoriaNombre;

        /**
         * Total general de unidades registradas del producto.
         *
         * Incluye unidades en custodia y disponibles.
         */
        this.total = total;

        /**
         * Total de unidades actualmente asignadas en custodia.
         *
         * Representa el stock entregado a empleados.
         */
        this.totalCustodia = totalCustodia;

        /**
         * Total de unidades disponibles en pañol.
         *
         * Calculado como:
         * total - totalCustodia
         */
        this.totalDisponible = totalDisponible;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalCustodia() {
        return totalCustodia;
    }

    public void setTotalCustodia(Long totalCustodia) {
        this.totalCustodia = totalCustodia;
    }

    public Long getTotalDisponible() {
        return totalDisponible;
    }

    public void setTotalDisponible(Long totalDisponible) {
        this.totalDisponible = totalDisponible;
    }
}