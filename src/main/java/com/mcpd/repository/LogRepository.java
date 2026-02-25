package com.mcpd.repository;

import com.mcpd.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link Log}.
 *
 * <p>
 * Proporciona operaciones CRUD estándar para la persistencia
 * de registros de auditoría del sistema.
 * </p>
 *
 * <p>
 * Esta entidad suele utilizarse para:
 * <ul>
 *   <li>Registrar acciones realizadas por usuarios.</li>
 *   <li>Auditar operaciones críticas.</li>
 *   <li>Almacenar eventos del sistema o errores.</li>
 * </ul>
 * </p>
 *
 * Extiende {@link org.springframework.data.jpa.repository.JpaRepository}
 * permitiendo:
 * <ul>
 *   <li>Guardar nuevos registros de log.</li>
 *   <li>Consultar logs por ID.</li>
 *   <li>Listar registros.</li>
 *   <li>Eliminar registros si fuera necesario.</li>
 * </ul>
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
