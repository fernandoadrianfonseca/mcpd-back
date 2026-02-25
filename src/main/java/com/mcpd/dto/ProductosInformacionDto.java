package com.mcpd.dto;

/**
 * DTO que representa una unidad individual de un producto en stock.
 *
 * <p>
 * Modela la información detallada de cada unidad física asociada a un
 * {@code ProductosStock}, incluyendo:
 * </p>
 *
 * <ul>
 *   <li>Códigos identificatorios internos</li>
 *   <li>Número de serie (si aplica)</li>
 *   <li>Observaciones</li>
 *   <li>Empleado en custodia</li>
 * </ul>
 *
 * <h3>Estructura de Códigos</h3>
 *
 * <ul>
 *   <li><b>codigo</b>: idProductoStock + "-" + número incremental de la unidad
 *       (ejemplo: 5-1)</li>
 *   <li><b>codigoProducto</b>: número incremental dentro del producto
 *       (ejemplo: 1)</li>
 *   <li><b>codigoGeneral</b>: idProductoStock + "-" + id único del registro</li>
 * </ul>
 *
 * <p>
 * Este DTO se utiliza principalmente en:
 * </p>
 * <ul>
 *   <li>{@code ProductosInformacionController}</li>
 *   <li>{@code ProductosInformacionService}</li>
 * </ul>
 *
 * <p>
 * No representa directamente la entidad JPA, sino una vista simplificada
 * y segura para exponer en la API.
 * </p>
 */
public class ProductosInformacionDto {

    /** Identificador único de la unidad individual. */
    private Integer id;

    /**
     * Código compuesto del producto.
     *
     * Formato:
     * idProductoStock + "-" + número incremental
     *
     * Ejemplo:
     * 5-1 (Primera unidad del producto con id 5)
     */
    private String codigo;

    /**
     * Número incremental de la unidad dentro del producto.
     *
     * Representa la unidad física específica
     * (ej: 1, 2, 3...).
     */
    private String codigoProducto;

    /**
     * Código general único del sistema.
     *
     * Formato:
     * idProductoStock + "-" + id único del registro
     *
     * Se utiliza para identificación global.
     */
    private String codigoGeneral;

    /**
     * Código heredado del sistema anterior (si existe).
     *
     * Puede utilizarse para compatibilidad histórica
     * o migración de datos.
     */
    private String codigoAntiguo;

    /**
     * Número de serie físico del producto.
     *
     * Solo aplica cuando el producto requiere
     * trazabilidad individual.
     */
    private String numeroDeSerie;

    /**
     * Observaciones asociadas a la unidad individual.
     *
     * Puede incluir:
     * - Estado físico
     * - Notas administrativas
     * - Información adicional relevante
     */
    private String observaciones;

    /**
     * Legajo del empleado que tiene actualmente la unidad en custodia.
     *
     * Si es null:
     * - La unidad se encuentra en pañol o sin asignar.
     */
    private Long empleadoCustodia;

    // Getters y Setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoGeneral() {
        return codigoGeneral;
    }

    public void setCodigoGeneral(String codigoGeneral) {
        this.codigoGeneral = codigoGeneral;
    }

    public String getCodigoAntiguo() {
        return codigoAntiguo;
    }

    public void setCodigoAntiguo(String codigoAntiguo) {
        this.codigoAntiguo = codigoAntiguo;
    }

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }
    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getEmpleadoCustodia() {
        return empleadoCustodia;
    }
    public void setEmpleadoCustodia(Long empleadoCustodia) {
        this.empleadoCustodia = empleadoCustodia;
    }
}
