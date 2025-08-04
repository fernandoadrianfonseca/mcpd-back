package com.mcpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "comprasadquisicionpresupuestodetalle")
public class PresupuestoDeAdquisicionDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprasadquisicionpresupuesto", nullable = false)
    @JsonBackReference
    private PresupuestoDeAdquisicion comprasadquisicionpresupuesto;

    @Column(nullable = false)
    private Double cantidad;

    @Column(length = 50, columnDefinition = "nvarchar(50) default 'detalle'")
    private String codigo;

    @Column(nullable = false, length = 8000)
    private String detalle;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String rubro;

    @Column(nullable = false)
    private Double montounitario;

    @Column(columnDefinition = "varchar(max)")
    private String observaciones;

    @Column(nullable = false)
    private Boolean aprobado = false;

    private Long itempedidoadquisicion;

    @Column(nullable = false)
    private Double adjudicado = 0.0;

    @ManyToOne
    @JoinColumn(name = "productostockid")
    private ProductosStock productoStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PresupuestoDeAdquisicion getComprasadquisicionpresupuesto() {
        return comprasadquisicionpresupuesto;
    }

    public void setComprasadquisicionpresupuesto(PresupuestoDeAdquisicion comprasadquisicionpresupuesto) {
        this.comprasadquisicionpresupuesto = comprasadquisicionpresupuesto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public Double getMontoUnitario() {
        return montounitario;
    }

    public void setMontoUnitario(Double montounitario) {
        this.montounitario = montounitario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Long getItemPedidoAdquisicion() {
        return itempedidoadquisicion;
    }

    public void setItemPedidoAdquisicion(Long itemPedidoAdquisicion) {
        this.itempedidoadquisicion = itemPedidoAdquisicion;
    }

    public Double getAdjudicado() {
        return adjudicado;
    }

    public void setAdjudicado(Double adjudicado) {
        this.adjudicado = adjudicado;
    }

    public ProductosStock getProductoStock() {
        return productoStock;
    }

    public void setProductoStock(ProductosStock productoStock) {
        this.productoStock = productoStock;
    }
}
