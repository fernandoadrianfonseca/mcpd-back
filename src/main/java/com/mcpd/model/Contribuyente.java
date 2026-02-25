package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa un contribuyente registrado en el sistema.
 *
 * Contribuyente (base fiscal)
 *         ↓
 * Proveedor (rol comercial)
 *         ↓
 * Compras / Pagos / Movimientos
 *
 * <p>
 * Un contribuyente es una persona física o jurídica identificada por su CUIT,
 * que puede participar en distintas operaciones del sistema (por ejemplo:
 * como proveedor, contratista u otro rol fiscal).
 * </p>
 *
 * <h3>Características</h3>
 * <ul>
 *   <li>Identificado únicamente por CUIT (clave primaria).</li>
 *   <li>Contiene información fiscal, personal y de domicilio.</li>
 *   <li>Sirve como entidad base para {@link Proveedor}.</li>
 * </ul>
 *
 * <p>
 * No contiene lógica de negocio; actúa como entidad maestra de datos fiscales.
 * </p>
 */
@Entity
@Table(name = "mcpdcontribuyente")
public class Contribuyente implements Serializable {

    /** CUIT del contribuyente (clave primaria). */
    @Id
    @Column(name = "Cuit", nullable = false)
    private Long cuit;

    /** Nombre completo o razón social del contribuyente. */
    @Column(name = "Nombre", nullable = false)
    private String nombre = "";

    /** Domicilio completo en formato libre. */
    @Column(name = "Domicilio")
    private String domicilio;

    /** País de residencia. Por defecto: Argentina. */
    @Column(name = "Pais", nullable = false)
    private String pais = "Argentina";

    /** Provincia de residencia. */
    @Column(name = "Provincia", nullable = false)
    private String provincia = "Santa Cruz";

    /** Ciudad de residencia. */
    @Column(name = "Ciudad", nullable = false)
    private String ciudad = "Puerto Deseado";

    /** Código postal del domicilio. */
    @Column(name = "Codigo_Postal")
    private String codigoPostal = "9050";

    /** Teléfono de contacto. */
    @Column(name = "Telefono")
    private String telefono;

    /** Correo electrónico de contacto. */
    @Column(name = "Mail")
    private String mail;

    /**
     * Código de responsabilidad fiscal.
     *
     * Generalmente representa condición frente a IVA.
     * Ejemplo: C = Consumidor Final, R = Responsable Inscripto.
     */
    @Column(name = "Responsabilidad", nullable = false, length = 1)
    private String responsabilidad = "C";

    /**
     * Código de responsabilidad fiscal.
     *
     * Generalmente representa condición frente a IVA.
     * Ejemplo: C = Consumidor Final, R = Responsable Inscripto.
     */
    @Column(name = "Estado_Civil")
    private String estadoCivil;

    /**
     * CUIT del cónyuge en caso de estar registrado.
     * Puede utilizarse para cruces fiscales.
     */
    @Column(name = "Conyuge")
    private Long conyuge;

    /**
     * Sexo del contribuyente.
     * Generalmente: M (Masculino), F (Femenino).
     */
    @Column(name = "Sexo", nullable = false, length = 1)
    private String sexo = "M";

    /** Fecha de nacimiento. */
    @Column(name = "Nacimiento")
    @Temporal(TemporalType.DATE)
    private Date nacimiento = new Date();

    /** Nacionalidad declarada. */
    @Column(name = "Nacionalidad")
    private String nacionalidad;

    /** Calle del domicilio. */
    @Column(name = "Calle", length = 500)
    private String calle;

    /** Altura del domicilio. */
    @Column(name = "Altura")
    private String altura;

    /** Piso del domicilio (si corresponde). */
    @Column(name = "Piso")
    private String piso;

    /** Departamento o unidad funcional. */
    @Column(name = "Departamento")
    private String departamento;

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getResponsabilidad() {
        return responsabilidad;
    }

    public void setResponsabilidad(String responsabilidad) {
        this.responsabilidad = responsabilidad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Long getConyuge() {
        return conyuge;
    }

    public void setConyuge(Long conyuge) {
        this.conyuge = conyuge;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
