package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "comprasAdquisicionObrasPublicasPresupuestoDetalle")

public class PresupuestoDeAdquisicionObraPublicaDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprasAdquisicionPresupuesto", nullable = false)
    private PresupuestoDeAdquisicionObraPublica comprasAdquisicionPresupuesto;

    @Column(nullable = false)
    private double cantidad;

    @Column(length = 50, nullable = false)
    private String codigo = "detalle";

    @Column(length = 255, nullable = false)
    private String detalle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String rubro;

    @Column(nullable = false)
    private double montoUnitario;

    @Column(length = 255)
    private String observaciones;

    @Column(nullable = false)
    private boolean aprobado = false;

    private Long itemPedidoAdquisicion;

    @Column(nullable = false)
    private double adjudicado = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PresupuestoDeAdquisicionObraPublica getComprasAdquisicionPresupuesto() {
        return comprasAdquisicionPresupuesto;
    }

    public void setComprasAdquisicionPresupuesto(PresupuestoDeAdquisicionObraPublica comprasAdquisicionPresupuesto) {
        this.comprasAdquisicionPresupuesto = comprasAdquisicionPresupuesto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
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

    public double getMontoUnitario() {
        return montoUnitario;
    }

    public void setMontoUnitario(double montoUnitario) {
        this.montoUnitario = montoUnitario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Long getItemPedidoAdquisicion() {
        return itemPedidoAdquisicion;
    }

    public void setItemPedidoAdquisicion(Long itemPedidoAdquisicion) {
        this.itemPedidoAdquisicion = itemPedidoAdquisicion;
    }

    public double getAdjudicado() {
        return adjudicado;
    }

    public void setAdjudicado(double adjudicado) {
        this.adjudicado = adjudicado;
    }
}
