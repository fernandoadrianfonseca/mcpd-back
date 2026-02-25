package com.mcpd.model;

import jakarta.persistence.*;

import java.util.Date;

/**
 * Representa la información unitaria de bienes inventariables
 * dentro del sistema de stock municipal.
 *
 * <p>
 * A diferencia de {@link ProductosStock}, que representa cantidades consolidadas,
 * esta entidad modela cada unidad física individual (uno por uno).
 *
 * Se utiliza principalmente para:
 * - Bienes de dotación fija
 * - Equipos con número de serie
 * - Elementos patrimoniales
 * - Trazabilidad por unidad
 *
 * <h3>Relación con el sistema</h3>
 * <ul>
 *   <li>Se vincula al movimiento de alta mediante {@link ProductosStockFlujo}.</li>
 *   <li>Permite asignar custodia específica por empleado.</li>
 *   <li>Permite baja lógica mediante el campo {@code activo}.</li>
 * </ul>
 *
 * Esta entidad permite una trazabilidad completa:
 * Desde el ingreso del bien → asignación → reasignación → baja.
 */

@Entity
@Table(name = "productos_informacion")
public class ProductosInformacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Movimiento de flujo (tipo "alta") donde se dio de alta esta unidad.
     *
     * Permite conocer:
     * - Fecha de ingreso
     * - Orden de compra
     * - Remito
     * - Usuario que registró el movimiento
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto_flujo")
    private ProductosStockFlujo productoFlujo;

    /**
     * Código identificatorio compuesto del bien.
     *
     * Formato:
     *   {id_producto_stock}-{numero_unidad}
     *
     * Ejemplo:
     *   Si el producto_stock tiene ID 5 (Abrochadora)
     *   y es la primera unidad cargada,
     *   el código será: 5-1
     *
     * Representa la identificación operativa visible del bien.
     */
    @Column(nullable = true, length = 255)
    private String codigo;

    /**
     * Número incremental de la unidad dentro del mismo producto_stock.
     *
     * Corresponde a la parte numérica final del código.
     *
     * Ejemplo:
     *   ProductoStock ID 5
     *   Primera unidad → codigoProducto = 1
     *   Segunda unidad → codigoProducto = 2
     *
     * Se utiliza para mantener correlatividad interna.
     */
    @Column(nullable = true, length = 255)
    private Integer codigoProducto;

    /**
     * Código general único del sistema.
     *
     * Formato:
     *   {id_producto_stock}-{id_productos_informacion}
     *
     * Ejemplo:
     *   ProductoStock ID 5
     *   ProductosInformacion ID 27
     *   Código general: 5-27
     *
     * Permite identificar la unidad de manera global y única
     * dentro del sistema completo.
     */
    @Column(nullable = true, length = 255)
    private String codigoGeneral;

    /**
     * Código patrimonial anterior del bien (si aplica).
     *
     * Se utiliza cuando el sistema reemplaza o migra numeraciones históricas.
     */
    @Column(nullable = true, length = 255)
    private String codigoAntiguo;

    /**
     * Número de serie único del bien (si aplica).
     *
     * Fundamental para equipos electrónicos y bienes trazables.
     */
    @Column(name = "numero_de_serie", nullable = true)
    private String numeroDeSerie;

    @Column(name = "observaciones", nullable = true)
    private String observaciones;

    /**
     * Empleado actualmente responsable de la unidad.
     *
     * Puede modificarse en caso de reasignación.
     */
    @ManyToOne
    @JoinColumn(name = "empleado_custodia")
    private Empleado empleadoCustodia;

    /**
     * Indica si la unidad está vigente.
     *
     * - true  → Bien activo en inventario.
     * - false → Bien dado de baja lógica o fuera de uso.
     */
    @Column(nullable = false)
    private Boolean activo = true;

    /**
     * Fecha de última actualización del registro unitario.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

    @PrePersist
    protected void onCreate() {
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ProductosStockFlujo getProductoFlujo() { return productoFlujo; }
    public void setProductoFlujo(ProductosStockFlujo productoFlujo) { this.productoFlujo = productoFlujo; }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
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

    public String getNumeroDeSerie() { return numeroDeSerie; }
    public void setNumeroDeSerie(String numeroDeSerie) { this.numeroDeSerie = numeroDeSerie; }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Empleado getEmpleadoCustodia() {
        return empleadoCustodia;
    }

    public void setEmpleadoCustodia(Empleado empleadoCustodia) {
        this.empleadoCustodia = empleadoCustodia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
