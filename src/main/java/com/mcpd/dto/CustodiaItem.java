package com.mcpd.dto;

import java.util.Date;

/**
 * DTO utilizado para representar una operación de custodia
 * sobre un ítem de {@link com.mcpd.model.ProductosStock}.
 *
 * <p>
 * Se utiliza en operaciones transaccionales del
 * {@link com.mcpd.service.ProductosStockService}, tales como:
 * <ul>
 *   <li>Asignación de custodia</li>
 *   <li>Quitar custodia</li>
 *   <li>Transferencia de custodia</li>
 * </ul>
 *
 * <p>
 * Representa una instrucción puntual sobre un producto de stock,
 * indicando qué cantidad se afecta y con qué metadatos.
 *
 * <h3>Notas</h3>
 * <ul>
 *   <li>No es una entidad persistente.</li>
 *   <li>No representa el estado consolidado del stock.</li>
 *   <li>Es un objeto de transporte entre frontend y backend.</li>
 * </ul>
 */
public class CustodiaItem {

    /**
     * Identificador del {@link com.mcpd.model.ProductosStock}
     * sobre el cual se realiza la operación.
     */
    private Integer stockId;

    /**
     * Cantidad de unidades a afectar en la operación de custodia.
     *
     * Debe ser un valor positivo.
     */
    private Long cantidad;

    /**
     * Observaciones asociadas a la operación de custodia.
     *
     * Se almacenan en el registro de {@link com.mcpd.model.ProductosStockFlujo}.
     */
    private String observaciones;

    /**
     * Fecha prevista de devolución (si aplica).
     *
     * Se utiliza únicamente cuando el producto permite devolución
     * ({@code conDevolucion = true}).
     */
    private Date fechaDevolucion;

    // getters y setters
    public Integer getStockId() { return stockId; }
    public void setStockId(Integer stockId) { this.stockId = stockId; }

    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
