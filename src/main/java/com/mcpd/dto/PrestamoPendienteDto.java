package com.mcpd.dto;

import com.mcpd.model.ProductosStockFlujo;
import java.util.Date;

public class PrestamoPendienteDto {

    private Integer id;
    private ProductosStockFlujo flujo;
    private Long cantidadPendiente;
    private String fechaDevolucion;
    private String estadoDevolucion;

    public PrestamoPendienteDto(Integer id, ProductosStockFlujo flujo, Long cantidadPendiente, String fechaDevolucion, String estadoDevolucion) {
        this.id = id;
        this.flujo = flujo;
        this.cantidadPendiente = cantidadPendiente;
        this.fechaDevolucion = fechaDevolucion;
        this.estadoDevolucion = estadoDevolucion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductosStockFlujo getFlujo() {
        return flujo;
    }

    public void setFlujo(ProductosStockFlujo flujo) {
        this.flujo = flujo;
    }

    public Long getCantidadPendiente() {
        return cantidadPendiente;
    }

    public void setCantidadPendiente(Long cantidadPendiente) {
        this.cantidadPendiente = cantidadPendiente;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstadoDevolucion() {
        return estadoDevolucion;
    }

    public void setEstadoDevolucion(String estadoDevolucion) {
        this.estadoDevolucion = estadoDevolucion;
    }
}