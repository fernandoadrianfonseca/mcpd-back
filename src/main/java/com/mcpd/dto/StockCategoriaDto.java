package com.mcpd.dto;

public class StockCategoriaDto {
    private Integer categoriaId;
    private String categoriaNombre;
    private Long total;
    private Long totalCustodia;
    private Long totalDisponible;

    public StockCategoriaDto(Integer categoriaId, String categoriaNombre,
                             Long total, Long totalCustodia, Long totalDisponible) {
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.total = total;
        this.totalCustodia = totalCustodia;
        this.totalDisponible = totalDisponible;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
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