package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comprasadquisicionordencompra")
public class OrdenDeCompraAdquisicion implements Serializable {

    @Id
    @Column(name = "numero", length = 50, nullable = false)
    private String numero;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "instrumento", length = 255)
    private String instrumento;

    @Column(name = "resolucion", length = 255, nullable = false)
    private String resolucion;

    @Column(name = "remito", length = 255, nullable = false)
    private String remito;

    @Column(name = "factura", length = 255, nullable = false)
    private String factura;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;

    @Column(name = "partida", length = 50)
    private String partida;

    @Column(name = "[plan]", length = 50)
    private String plan;

    @Column(name = "razon", length = 250)
    private String razon;

    @Column(name = "fantasia", length = 250)
    private String fantasia;

    @Column(name = "cargaremito", columnDefinition = "TEXT")
    private String cargaRemito;

    @Column(name = "plazo", columnDefinition = "TEXT")
    private String plazo;

    @Column(name = "validez", columnDefinition = "TEXT")
    private String validez;

    @Column(name = "formapago", columnDefinition = "TEXT")
    private String formaPago;

    @Column(name = "observacionesproveedor", columnDefinition = "TEXT")
    private String observacionesProveedor;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenDeCompraAdquisicionDetalle> detalles;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
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

    public String getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getRemito() {
        return remito;
    }

    public void setRemito(String remito) {
        this.remito = remito;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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

    public String getCargaRemito() {
        return cargaRemito;
    }

    public void setCargaRemito(String cargaRemito) {
        this.cargaRemito = cargaRemito;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getValidez() {
        return validez;
    }

    public void setValidez(String validez) {
        this.validez = validez;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getObservacionesProveedor() {
        return observacionesProveedor;
    }

    public void setObservacionesProveedor(String observacionesProveedor) {
        this.observacionesProveedor = observacionesProveedor;
    }

    public List<OrdenDeCompraAdquisicionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenDeCompraAdquisicionDetalle> detalles) {
        this.detalles = detalles;
    }
}