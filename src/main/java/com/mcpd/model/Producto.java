package com.mcpd.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Entidad que representa un producto dentro del sistema de inventario.
 *
 * <p>
 * Un producto es la definición base del artículo administrado por el sistema.
 * No representa unidades físicas individuales, sino el concepto general
 * (por ejemplo: "Notebook", "Silla", "Monitor").
 * </p>
 *
 * <h3>Relaciones</h3>
 * <ul>
 *   <li>Pertenece a una {@link ProductosCategoria} (ManyToOne).</li>
 *   <li>Puede estar asociado a múltiples registros en ProductosStock.</li>
 * </ul>
 *
 * <p>
 * Incluye control automático de fechas de creación y actualización.
 * </p>
 */
@Entity
@Table(name = "Productos")
public class Producto {

    /** Identificador único del producto (autogenerado). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre descriptivo del producto. */
    @Column(nullable = false)
    private String nombre;

    /**
     * Categoría a la que pertenece el producto.
     *
     * Relación ManyToOne con {@link ProductosCategoria}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria", nullable = false)
    private ProductosCategoria categoria;

    /**
     * Nombre de la categoría almacenado como redundancia.
     *
     * Se utiliza para facilitar consultas o evitar joins
     * innecesarios en determinadas operaciones.
     */
    @Column(nullable = false)
    private String categoriaNombre;

    /**
     * Fecha y hora de creación del registro.
     * Se establece automáticamente al persistir.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fechaDeCarga;

    /**
     * Fecha y hora de última actualización del registro.
     * Se actualiza automáticamente en cada modificación.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated;

    /**
     * Inicializa automáticamente las fechas de creación y actualización
     * al momento de persistir la entidad.
     */
    @PrePersist
    protected void onCreate() {
        fechaDeCarga = new Date();
        updated = new Date();
    }

    /**
     * Actualiza la fecha de modificación antes de cada update.
     */
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getFechaDeCarga() {
        return fechaDeCarga;
    }

    public Date getUpdated() {
        return updated;
    }
}
