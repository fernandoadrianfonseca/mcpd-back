package com.mcpd.controller;

import com.mcpd.model.Empleado;
import com.mcpd.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para la administración de empleados.
 *
 * <p>
 * Expone endpoints CRUD bajo la ruta base {@code /empleados}.
 * </p>
 *
 * Permite:
 * <ul>
 *   <li>Listar empleados</li>
 *   <li>Consultar por legajo</li>
 *   <li>Crear registros</li>
 *   <li>Actualizar datos</li>
 *   <li>Eliminar registros</li>
 * </ul>
 */
@RestController
@RequestMapping("/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Obtiene la lista completa de empleados.
     *
     * <p>
     * Los resultados incluyen el nombre enriquecido desde la entidad
     * {@link com.mcpd.model.Contribuyente}, y se devuelven ordenados
     * por número de legajo.
     * </p>
     *
     * @return lista de empleados registrados.
     */
    @GetMapping
    public List<Empleado> obtenerTodos() {
        return empleadoService.obtenerTodos();
    }

    /**
     * Obtiene un empleado específico por su número de legajo.
     *
     * @param legajo identificador único del empleado.
     * @return 200 OK con el empleado si existe,
     *         404 Not Found si no se encuentra registrado.
     */
    @GetMapping("/{legajo}")
    public ResponseEntity<Empleado> obtenerPorLegajo(@PathVariable Long legajo) {
        Optional<Empleado> empleado = empleadoService.obtenerPorLegajo(legajo);
        return empleado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo empleado en el sistema.
     *
     * <p>
     * El número de legajo debe ser único.
     * Si ya existe, se sobrescribirá según el comportamiento
     * de JPA {@code save()}.
     * </p>
     *
     * @param empleado datos del nuevo empleado.
     * @return empleado persistido.
     */
    @PostMapping
    public Empleado guardar(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    /**
     * Actualiza los datos de un empleado existente.
     *
     * @param legajo número de legajo del empleado a actualizar.
     * @param empleado nuevos datos del empleado.
     * @return 200 OK con el empleado actualizado si existe,
     *         404 Not Found si no se encuentra registrado.
     */
    @PutMapping("/{legajo}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long legajo, @RequestBody Empleado empleado) {
        Optional<Empleado> actualizado = empleadoService.actualizar(legajo, empleado);
        return actualizado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Elimina un empleado del sistema por su número de legajo.
     *
     * @param legajo identificador único del empleado.
     * @return 204 No Content si la eliminación se realiza correctamente.
     *
     * Nota: No valida existencia previa; si el legajo no existe,
     * el comportamiento dependerá del repositorio JPA.
     */
    @DeleteMapping("/{legajo}")
    public ResponseEntity<Void> eliminar(@PathVariable Long legajo) {
        empleadoService.eliminar(legajo);
        return ResponseEntity.noContent().build();
    }
}
