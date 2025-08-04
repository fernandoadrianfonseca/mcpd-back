package com.mcpd.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comprasadquisicionpedido")
public class PedidoDeAdquisicion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private Long numero;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechasolicitud", nullable = false)
    private Date fechaSolicitud;

    @Column(name = "nombresolicitante", nullable = false, length = 50)
    private String nombreSolicitante;

    @Column(name = "prioridad", nullable = false)
    private int prioridad = 0;

    @Column(name = "presupuesto", nullable = false)
    private double presupuesto = 0.0;

    @Column(name = "secretaria", nullable = false, length = 255)
    private String secretaria;

    @Column(name = "direccion", nullable = false, length = 255)
    private String direccion;

    @Column(name = "observacion", columnDefinition = "nvarchar(max) default 'S/N'")
    private String observacion;

    @Column(name = "administracion", nullable = false, length = 255)
    private String administracion;

    @Column(name = "hacienda", nullable = false)
    private boolean hacienda = false;

    @Column(name = "archivado", nullable = false)
    private boolean archivado = false;

    @Column(name = "despacho", nullable = false)
    private boolean despacho = false;

    @Column(name = "presupuestado", nullable = false)
    private boolean presupuestado = false;

    @Column(name = "numeroinstrumentoadquisicion", length = 50)
    private String numeroInstrumentoAdquisicion;

    @Column(name = "destino", columnDefinition = "varchar(max)")
    private String destino;

    @Column(name = "completo", nullable = false)
    private boolean completo = false;

    @Column(name = "ofertado", nullable = false)
    private boolean ofertado = false;

    @Column(name = "pase", nullable = false, columnDefinition = "varchar(max)")
    private String pase;

    @Column(name = "obra", nullable = false)
    private boolean obra = false;

    @Column(name = "directa", nullable = false)
    private boolean directa = false;

    @Column(name = "nota", nullable = false, columnDefinition = "text default 'NA'")
    private String nota;

    @Column(name = "presentapre", nullable = false)
    private boolean presentaPre = false;

    @Column(name = "presentes", columnDefinition = "text")
    private String presentes;

    @Column(name = "pañol", nullable = false)
    private boolean pañol = false;

    @Column(name = "motivorechazo", length = 50)
    private String motivoRechazo;

    @Column(name = "imputacion", length = 50)
    private String imputacion;

    @Column(name = "legajosolicitante", nullable = false)
    private Long legajoSolicitante = 0L;

    @Column(name = "haciendaempleado", length = 100)
    private String haciendaEmpleado;

    @Column(name = "haciendalegajoempleado")
    private Long haciendaLegajoEmpleado;

    @Column(name = "pañolempleado", length = 100)
    private String pañolEmpleado;

    @Column(name = "pañollegajoempleado")
    private Long pañolLegajoEmpleado;

    @Column(name = "adquisicion", nullable = false)
    private boolean adquisicion = true;

    @Column(name = "nuevosistema", nullable = false)
    private boolean nuevoSistema = false;

    @Column(name = "entregado", nullable = false)
    private boolean entregado = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = true)
    private Date updated;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PedidoDeAdquisicionDetalle> detalles;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    // Getters y Setters ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

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

    public Long getLegajoSolicitante() {
        return legajoSolicitante;
    }

    public void setLegajoSolicitante(Long legajoSolicitante) {
        this.legajoSolicitante = legajoSolicitante;
    }

    public String getHaciendaEmpleado() {
        return haciendaEmpleado;
    }

    public void setHaciendaEmpleado(String haciendaEmpleado) {
        this.haciendaEmpleado = haciendaEmpleado;
    }

    public Long getHaciendaLegajoEmpleado() {
        return haciendaLegajoEmpleado;
    }

    public void setHaciendaLegajoEmpleado(Long haciendaLegajoEmpleado) {
        this.haciendaLegajoEmpleado = haciendaLegajoEmpleado;
    }

    public String getPañolEmpleado() {
        return pañolEmpleado;
    }

    public void setPañolEmpleado(String pañolEmpleado) {
        this.pañolEmpleado = pañolEmpleado;
    }

    public Long getPañolLegajoEmpleado() {
        return pañolLegajoEmpleado;
    }

    public void setPañolLegajoEmpleado(Long pañolLegajoEmpleado) {
        this.pañolLegajoEmpleado = pañolLegajoEmpleado;
    }

    public boolean isAdquisicion() {
        return adquisicion;
    }

    public void setAdquisicion(boolean adquisicion) {
        this.adquisicion = adquisicion;
    }

    public boolean isNuevoSistema() {
        return nuevoSistema;
    }

    public void setNuevoSistema(boolean nuevoSistema) {
        this.nuevoSistema = nuevoSistema;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<PedidoDeAdquisicionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PedidoDeAdquisicionDetalle> detalles) {
        this.detalles = detalles;
    }
}
