package com.mcpd.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad heredada del sistema anterior para el registro
 * de movimientos operativos de usuarios.
 *
 * <p>
 * Mapea la tabla legacy {@code seguridadoperadorlog},
 * utilizada históricamente para almacenar acciones realizadas
 * por operadores del sistema.
 *
 * <h3>Diferencias con el nuevo sistema</h3>
 * <ul>
 *   <li>No reemplaza {@link com.mcpd.model.ReportesLog}</li>
 *   <li>No forma parte del nuevo módulo de auditoría estructurada</li>
 *   <li>Se mantiene por compatibilidad con el sistema anterior</li>
 * </ul>
 *
 * <p>
 * Representa un log simple basado en:
 * operador + fecha + descripción textual del movimiento.
 */
@Entity
@Table(name = "seguridadoperadorlog")
public class Log implements Serializable {

    /** Identificador único del registro de log (autogenerado). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Identificador del operador que realizó la acción.
     * En el sistema legacy suele representar usuario o legajo.
     */
    @Column(name = "operador", nullable = false, length = 254)
    private String operador;

    /**
     * Fecha y hora en que se registró el movimiento.
     * Se establece automáticamente al persistir.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    /**
     * Descripción textual del movimiento realizado.
     * No estructurado (texto libre).
     */
    @Column(name = "movimiento", nullable = false, length = 254)
    private String movimiento;

    /**
     * Inicializa automáticamente la fecha del log
     * al momento de su creación.
     */
    @PrePersist
    protected void onCreate() {
        this.fecha = new Date();
    }

    // ✅ Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }
}
