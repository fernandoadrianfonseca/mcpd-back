package com.mcpd.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(Integer stockId, int cantidadDisponible, int cantidadSolicitada) {
        super("Stock insuficiente para el producto ID: " + stockId +
                ". Disponible: " + cantidadDisponible + ", solicitado: " + cantidadSolicitada);
    }
}