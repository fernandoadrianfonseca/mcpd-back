package com.mcpd.dto;

import java.util.Date;

public class PedidoDeAdquisicionDto {

    private Long numero;
    private Date fechaSolicitud;
    private String nombreSolicitante;
    private int prioridad;
    private double presupuesto;
    private String secretaria;
    private String direccion;
    private String observacion;
    private String administracion;
    private boolean hacienda;
    private boolean archivado;
    private boolean despacho;
    private boolean presupuestado;
    private String numeroInstrumentoAdquisicion;
    private String destino;
    private boolean completo;
    private boolean ofertado;
    private String pase;
    private boolean obra;
    private boolean directa;
    private String nota;
    private boolean presentaPre;
    private String presentes;
    private boolean pañol;
    private String motivoRechazo;
    private String imputacion;
    private Long legajoSolicitante;
    private String haciendaEmpleado;
    private Long haciendaLegajoEmpleado;
    private String pañolEmpleado;
    private Long pañolLegajoEmpleado;
    private boolean adquisicion;
    private boolean nuevoSistema;
    private Date updated;

    // Getters y Setters ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

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

    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
