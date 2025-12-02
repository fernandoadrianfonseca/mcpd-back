package com.mcpd.dto;

public class StockProductoDto {

    private Integer productoId;
    private String productoNombre;
    private String categoriaNombre;
    private Long total;
    private Long totalCustodia;
    private Long totalDisponible;

    public StockProductoDto(Integer productoId, String productoNombre, String categoriaNombre,
                            Long total, Long totalCustodia, Long totalDisponible) {
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.categoriaNombre = categoriaNombre;
        this.total = total;
        this.totalCustodia = totalCustodia;
        this.totalDisponible = totalDisponible;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalCustodia() {
        return totalCustodia;
    }

    public void setTotalCustodia(Long totalCustodia) {
        this.totalCustodia = totalCustodia;
    }

    public Long getTotalDisponible() {
        return totalDisponible;
    }

    public void setTotalDisponible(Long totalDisponible) {
        this.totalDisponible = totalDisponible;
    }
}