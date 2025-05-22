package com.mcpd.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "productos_numeros_de_serie")
public class ProductosNumeroDeSerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto_flujo")
    private ProductosStockFlujo productoFlujo;

    @Column(name = "numero_de_serie", nullable = false)
    private String numeroDeSerie;

    @ManyToOne
    @JoinColumn(name = "empleado_custodia")
    private Empleado empleadoCustodia;

    @Column(nullable = false)
    private Boolean activo = true;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ProductosStockFlujo getProductoFlujo() { return productoFlujo; }
    public void setProductoFlujo(ProductosStockFlujo productoFlujo) { this.productoFlujo = productoFlujo; }

    public String getNumeroDeSerie() { return numeroDeSerie; }
    public void setNumeroDeSerie(String numeroDeSerie) { this.numeroDeSerie = numeroDeSerie; }

    public Empleado getEmpleadoCustodia() {
        return empleadoCustodia;
    }

    public void setEmpleadoCustodia(Empleado empleadoCustodia) {
        this.empleadoCustodia = empleadoCustodia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
