package com.mcpd.dto;

/**
 * DTO que representa un resumen consolidado de stock por categoría.
 *
 * Categoría
 *    ↓
 * SUM(stock total)
 *    ↓
 * SUM(stock en custodia)
 *    ↓
 * Stock disponible
 *
 * <p>
 * Se utiliza en consultas agregadas del módulo de
 * {@code ProductosStock} para obtener totales agrupados
 * por {@code ProductosCategoria}.
 * </p>
 *
 * <h3>Finalidad</h3>
 * <ul>
 *   <li>Mostrar totales generales por categoría</li>
 *   <li>Separar stock en custodia y disponible</li>
 *   <li>Facilitar reportes o dashboards</li>
 * </ul>
 *
 * <p>
 * No representa una entidad persistente, sino el resultado
 * de una consulta agregada (generalmente con GROUP BY).
 * </p>
 */
public class StockCategoriaDto {

    /** Identificador de la categoría. */
    private Integer categoriaId;

    /** Nombre descriptivo de la categoría. */
    private String categoriaNombre;

    /**
     * Total general de unidades registradas en la categoría.
     *
     * Incluye tanto unidades en custodia como disponibles.
     */
    private Long total;

    /**
     * Total de unidades actualmente asignadas en custodia.
     *
     * Representa el stock entregado a empleados.
     */
    private Long totalCustodia;

    /**
     * Total de unidades disponibles en pañol.
     *
     * Calculado como:
     * total - totalCustodia
     */
    private Long totalDisponible;

    public StockCategoriaDto(Integer categoriaId, String categoriaNombre,
                             Long total, Long totalCustodia, Long totalDisponible) {
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.total = total;
        this.totalCustodia = totalCustodia;
        this.totalDisponible = totalDisponible;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
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