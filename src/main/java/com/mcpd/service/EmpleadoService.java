package com.mcpd.service;

import com.mcpd.model.Empleado;
import com.mcpd.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /*public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }*/

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

    public Optional<Empleado> obtenerPorLegajo(Long legajo) {
        return empleadoRepository.findById(legajo);
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public Optional<Empleado> actualizar(Long legajo, Empleado nuevo) {
        return empleadoRepository.findById(legajo).map(empleadoExistente -> {
            nuevo.setLegajo(legajo);
            return empleadoRepository.save(nuevo);
        });
    }

    public void eliminar(Long legajo) {
        empleadoRepository.deleteById(legajo);
    }
}
