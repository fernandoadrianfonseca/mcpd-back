package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "comprasAdquisicionPedidoObrasPublicasDetalle")
public class PedidoDeAdquisicionObraPublicaDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprasAdquisicionPedido", nullable = false)
    private PedidoDeAdquisicionObraPublica pedidoDeAdquisicionObraPublica;

    @Column(nullable = false)
    private double cantidad;

    @Column(length = 50, columnDefinition = "nvarchar(50) default 'detalle'")
    private String codigo;

    @Column(nullable = false, length = 255)
    private String detalle;

    @Column(nullable = false)
    private double montoUnitario;

    @Column(length = 255)
    private String observaciones;

    @Column(columnDefinition = "nvarchar(max)", nullable = false)
    private String rubro;

    @Column(length = 50, columnDefinition = "nvarchar(50) default 'ADMINISTRACION CENTRAL'")
    private String planDeCuentas;

    @Column(length = 50)
    private String imputacion;

    @Column(nullable = false)
    private boolean presupuestado;

    @Column(nullable = false)
    private boolean adquirido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PedidoDeAdquisicionObraPublica getPedidoDeAdquisicionObraPublica() {
        return pedidoDeAdquisicionObraPublica;
    }

    public void setPedidoDeAdquisicionObraPublica(PedidoDeAdquisicionObraPublica pedidoDeAdquisicionObraPublica) {
        this.pedidoDeAdquisicionObraPublica = pedidoDeAdquisicionObraPublica;
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

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getPlanDeCuentas() {
        return planDeCuentas;
    }

    public void setPlanDeCuentas(String planDeCuentas) {
        this.planDeCuentas = planDeCuentas;
    }

    public String getImputacion() {
        return imputacion;
    }

    public void setImputacion(String imputacion) {
        this.imputacion = imputacion;
    }

    public boolean isPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(boolean presupuestado) {
        this.presupuestado = presupuestado;
    }

    public boolean isAdquirido() {
        return adquirido;
    }

    public void setAdquirido(boolean adquirido) {
        this.adquirido = adquirido;
    }
}
