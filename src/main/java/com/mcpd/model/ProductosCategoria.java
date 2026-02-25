package com.mcpd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcpd.model.Producto;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entidad que representa una categoría de productos dentro del sistema.
 *
 * Categoría
 *    ↓
 * Producto
 *    ↓
 * Stock (módulo contable)
 *    ↓
 * Unidades individuales
 *
 * <p>
 * Permite agrupar instancias de {@link Producto} bajo una clasificación común,
 * facilitando organización, filtrado y administración del inventario.
 * </p>
 *
 * <h3>Relaciones</h3>
 * <ul>
 *   <li>Una categoría puede tener múltiples productos (OneToMany).</li>
 * </ul>
 *
 * <p>
 * Incluye control automático de fechas de creación y actualización.
 * </p>
 */
@Entity
@Table(name = "ProductosCategorias")
public class ProductosCategoria {

    /** Identificador único de la categoría (autogenerado). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nombre descriptivo de la categoría. */
    @Column(nullable = false)
    private String nombre;

    /**
     * Lista de productos asociados a la categoría.
     *
     * Se ignora en serialización JSON para evitar ciclos de referencia
     * y sobrecarga innecesaria en respuestas REST.
     */
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Producto> productos = new ArrayList<>();

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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Date getFechaDeCarga() {
        return fechaDeCarga;
    }

    public Date getUpdated() {
        return updated;
    }
}
