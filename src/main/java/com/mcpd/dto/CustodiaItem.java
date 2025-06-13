package com.mcpd.dto;

import java.util.Date;

public class CustodiaItem {
    private Integer stockId;
    private Long cantidad;
    private String observaciones;
    private Date fechaDevolucion;

    // getters y setters
    public Integer getStockId() { return stockId; }
    public void setStockId(Integer stockId) { this.stockId = stockId; }

    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
