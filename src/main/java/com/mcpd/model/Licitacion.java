package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comprasLicitacion")
public class Licitacion implements Serializable {
    @Id
    @Column(length = 254, nullable = false)
    private String id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Column(length = 50, nullable = false)
    private String resolucion = "0";

    @Column(nullable = false)
    private boolean privada = false;

    @Column(length = 50, nullable = false)
    private String resolucionAdjudicacion = "0";

    @Column(length = 50, nullable = false)
    private String resolucionDesierto = "0";

    @Column(nullable = false)
    private boolean directa = false;

    @Column(nullable = false)
    private boolean archiva = false;

    @Column(length = 50)
    private String nroActaApertura;

    @Column(length = 50)
    private String nroActaAdjudicacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAdjudicacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public boolean isPrivada() {
        return privada;
    }

    public void setPrivada(boolean privada) {
        this.privada = privada;
    }

    public String getResolucionAdjudicacion() {
        return resolucionAdjudicacion;
    }

    public void setResolucionAdjudicacion(String resolucionAdjudicacion) {
        this.resolucionAdjudicacion = resolucionAdjudicacion;
    }

    public String getResolucionDesierto() {
        return resolucionDesierto;
    }

    public void setResolucionDesierto(String resolucionDesierto) {
        this.resolucionDesierto = resolucionDesierto;
    }

    public boolean isDirecta() {
        return directa;
    }

    public void setDirecta(boolean directa) {
        this.directa = directa;
    }

    public boolean isArchiva() {
        return archiva;
    }

    public void setArchiva(boolean archiva) {
        this.archiva = archiva;
    }

    public String getNroActaApertura() {
        return nroActaApertura;
    }

    public void setNroActaApertura(String nroActaApertura) {
        this.nroActaApertura = nroActaApertura;
    }

    public String getNroActaAdjudicacion() {
        return nroActaAdjudicacion;
    }

    public void setNroActaAdjudicacion(String nroActaAdjudicacion) {
        this.nroActaAdjudicacion = nroActaAdjudicacion;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaAdjudicacion() {
        return fechaAdjudicacion;
    }

    public void setFechaAdjudicacion(Date fechaAdjudicacion) {
        this.fechaAdjudicacion = fechaAdjudicacion;
    }
}
