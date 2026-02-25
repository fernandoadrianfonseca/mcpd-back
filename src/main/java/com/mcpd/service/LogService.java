package com.mcpd.service;

import com.mcpd.model.Log;
import com.mcpd.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de acceso y gestión de logs heredados
 * del sistema legacy ({@link Log}).
 *
 * <p>
 * Provee operaciones CRUD básicas sobre la tabla
 * {@code seguridadoperadorlog}.
 *
 * <p>
 * Este servicio no implementa lógica adicional ni
 * auditoría estructurada, ya que la tabla pertenece
 * al modelo histórico del sistema.
 */
@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    /** Obtiene todos los registros legacy de log. */
    public List<Log> obtenerTodos() {
        return logRepository.findAll();
    }

    /**
     * Obtiene un registro legacy por id.
     *
     * @param id identificador del log.
     * @return Optional con el registro si existe.
     */
    public Optional<Log> obtenerPorId(Long id) {
        return logRepository.findById(id);
    }

    /**
     * Persiste un nuevo registro legacy de log.
     *
     * @param log entidad a guardar.
     * @return registro persistido.
     */
    public Log guardar(Log log) {
        return logRepository.save(log);
    }

    /**
     * Elimina un registro legacy por id.
     *
     * @param id identificador del log.
     */
    public void eliminar(Long id) {
        logRepository.deleteById(id);
    }
}
