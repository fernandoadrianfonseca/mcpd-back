package com.mcpd.dto;

import com.mcpd.model.ProductosStockFlujo;
import java.util.Date;

/**
 * DTO que representa un préstamo pendiente de devolución asociado
 * a un movimiento de tipo "custodia_alta".
 *
 * <p>
 * Este objeto se utiliza para exponer al frontend los movimientos
 * de stock que aún tienen cantidad pendiente de devolver,
 * luego de descontar cronológicamente los movimientos
 * "custodia_baja" (devoluciones parciales o totales).
 *
 * <h3>Contexto funcional</h3>
 *
 * En el modelo de flujo contable:
 * - Un movimiento "custodia_alta" genera una asignación a un empleado.
 * - Los movimientos "custodia_baja" van descontando esa asignación.
 * - La diferencia entre ambos determina la cantidad pendiente.
 *
 * Este DTO representa el estado resultante de ese cálculo.
 *
 * <h3>Campos</h3>
 *
 * <ul>
 *   <li><b>id</b>: Identificador del movimiento original de custodia_alta.</li>
 *   <li><b>flujo</b>: Movimiento original registrado en productos_stock_flujo.</li>
 *   <li><b>cantidadPendiente</b>: Cantidad aún no devuelta.</li>
 *   <li><b>fechaDevolucion</b>: Fecha límite o estimada de devolución (formateada).</li>
 *   <li><b>estadoDevolucion</b>: Estado calculado del préstamo
 *       (Ej.: "PENDIENTE", "VENCIDO", "DEVUELTO_PARCIAL").</li>
 * </ul>
 *
 * <p>
 * Este DTO no representa una entidad persistente,
 * sino una proyección calculada desde el backend.
 */

public class PrestamoPendienteDto {

    private Integer id;
    private ProductosStockFlujo flujo;
    private Long cantidadPendiente;
    private String fechaDevolucion;
    private String estadoDevolucion;

    public PrestamoPendienteDto(Integer id, ProductosStockFlujo flujo, Long cantidadPendiente, String fechaDevolucion, String estadoDevolucion) {
        this.id = id;
        this.flujo = flujo;
        this.cantidadPendiente = cantidadPendiente;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoDevolucion = estadoDevolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductosStockFlujo getFlujo() {
        return flujo;
    }

    public void setFlujo(ProductosStockFlujo flujo) {
        this.flujo = flujo;
    }

    public Long getCantidadPendiente() {
        return cantidadPendiente;
    }

    public void setCantidadPendiente(Long cantidadPendiente) {
        this.cantidadPendiente = cantidadPendiente;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstadoDevolucion() {
        return estadoDevolucion;
    }

    public void setEstadoDevolucion(String estadoDevolucion) {
        this.estadoDevolucion = estadoDevolucion;
    }
}