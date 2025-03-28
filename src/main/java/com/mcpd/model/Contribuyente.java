package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mcpdcontribuyente")
public class Contribuyente implements Serializable {

    @Id
    @Column(name = "Cuit", nullable = false)
    private Long cuit;

    @Column(name = "Nombre", nullable = false)
    private String nombre = "";

    @Column(name = "Domicilio")
    private String domicilio;

    @Column(name = "Pais", nullable = false)
    private String pais = "Argentina";

    @Column(name = "Provincia", nullable = false)
    private String provincia = "Santa Cruz";

    @Column(name = "Ciudad", nullable = false)
    private String ciudad = "Puerto Deseado";

    @Column(name = "Codigo_Postal")
    private String codigoPostal = "9050";

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Mail")
    private String mail;

    @Column(name = "Responsabilidad", nullable = false, length = 1)
    private String responsabilidad = "C";

    @Column(name = "Estado_Civil")
    private String estadoCivil;

    @Column(name = "Conyuge")
    private Long conyuge;

    @Column(name = "Sexo", nullable = false, length = 1)
    private String sexo = "M";

    @Column(name = "Nacimiento")
    @Temporal(TemporalType.DATE)
    private Date nacimiento = new Date();

    @Column(name = "Nacionalidad")
    private String nacionalidad;

    @Column(name = "Calle", length = 500)
    private String calle;

    @Column(name = "Altura")
    private String altura;

    @Column(name = "Piso")
    private String piso;

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
