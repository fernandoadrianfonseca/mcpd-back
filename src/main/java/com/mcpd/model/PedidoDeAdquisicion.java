package com.mcpd.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comprasAdquisicionPedido")
public class PedidoDeAdquisicion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaSolicitud;

    @Column(nullable = false, length = 50)
    private String nombreSolicitante;

    @Column(nullable = false)
    private int prioridad = 0;

    @Column(nullable = false)
    private double presupuesto = 0.0;

    @Column(nullable = false, length = 255)
    private String secretaria;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(columnDefinition = "nvarchar(max) default 'S/N'")
    private String observacion;

    @Column(nullable = false, length = 255)
    private String administracion;

    @Column(nullable = false)
    private boolean hacienda = false;

    @Column(nullable = false)
    private boolean archivado = false;

    @Column(nullable = false)
    private boolean despacho = false;

    @Column(nullable = false)
    private boolean presupuestado = false;

    @Column(length = 50)
    private String numeroInstrumentoAdquisicion;

    @Column(columnDefinition = "varchar(max)")
    private String destino;

    @Column(nullable = false)
    private boolean completo = false;

    @Column(nullable = false)
    private boolean ofertado = false;

    @Column(nullable = false, columnDefinition = "varchar(max)")
    private String pase;

    @Column(nullable = false)
    private boolean obra = false;

    @Column(nullable = false)
    private boolean directa = false;

    @Column(nullable = false, columnDefinition = "text default 'NA'")
    private String nota;

    @Column(nullable = false)
    private boolean presentaPre = false;

    @Column(columnDefinition = "text")
    private String presentes;

    @Column(nullable = false)
    private boolean pañol = false;

    @Column(length = 50)
    private String motivoRechazo;

    @Column(length = 50)
    private String imputacion;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoDeAdquisicionDetalle> detalles;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public boolean isHacienda() {
        return hacienda;
    }

    public void setHacienda(boolean hacienda) {
        this.hacienda = hacienda;
    }

    public boolean isArchivado() {
        return archivado;
    }

    public void setArchivado(boolean archivado) {
        this.archivado = archivado;
    }

    public boolean isDespacho() {
        return despacho;
    }

    public void setDespacho(boolean despacho) {
        this.despacho = despacho;
    }

    public boolean isPresupuestado() {
        return presupuestado;
    }

    public void setPresupuestado(boolean presupuestado) {
        this.presupuestado = presupuestado;
    }

    public String getNumeroInstrumentoAdquisicion() {
        return numeroInstrumentoAdquisicion;
    }

    public void setNumeroInstrumentoAdquisicion(String numeroInstrumentoAdquisicion) {
        this.numeroInstrumentoAdquisicion = numeroInstrumentoAdquisicion;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public boolean isOfertado() {
        return ofertado;
    }

    public void setOfertado(boolean ofertado) {
        this.ofertado = ofertado;
    }

    public String getPase() {
        return pase;
    }

    public void setPase(String pase) {
        this.pase = pase;
    }

    public boolean isObra() {
        return obra;
    }

    public void setObra(boolean obra) {
        this.obra = obra;
    }

    public boolean isDirecta() {
        return directa;
    }

    public void setDirecta(boolean directa) {
        this.directa = directa;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public boolean isPresentaPre() {
        return presentaPre;
    }

    public void setPresentaPre(boolean presentaPre) {
        this.presentaPre = presentaPre;
    }

    public String getPresentes() {
        return presentes;
    }

    public void setPresentes(String presentes) {
        this.presentes = presentes;
    }

    public boolean isPañol() {
        return pañol;
    }

    public void setPañol(boolean pañol) {
        this.pañol = pañol;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getImputacion() {
        return imputacion;
    }

    public void setImputacion(String imputacion) {
        this.imputacion = imputacion;
    }

    public List<PedidoDeAdquisicionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDeAdquisicionDetalle> detalles) {
        this.detalles = detalles;
    }
}
