package com.mcpd.exception;

/**
 * Excepción de negocio lanzada cuando la cantidad solicitada
 * de un producto supera el stock disponible.
 *
 * <p>
 * Se utiliza principalmente en el módulo de stock
 * (ProductosStockService) durante operaciones como:
 * </p>
 *
 * <ul>
 *   <li>Asignación de custodia</li>
 *   <li>Transferencia</li>
 *   <li>Baja de stock</li>
 * </ul>
 *
 * <p>
 * Esta excepción es interceptada por
 * {@link GlobalExceptionHandler} y transformada
 * en una respuesta HTTP 400.
 * </p>
 */
public class StockInsuficienteException extends RuntimeException {

    /**
     * Crea una excepción indicando que el stock es insuficiente.
     *
     * @param stockId identificador del producto.
     * @param cantidadDisponible cantidad actual disponible.
     * @param cantidadSolicitada cantidad requerida para la operación.
     */
    public StockInsuficienteException(Integer stockId, int cantidadDisponible, int cantidadSolicitada) {
        super("Stock insuficiente para el producto ID: " + stockId +
                ". Disponible: " + cantidadDisponible + ", solicitado: " + cantidadSolicitada);
    }
}