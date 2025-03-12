package com.mcpd.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria", nullable = false)
    private ProductosCategoria categoria;

    @Column(nullable = false)
    private String categoriaNombre;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fechaDeCarga;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getFechaDeCarga() {
        return fechaDeCarga;
    }

    public Date getUpdated() {
        return updated;
    }
}
