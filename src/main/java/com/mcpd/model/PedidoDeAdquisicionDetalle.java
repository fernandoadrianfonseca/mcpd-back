package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "comprasAdquisicionPedidoDetalle")
public class PedidoDeAdquisicionDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprasAdquisicionPedido", nullable = false)
    private PedidoDeAdquisicion pedido;

    @Column(nullable = false)
    private double cantidad;

    @Column(nullable = false, length = 50, columnDefinition = "nvarchar(50) default '0'")
    private String codigo;

    @Column(nullable = false, length = 255)
    private String detalle;

    @Column(nullable = false)
    private double montoUnitario;

    @Column(length = 255)
    private String observaciones;

    @Column(nullable = false, columnDefinition = "nvarchar(max)")
    private String rubro;

    @Column(nullable = false, length = 50)
    private String planDeCuentas;

    @Column(nullable = false, length = 50)
    private String imputacion;

    @Column(nullable = false)
    private boolean presupuestado = false;

    @Column(nullable = false)
    private boolean adquirido = false;

    @Column(nullable = false, columnDefinition = "varchar(max) default 'generico'")
    private String marca;

    @Column(nullable = false, columnDefinition = "varchar(max) default 'INSUMO'")
    private String tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PedidoDeAdquisicion getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDeAdquisicion pedido) {
        this.pedido = pedido;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}