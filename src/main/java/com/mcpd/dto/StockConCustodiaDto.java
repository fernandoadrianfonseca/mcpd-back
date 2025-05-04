package com.mcpd.dto;

import com.mcpd.model.ProductosStock;

public record StockConCustodiaDto(ProductosStock stock, Long cantidadCustodia) {}
