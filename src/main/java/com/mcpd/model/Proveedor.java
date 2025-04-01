package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mcpdproveedor")
public class Proveedor implements Serializable {

    @Id
    @Column(name = "cuit", nullable = false)
    private Long cuit;

    @Column(name = "nombrefantasia", nullable = false, length = 150)
    private String nombreFantasia;

    @Column(name = "empleador", nullable = false)
    private Boolean empleador = false;

    @Column(name = "iibb", length = 50)
    private String iibb;

    @Column(name = "cbu", columnDefinition = "VARCHAR(MAX)")
    private String cbu;

    @Column(name = "saldo", nullable = false)
    private Double saldo = 0.0;

    @Column(name = "multilateral", nullable = false)
    private Boolean multilateral = false;

    @ManyToOne
    @JoinColumn(name = "cuit", referencedColumnName = "Cuit", insertable = false, updatable = false)
    private Contribuyente contribuyente;

    @Transient
    private String nombre;

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public Boolean getEmpleador() {
        return empleador;
    }

    public void setEmpleador(Boolean empleador) {
        this.empleador = empleador;
    }

    public String getIibb() {
        return iibb;
    }

    public void setIibb(String iibb) {
        this.iibb = iibb;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Boolean getMultilateral() {
        return multilateral;
    }

    public void setMultilateral(Boolean multilateral) {
        this.multilateral = multilateral;
    }

    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Contribuyente contribuyente) {
        this.contribuyente = contribuyente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
