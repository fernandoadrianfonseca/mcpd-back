package com.mcpd.dto;

import com.mcpd.model.ProductosStock;

/**
 * DTO de proyección utilizado para representar el estado actual
 * de un {@link ProductosStock} junto con la cantidad en custodia
 * correspondiente a un empleado específico.
 *
 * <p>
 * Se utiliza en consultas que determinan el último movimiento
 * de custodia (alta/baja) y devuelven el saldo actual
 * de un producto en custodia.
 *
 * <h3>Campos</h3>
 * <ul>
 *   <li><b>stock</b>: Ítem de inventario consolidado.</li>
 *   <li><b>cantidadCustodia</b>: Saldo actual en custodia para el empleado consultado.</li>
 * </ul>
 *
 * Este DTO no es una entidad persistente, sino una proyección
 * utilizada para optimizar consultas sin cargar todo el historial de flujos.
 */

public record StockConCustodiaDto(ProductosStock stock, Long cantidadCustodia) {}
