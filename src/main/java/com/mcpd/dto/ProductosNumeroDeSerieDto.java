package com.mcpd.dto;

public class ProductosNumeroDeSerieDto {
    private Integer id;
    private String numeroDeSerie;
    private Long empleadoCustodia;

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }
    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }
    public Long getEmpleadoCustodia() {
        return empleadoCustodia;
    }
    public void setEmpleadoCustodia(Long empleadoCustodia) {
        this.empleadoCustodia = empleadoCustodia;
    }
}
