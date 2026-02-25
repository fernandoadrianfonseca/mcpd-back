package com.mcpd.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa el historial de movimientos del stock.
 *
 * <p>
 * Cada registro corresponde a un movimiento histórico que afecta
 * un ítem de {@link ProductosStock}.
 *
 * Esta tabla funciona como:
 * - Historial de auditoría
 * - Trazabilidad de ingresos y egresos
 * - Snapshot del estado posterior al movimiento
 *
 * ⚠ Importante:
 * Esta entidad NO modifica directamente el stock.
 * El servicio debe actualizar manualmente:
 * - productos_stock.cantidad
 * - productos_stock.cantidad_custodia
 *
 * Valores válidos del campo {@code tipo}:
 * - "alta" → ingreso general al stock
 * - "baja" → egreso definitivo (baja de inventario)
 * - "custodia_alta" → asignación a empleado
 * - "custodia_baja" → devolución de empleado
 */
@Entity
@Table(name = "productos_stock_flujo")
public class ProductosStockFlujo {

    /**
     * Identificador único del movimiento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Ítem de stock afectado.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_stock")
    private ProductosStock productoStock;

    /**
     * Cantidad movida en este evento.
     */
    @Column(nullable = false)
    private Long cantidad;

    /**
     * Snapshot del total general del stock
     * luego de aplicar este movimiento.
     *
     * Se guarda con fines de auditoría histórica.
     */
    @Column(nullable = false)
    private Long total;

    /**
     * Snapshot del total en custodia para el empleado
     * luego del movimiento.
     *
     * Aplica solo para movimientos de custodia.
     */
    @Column(name = "total_legajo_custodia")
    private Long totalLegajoCustodia;

    /**
     * Tipo de movimiento.
     *
     * Valores posibles:
     * - "alta"
     * - "baja"
     * - "custodia_alta"
     * - "custodia_baja"
     */
    @Column(nullable = false, length = 10)
    private String tipo;

    /**
     * Legajo del empleado que recibe o devuelve el bien.
     * Solo aplica en movimientos de custodia.
     */
    @Column(name = "empleado_custodia")
    private Long empleadoCustodia;

    /**
     * Legajo del usuario que registra el movimiento.
     * Siempre obligatorio.
     */
    @Column(name = "empleado_carga", nullable = false)
    private Long empleadoCarga;

    /**
     * Número de remito asociado (si corresponde).
     */
    @Column
    private String remito;

    /**
     * Número de orden de compra asociada (si corresponde).
     */
    @Column(name = "orden_de_compra")
    private String ordenDeCompra;

    /**
     * Precio unitario o total asociado al movimiento.
     * Se utiliza para trazabilidad contable.
     */
    @Column
    private BigDecimal precio;

    /**
     * Observaciones generales del movimiento.
     */
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String observaciones;

    /**
     * Motivo en caso de movimiento tipo "baja".
     */
    @Column(name = "motivo_baja", columnDefinition = "NVARCHAR(MAX)")
    private String motivoBaja;

    /**
     * Fecha del movimiento.
     * Se asigna automáticamente al persistir.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    /**
     * Fecha efectiva de devolución.
     * Aplica cuando el bien tiene devolución.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_devolucion")
    private Date fechaDevolucion;

    /**
     * Asigna la fecha actual automáticamente
     * antes de persistir el registro.
     */
    @PrePersist
    protected void onCreate() {
        fecha = new Date();
    }

    // Getters y Setters

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public ProductosStock getProductoStock() { return productoStock; }

    public void setProductoStock(ProductosStock productoStock) { this.productoStock = productoStock; }

    public Long getCantidad() { return cantidad; }

    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }

    public Long getTotal() { return total; }

    public void setTotal(Long total) { this.total = total; }

    public Long getTotalLegajoCustodia() {
        return totalLegajoCustodia;
    }

    public void setTotalLegajoCustodia(Long totalLegajoCustodia) {
        this.totalLegajoCustodia = totalLegajoCustodia;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getEmpleadoCustodia() { return empleadoCustodia; }

    public void setEmpleadoCustodia(Long empleadoCustodia) { this.empleadoCustodia = empleadoCustodia; }

    public Long getEmpleadoCarga() { return empleadoCarga; }

    public void setEmpleadoCarga(Long empleadoCarga) { this.empleadoCarga = empleadoCarga; }

    public String getRemito() { return remito; }

    public void setRemito(String remito) { this.remito = remito; }

    public String getOrdenDeCompra() { return ordenDeCompra; }

    public void setOrdenDeCompra(String ordenDeCompra) { this.ordenDeCompra = ordenDeCompra; }

    public BigDecimal getPrecio() { return precio; }

    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getMotivoBaja() { return motivoBaja; }

    public void setMotivoBaja(String motivoBaja) { this.motivoBaja = motivoBaja; }

    public Date getFecha() { return fecha; }

    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
