package com.mcpd.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Representa un ítem físico dentro del inventario municipal.
 *
 * <p>
 * Esta entidad modela el stock consolidado de un producto determinado,
 * incluyendo su cantidad total y la cantidad actualmente asignada
 * en custodia a empleados.
 *
 * No representa movimientos históricos (eso corresponde a
 * {@link ProductosStockFlujo}), sino el estado actual del inventario.
 *
 * <h3>Conceptos clave</h3>
 *
 * <ul>
 *   <li><b>cantidad</b>: Total físico existente (incluye disponible + en custodia).</li>
 *   <li><b>cantidadCustodia</b>: Unidades actualmente asignadas a empleados.</li>
 *   <li><b>Disponible</b>: cantidad - cantidadCustodia.</li>
 *   <li><b>consumible</b>: Si es true, no se espera devolución.</li>
 *   <li><b>conDevolucion</b>: Si es true, requiere devolución (ej: notebooks).</li>
 * </ul>
 *
 * La trazabilidad histórica de cambios se encuentra en
 * {@link ProductosStockFlujo}.
 */

@Entity
@Table(name = "ProductosStock")
public class ProductosStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Representa una categoría general de productos dentro del sistema.
     *
     * <p>
     * Las categorías permiten agrupar productos del catálogo
     * según su naturaleza funcional.
     *
     * Ejemplos:
     * - Informática
     * - Mobiliario
     * - Limpieza
     * - Seguridad
     *
     * Esta entidad no maneja stock ni cantidades.
     * Solo define una clasificación organizativa.
     *
     * Relación:
     * Una categoría puede contener múltiples {@link Producto}.
     */
    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false)
    private ProductosCategoria categoria;

    @Column(nullable = false)
    private String categoriaNombre;

    /**
     * Representa un producto abstracto dentro del catálogo municipal.
     *
     * <p>
     * Esta entidad define la descripción conceptual del producto,
     * pero no representa unidades físicas ni cantidades en inventario.
     *
     * Ejemplos:
     * - Notebook
     * - Monitor
     * - Escritorio metálico
     * - Cable USB
     *
     * El stock físico asociado se encuentra en {@link ProductosStock}.
     *
     * Relación:
     * - Pertenece a una {@link ProductosCategoria}.
     * - Puede tener múltiples registros en {@link ProductosStock}.
     *
     * Separar catálogo de stock permite:
     * - Reutilizar definiciones de producto
     * - Mantener trazabilidad histórica
     * - Gestionar distintas variantes físicas (marca, modelo, detalle)
     */
    @ManyToOne
    @JoinColumn(name = "producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private String productoNombre;

    /**
     * Cantidad total física del producto en inventario.
     *
     * Incluye tanto unidades disponibles como unidades
     * actualmente en custodia.
     */
    @Column(nullable = false)
    private Integer cantidad;

    /**
     * Cantidad actualmente asignada a empleados.
     *
     * Estas unidades no están disponibles para nuevas asignaciones.
     */
    @Column(name = "cantidad_custodia", nullable = false)
    private Integer cantidadCustodia;

    private String marca;
    private String modelo;
    private String detalle;

    /**
     * Fecha de creación del registro en inventario.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fechaDeCarga;

    /**
     * Fecha de última modificación del registro.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

    /**
     * Tipo funcional del ítem de stock.
     *
     * Valores actuales:
     * - "insumo" → Bien consumible o de uso corriente (ej: papel, tóner, cables).
     * - "dotacion fija" → Bien inventariable asignable a empleados (ej: notebook, radio).
     *
     * Esto determina cómo se comporta el stock respecto a:
     * - Custodia
     * - Devolución
     * - Movimientos de flujo
     *
     * Esta clasificación impacta en el comportamiento del sistema:
     * - Los "insumo" generalmente no requieren devolución.
     * - La "dotacion fija" puede implicar custodia y trazabilidad.
     */
    @Column(nullable = false, length = 255)
    private String tipo;

    /**
     * Indica si el producto es consumible.
     *
     * Si es true:
     * - No se espera devolución.
     * - Las salidas descuentan definitivamente del stock.
     */
    @Column(nullable = false)
    private Boolean consumible;

    /**
     * Indica si el producto requiere devolución al finalizar la custodia.
     *
     * Si es true:
     * - Se registran movimientos de custodia.
     * - Puede existir fechaDevolucion en el flujo.
     */
    @Column(name = "con_devolucion", nullable = false)
    private Boolean conDevolucion;

    /**
     * Campo transitorio utilizado para cálculos específicos
     * de custodia por empleado.
     *
     * No se persiste en base de datos.
     *
     * Generalmente se utiliza para exponer al frontend
     * la cantidad asignada a un legajo determinado.
     */
    @Transient
    private Integer cantidadCustodiaLegajo;

    @PrePersist
    protected void onCreate() {
        fechaDeCarga = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductosCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ProductosCategoria categoria) {
        this.categoria = categoria;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadCustodia() {
        return cantidadCustodia;
    }

    public void setCantidadCustodia(Integer cantidadCustodia) {
        this.cantidadCustodia = cantidadCustodia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaDeCarga() {
        return fechaDeCarga;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getConsumible() {
        return consumible;
    }

    public void setConsumible(Boolean consumible) {
        this.consumible = consumible;
    }

    public Boolean getConDevolucion() {
        return conDevolucion;
    }

    public void setConDevolucion(Boolean conDevolucion) {
        this.conDevolucion = conDevolucion;
    }

    public Integer getCantidadCustodiaLegajo() {
        return cantidadCustodiaLegajo;
    }

    public void setCantidadCustodiaLegajo(Integer cantidadCustodiaLegajo) {
        this.cantidadCustodiaLegajo = cantidadCustodiaLegajo;
    }
}