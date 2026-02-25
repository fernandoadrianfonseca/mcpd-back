package com.mcpd.service;

import com.mcpd.model.Contribuyente;
import com.mcpd.repository.ContribuyenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de negocio para la gestión de contribuyentes.
 *
 * <p>
 * Encapsula la lógica de acceso a datos y expone operaciones
 * CRUD para la entidad {@link Contribuyente}.
 * </p>
 *
 * <p>
 * Actúa como capa intermedia entre el controller y el repository.
 * </p>
 */
@Service
public class ContribuyenteService {

    @Autowired
    private ContribuyenteRepository contribuyenteRepository;

    /** Obtiene todos los contribuyentes registrados. */
    public List<Contribuyente> obtenerTodos() {
        return contribuyenteRepository.findAll();
    }

    /**
     * Obtiene un contribuyente por CUIT.
     *
     * @param cuit identificador único.
     * @return Optional con el contribuyente si existe.
     */
    public Optional<Contribuyente> obtenerPorCuit(Long cuit) {
        return contribuyenteRepository.findById(cuit);
    }

    /**
     * Guarda un nuevo contribuyente o actualiza uno existente.
     *
     * @param contribuyente entidad a persistir.
     * @return contribuyente persistido.
     */
    public Contribuyente guardar(Contribuyente contribuyente) {
        return contribuyenteRepository.save(contribuyente);
    }

    /**
     * Actualiza un contribuyente existente.
     *
     * @param cuit identificador del contribuyente.
     * @param nuevo datos actualizados.
     * @return Optional con la entidad actualizada si existe.
     */
    public Optional<Contribuyente> actualizar(Long cuit, Contribuyente nuevo) {
        return contribuyenteRepository.findById(cuit).map(c -> {
            nuevo.setCuit(cuit);
            return contribuyenteRepository.save(nuevo);
        });
    }

    /**
     * Elimina un contribuyente por CUIT.
     *
     * @param cuit identificador único.
     */
    public void eliminar(Long cuit) {
        contribuyenteRepository.deleteById(cuit);
    }
}
