package com.mcpd.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProductosStock")
public class ProductosStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private ProductosCategoria categoria;

    @Column(nullable = false)
    private String categoriaNombre;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Long precio;

    private String marca;
    private String modelo;
    private String detalle;

    @Column(nullable = false)
    private Long unidades;

    private String numeroDeSerie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaDeCarga;

    @Column(nullable = false, length = 255)
    private String tipo;

    private String ordenDeCompra;
    private String remito;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long custodia;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long acta;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long transfiere;

    private String motivoBaja;
    private String fechaDeDevolucion;
    private String observaciones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductosCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ProductosCategoria categoria) {
        this.categoria = categoria;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Long getUnidades() {
        return unidades;
    }

    public void setUnidades(Long unidades) {
        this.unidades = unidades;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public Date getFechaDeCarga() {
        return fechaDeCarga;
    }

    public void setFechaDeCarga(Date fechaDeCarga) {
        this.fechaDeCarga = fechaDeCarga;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(String ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public Long getCustodia() {
        return custodia;
    }

    public void setCustodia(Long custodia) {
        this.custodia = custodia;
    }

    public Long getActa() {
        return acta;
    }

    public void setActa(Long acta) {
        this.acta = acta;
    }

    public Long getTransfiere() {
        return transfiere;
    }

    public void setTransfiere(Long transfiere) {
        this.transfiere = transfiere;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getFechaDeDevolucion() {
        return fechaDeDevolucion;
    }

    public void setFechaDeDevolucion(String fechaDeDevolucion) {
        this.fechaDeDevolucion = fechaDeDevolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
