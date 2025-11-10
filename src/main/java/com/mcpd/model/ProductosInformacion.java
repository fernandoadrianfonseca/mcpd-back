package com.mcpd.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "productos_informacion")
public class ProductosInformacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto_flujo")
    private ProductosStockFlujo productoFlujo;

    @Column(nullable = true, length = 255)
    private String codigo;

    @Column(nullable = true, length = 255)
    private Integer codigoProducto;

    @Column(nullable = true, length = 255)
    private String codigoGeneral;

    @Column(nullable = true, length = 255)
    private String codigoAntiguo;

    @Column(name = "numero_de_serie", nullable = true)
    private String numeroDeSerie;

    @Column(name = "observaciones", nullable = true)
    private String observaciones;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoGeneral() {
        return codigoGeneral;
    }

    public void setCodigoGeneral(String codigoGeneral) {
        this.codigoGeneral = codigoGeneral;
    }

    public String getCodigoAntiguo() {
        return codigoAntiguo;
    }

    public void setCodigoAntiguo(String codigoAntiguo) {
        this.codigoAntiguo = codigoAntiguo;
    }

    public String getNumeroDeSerie() { return numeroDeSerie; }
    public void setNumeroDeSerie(String numeroDeSerie) { this.numeroDeSerie = numeroDeSerie; }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

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
