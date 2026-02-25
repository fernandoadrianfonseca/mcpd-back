package com.mcpd.service;

import com.mcpd.model.Proveedor;
import com.mcpd.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de proveedores.
 *
 * <p>
 * Encapsula la lógica de acceso a datos y transformación
 * necesaria para enriquecer la información del proveedor
 * con datos provenientes de {@link Contribuyente}.
 * </p>
 */
@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    /** Obtiene todos los proveedores sin enriquecer datos. */
    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    /**
     * Obtiene un proveedor por CUIT.
     *
     * @param cuit identificador único del proveedor.
     * @return Optional con el proveedor si existe.
     */
    public Optional<Proveedor> obtenerPorCuit(Long cuit) {
        return proveedorRepository.findById(cuit);
    }

    /**
     * Guarda o actualiza un proveedor.
     *
     * @param proveedor entidad a persistir.
     * @return proveedor persistido.
     */
    public Proveedor guardar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    /**
     * Elimina un proveedor por CUIT.
     *
     * @param cuit identificador único.
     */
    public void eliminar(Long cuit) {
        proveedorRepository.deleteById(cuit);
    }

    /**
     * Obtiene todos los proveedores incluyendo el nombre
     * del contribuyente asociado.
     *
     * <p>
     * Ejecuta una consulta personalizada y asigna el nombre
     * al campo transitorio {@code nombre}.
     * </p>
     *
     * @return lista de proveedores con nombre enriquecido.
     */
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