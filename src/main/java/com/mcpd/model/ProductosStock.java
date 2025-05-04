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

    @ManyToOne
    @JoinColumn(name = "producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private String productoNombre;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "cantidad_custodia", nullable = false)
    private Integer cantidadCustodia;

    private String marca;
    private String modelo;
    private String detalle;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fechaDeCarga;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

    @Column(nullable = false, length = 255)
    private String tipo;

    @Column(nullable = false)
    private Boolean consumible;

    @PrePersist
    protected void onCreate() {
        fechaDeCarga = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadCustodia() {
        return cantidadCustodia;
    }

    public void setCantidadCustodia(Integer cantidadCustodia) {
        this.cantidadCustodia = cantidadCustodia;
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

    public Date getFechaDeCarga() {
        return fechaDeCarga;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getConsumible() {
        return consumible;
    }

    public void setConsumible(Boolean consumible) {
        this.consumible = consumible;
    }
}