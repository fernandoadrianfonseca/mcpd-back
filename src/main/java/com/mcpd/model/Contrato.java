package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comprasContrato")
public class Contrato implements Serializable {
    @Id
    @Column(length = 254, nullable = false)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fin;

    @ManyToOne
    @JoinColumn(name = "proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(columnDefinition = "nvarchar(max)", nullable = false)
    private String observacion;

    @Column(length = 50, nullable = false)
    private String resolucion;

    @Column(length = 50, nullable = false)
    private String imputacion;

    @Column(length = 50, nullable = false)
    private String administracion;

    @Column(columnDefinition = "nvarchar(max)", nullable = false)
    private String secretaria;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(nullable = false)
    private double monto = 0;

    @Column(nullable = false)
    private boolean alerta = false;

    @Column(length = 250)
    private String razon;

    @Column(length = 250)
    private String fantasia;

    @Column(columnDefinition = "varchar(max)", nullable = false)
    private String resoDesafectacion = "0";

    @Column(nullable = false)
    private double montoDesafecta = 0;

    @Column(columnDefinition = "varchar(max)")
    private String memoDesafecta;

    @Transient
    private double mensual;

    @PostLoad
    private void calcularMensual() {
        int dias = (int) ((fin.getTime() - inicio.getTime()) / (1000 * 60 * 60 * 24));
        mensual = Math.round(monto / ((dias / 29.0) == 0 ? 1 : (dias / 29.0)) * 100.0) / 100.0;
    }

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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getImputacion() {
        return imputacion;
    }

    public void setImputacion(String imputacion) {
        this.imputacion = imputacion;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isAlerta() {
        return alerta;
    }

    public void setAlerta(boolean alerta) {
        this.alerta = alerta;
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

    public String getResoDesafectacion() {
        return resoDesafectacion;
    }

    public void setResoDesafectacion(String resoDesafectacion) {
        this.resoDesafectacion = resoDesafectacion;
    }

    public double getMontoDesafecta() {
        return montoDesafecta;
    }

    public void setMontoDesafecta(double montoDesafecta) {
        this.montoDesafecta = montoDesafecta;
    }

    public String getMemoDesafecta() {
        return memoDesafecta;
    }

    public void setMemoDesafecta(String memoDesafecta) {
        this.memoDesafecta = memoDesafecta;
    }

    public double getMensual() {
        return mensual;
    }

    public void setMensual(double mensual) {
        this.mensual = mensual;
    }
}

