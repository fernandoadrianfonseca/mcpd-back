package com.mcpd.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comprasadquisicionpresupuesto")
public class PresupuestoDeAdquisicion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "cuit", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private Long pedido;

    @Column(nullable = false, length = 50)
    private String numeroproveedor = "pendiente";

    @Column(nullable = false, columnDefinition = "varchar(max) default '7'")
    private String validez;

    @Column(nullable = false, columnDefinition = "varchar(max) default '3-5 dias'")
    private String plazo;

    @Column(nullable = false, columnDefinition = "varchar(max) default '35/40 dias'")
    private String formapago;

    @Column(nullable = false, columnDefinition = "text default 'S/N'")
    private String observaciones;

    @Column(nullable = false, columnDefinition = "text default 'S/O'")
    private String observaadjudicacion;

    @Column(length = 250)
    private String razon;

    @Column(length = 250)
    private String fantasia;

    @Column(nullable = false)
    private Double total = 0.0;

    @Column(name = "nuevosistema", nullable = false)
    private boolean nuevoSistema = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = true)
    private Date updated;

    @OneToMany(mappedBy = "comprasadquisicionpresupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PresupuestoDeAdquisicionDetalle> detalles;

    @PrePersist
    protected void onCreate() {
        fecha = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
    }

    public String getNumeroProveedor() {
        return numeroproveedor;
    }

    public void setNumeroProveedor(String numeroProveedor) {
        this.numeroproveedor = numeroProveedor;
    }

    public String getValidez() {
        return validez;
    }

    public void setValidez(String validez) {
        this.validez = validez;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getFormaPago() {
        return formapago;
    }

    public void setFormaPago(String formaPago) {
        this.formapago = formaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaAdjudicacion() {
        return observaadjudicacion;
    }

    public void setObservaAdjudicacion(String observaAdjudicacion) {
        this.observaadjudicacion = observaAdjudicacion;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean isNuevoSistema() {
        return nuevoSistema;
    }

    public void setNuevoSistema(boolean nuevoSistema) {
        this.nuevoSistema = nuevoSistema;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<PresupuestoDeAdquisicionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PresupuestoDeAdquisicionDetalle> detalles) {
        this.detalles = detalles;
    }
}