package com.mcpd.service;

import com.mcpd.model.Empleado;
import com.mcpd.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de empleados.
 *
 * <p>
 * Se encarga de:
 * <ul>
 *   <li>Obtener empleados enriquecidos con nombre.</li>
 *   <li>Ordenar resultados por legajo.</li>
 *   <li>Encapsular operaciones CRUD.</li>
 * </ul>
 * </p>
 */
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /*public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }*/

    /**
     * Obtiene todos los empleados ordenados por legajo,
     * incluyendo el nombre proveniente de Contribuyente.
     */
    public List<Empleado> obtenerTodos() {
        List<Object[]> results = empleadoRepository.findAllWithContribuyente();
        List<Empleado> empleados = new ArrayList<>();

        for (Object[] result : results) {
            Empleado empleado = (Empleado) result[0];
            String nombre = (String) result[1];

            empleado.setNombre(nombre);
            empleados.add(empleado);
        }
        empleados.sort(Comparator.comparing(Empleado::getLegajo));
        return empleados;
    }

    /**
     * Obtiene un empleado por número de legajo.
     *
     * @param legajo identificador único.
     * @return Optional con el empleado si existe.
     */
    public Optional<Empleado> obtenerPorLegajo(Long legajo) {
        return empleadoRepository.findById(legajo);
    }

    /**
     * Guarda un nuevo empleado o actualiza uno existente.
     *
     * @param empleado entidad a persistir.
     * @return empleado persistido.
     */
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    /**
     * Actualiza un empleado existente.
     *
     * @param legajo identificador.
     * @param nuevo datos actualizados.
     * @return Optional con el empleado actualizado si existe.
     */
    public Optional<Empleado> actualizar(Long legajo, Empleado nuevo) {
        return empleadoRepository.findById(legajo).map(empleadoExistente -> {
            nuevo.setLegajo(legajo);
            return empleadoRepository.save(nuevo);
        });
    }

    /**
     * Elimina un empleado por número de legajo.
     *
     * @param legajo identificador único.
     */
    public void eliminar(Long legajo) {
        empleadoRepository.deleteById(legajo);
    }
}
