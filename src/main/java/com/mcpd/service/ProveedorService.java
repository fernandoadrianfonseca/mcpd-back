package com.mcpd.service;

import com.mcpd.model.Proveedor;
import com.mcpd.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerPorCuit(Long cuit) {
        return proveedorRepository.findById(cuit);
    }

    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void eliminar(Long cuit) {
        proveedorRepository.deleteById(cuit);
    }

    public List<Proveedor> obtenerTodosConNombre() {
        List<Object[]> results = proveedorRepository.findAllWithNombreContribuyente();
        List<Proveedor> proveedores = new ArrayList<>();

        for (Object[] result : results) {
            Proveedor proveedor = (Proveedor) result[0];
            String nombre = (String) result[1];

            proveedor.setNombre(nombre);
            proveedores.add(proveedor);
        }

        return proveedores;
    }
}