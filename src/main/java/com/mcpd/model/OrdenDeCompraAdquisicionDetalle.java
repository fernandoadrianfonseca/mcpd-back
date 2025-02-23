package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "comprasAdquisicionOrdenCompraDetalle")
public class OrdenDeCompraAdquisicionDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprasAdquisicionOrdenCompra", nullable = false)
    private OrdenDeCompraAdquisicion ordenCompra;

    @Column(name = "cantidad", nullable = false)
    private Double cantidad;

    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "detalle", length = 255, nullable = false)
    private String detalle;

    @Column(name = "rubro", columnDefinition = "TEXT", nullable = false)
    private String rubro;

    @Column(name = "montoUnitario", nullable = false)
    private Double montoUnitario;

    @Column(name = "itemPedidoAdquisicion")
    private Long itemPedidoAdquisicion;

    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdenDeCompraAdquisicion getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenDeCompraAdquisicion ordenCompra) {
        this.ordenCompra = ordenCompra;
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
        return montoUnitario;
    }

    public void setMontoUnitario(Double montoUnitario) {
        this.montoUnitario = montoUnitario;
    }

    public Long getItemPedidoAdquisicion() {
        return itemPedidoAdquisicion;
    }

    public void setItemPedidoAdquisicion(Long itemPedidoAdquisicion) {
        this.itemPedidoAdquisicion = itemPedidoAdquisicion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
