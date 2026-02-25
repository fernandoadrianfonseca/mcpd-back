package com.mcpd.model;

/**
 * Entidad que representa un proveedor dentro del sistema MCPD.
 *
 * <p>
 * Un proveedor es una entidad comercial identificada por su CUIT,
 * que puede suministrar bienes o servicios al organismo.
 * </p>
 *
 * <h3>Características</h3>
 * <ul>
 *   <li>Su identificador primario es el CUIT.</li>
 *   <li>Puede estar vinculado a un {@link Contribuyente} para obtener
 *       información fiscal adicional.</li>
 *   <li>Mantiene un saldo contable asociado.</li>
 * </ul>
 *
 * <p>
 * El campo {@code nombre} es transitorio y se utiliza únicamente
 * para exponer el nombre del contribuyente en respuestas API.
 * </p>
 */

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mcpdproveedor")
public class Proveedor implements Serializable {

    /** CUIT del proveedor (clave primaria). */
    @Id
    @Column(name = "cuit", nullable = false)
    private Long cuit;

    /** Nombre de fantasía o razón comercial visible del proveedor. */
    @Column(name = "nombrefantasia", nullable = false, length = 150)
    private String nombreFantasia;

    /**
     * Indica si el proveedor es empleador.
     * Puede utilizarse para lógica vinculada a retenciones o cargas sociales.
     */
    @Column(name = "empleador", nullable = false)
    private Boolean empleador = false;

    /** Número de inscripción en Ingresos Brutos. */
    @Column(name = "iibb", length = 50)
    private String iibb;

    /** Clave Bancaria Uniforme utilizada para pagos electrónicos. */
    @Column(name = "cbu", columnDefinition = "VARCHAR(MAX)")
    private String cbu;

    /**
     * Saldo contable actual del proveedor.
     * Representa deuda o crédito acumulado.
     */
    @Column(name = "saldo", nullable = false)
    private Double saldo = 0.0;

    /**
     * Indica si el proveedor tributa bajo régimen multilateral.
     */
    @Column(name = "multilateral", nullable = false)
    private Boolean multilateral = false;

    /**
     * Relación con la entidad {@link Contribuyente}.
     *
     * Se vincula por CUIT pero no es insertable ni actualizable desde esta entidad.
     */
    @ManyToOne
    @JoinColumn(name = "cuit", referencedColumnName = "Cuit", insertable = false, updatable = false)
    private Contribuyente contribuyente;

    /**
     * Nombre del contribuyente asociado.
     *
     * Campo transitorio utilizado para respuestas enriquecidas en la API.
     */
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
