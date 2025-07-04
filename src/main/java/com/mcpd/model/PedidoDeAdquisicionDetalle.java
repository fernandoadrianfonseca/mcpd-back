package com.mcpd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comprasadquisicionpedidodetalle")
public class PedidoDeAdquisicionDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprasadquisicionpedido", nullable = false)
    @JsonBackReference
    private PedidoDeAdquisicion pedido;

    @Column(name = "cantidad", nullable = false)
    private double cantidad;

    @Column(name = "codigo", nullable = false, length = 50, columnDefinition = "nvarchar(50) default '0'")
    private String codigo;

    @Column(name = "detalle", nullable = false, length = 255)
    private String detalle;

    @Column(name = "montounitario", nullable = false)
    private double montoUnitario;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    @Column(name = "rubro", nullable = false, columnDefinition = "nvarchar(max)")
    private String rubro;

    @Column(name = "plandecuentas", nullable = false, length = 50)
    private String planDeCuentas;

    @Column(name = "imputacion", nullable = false, length = 50)
    private String imputacion;

    @Column(name = "presupuestado", nullable = false)
    private boolean presupuestado = false;

    @Column(name = "adquirido", nullable = false)
    private boolean adquirido = false;

    @Column(name = "marca", nullable = false, columnDefinition = "varchar(max) default 'generico'")
    private String marca;

    @Column(name = "tipo", nullable = false, columnDefinition = "varchar(max) default 'INSUMO'")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "productostockid")
    private ProductosStock productoStock;

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

    public ProductosStock getProductoStock() {
        return productoStock;
    }

    public void setProductoStock(ProductosStock productoStock) {
        this.productoStock = productoStock;
    }
}