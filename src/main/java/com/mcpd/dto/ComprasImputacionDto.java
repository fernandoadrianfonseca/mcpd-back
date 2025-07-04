package com.mcpd.dto;

public class ComprasImputacionDto {
    private String imputacion;
    private String codigo;
    private String dependencia;

    // Constructors
    public ComprasImputacionDto() {}

    public ComprasImputacionDto(String imputacion, String codigo, String dependencia) {
        this.imputacion = imputacion;
        this.codigo = codigo;
        this.dependencia = dependencia;
    }

    // Getters and Setters
    public String getImputacion() {
        return imputacion;
    }

    public void setImputacion(String imputacion) {
        this.imputacion = imputacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }
}
