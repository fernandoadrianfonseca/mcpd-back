package com.mcpd.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "productos_stock_flujo")
public class ProductosStockFlujo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_stock")
    private ProductosStock productoStock;

    @Column(nullable = false)
    private Long cantidad;

    @Column(nullable = false)
    private Long total;

    @Column(name = "total_legajo_custodia")
    private Long totalLegajoCustodia;

    @Column(nullable = false, length = 10)
    private String tipo;

    @Column(name = "empleado_custodia")
    private Long empleadoCustodia;

    @Column(name = "empleado_carga", nullable = false)
    private Long empleadoCarga;

    @Column
    private String remito;

    @Column(name = "orden_de_compra")
    private String ordenDeCompra;

    @Column
    private BigDecimal precio;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String observaciones;

    @Column(name = "motivo_baja", columnDefinition = "NVARCHAR(MAX)")
    private String motivoBaja;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_devolucion")
    private Date fechaDevolucion;

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
