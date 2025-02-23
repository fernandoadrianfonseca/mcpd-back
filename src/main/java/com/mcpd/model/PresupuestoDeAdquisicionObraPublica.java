package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comprasAdquisicionObrasPublicasPresupuesto")

public class PresupuestoDeAdquisicionObraPublica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor", referencedColumnName = "cuit", nullable = false)
    private Proveedor proveedor;

    @Column(nullable = false)
    private Long pedido;

    @Column(nullable = false, length = 50)
    private String numeroProveedor = "pendiente";

    @Column(length = 250)
    private String razon;

    @Column(length = 250)
    private String fantasia;

    @Column(nullable = false)
    private double total = 0;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String validez = "7";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String plazo = "3-5 dias";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String formaPago = "35/40 dias";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String observaciones = "S/N";

    @Column(nullable = false, columnDefinition = "TEXT")
    private String observaAdjudicacion = "S/O";

    @OneToMany(mappedBy = "comprasAdquisicionPresupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PresupuestoDeAdquisicionObraPublicaDetalle> detalles;

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
        return numeroProveedor;
    }

    public void setNumeroProveedor(String numeroProveedor) {
        this.numeroProveedor = numeroProveedor;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaAdjudicacion() {
        return observaAdjudicacion;
    }

    public void setObservaAdjudicacion(String observaAdjudicacion) {
        this.observaAdjudicacion = observaAdjudicacion;
    }

    public List<PresupuestoDeAdquisicionObraPublicaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PresupuestoDeAdquisicionObraPublicaDetalle> detalles) {
        this.detalles = detalles;
    }
}
