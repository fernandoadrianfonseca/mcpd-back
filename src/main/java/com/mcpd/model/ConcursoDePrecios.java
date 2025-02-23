package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comprasConcursoPrecios")
public class ConcursoDePrecios implements Serializable {
    @Id
    @Column(name = "id", length = 254, nullable = false)
    private String id;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "resolucion", length = 50, nullable = false)
    private String resolucion;

    @Column(name = "resolucionAdjudicacion", length = 50, nullable = false)
    private String resolucionAdjudicacion;

    @Column(name = "resolucionDesierto", length = 50, nullable = false)
    private String resolucionDesierto;

    @Column(name = "directa", nullable = false)
    private boolean directa = false;

    @Column(name = "archiva", nullable = false)
    private boolean archiva = false;

    @Column(name = "nroActaApertura", length = 50)
    private String nroActaApertura;

    @Column(name = "nroActaAdjudicacion", length = 50)
    private String nroActaAdjudicacion;

    @Column(name = "fechaApertura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;

    @Column(name = "fechaAdjudicacion")
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

