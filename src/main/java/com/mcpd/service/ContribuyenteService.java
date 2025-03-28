package com.mcpd.service;

import com.mcpd.model.Contribuyente;
import com.mcpd.repository.ContribuyenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContribuyenteService {

    @Autowired
    private ContribuyenteRepository contribuyenteRepository;

    public List<Contribuyente> obtenerTodos() {
        return contribuyenteRepository.findAll();
    }

    public Optional<Contribuyente> obtenerPorCuit(Long cuit) {
        return contribuyenteRepository.findById(cuit);
    }

    public Contribuyente guardar(Contribuyente contribuyente) {
        return contribuyenteRepository.save(contribuyente);
    }

    public Optional<Contribuyente> actualizar(Long cuit, Contribuyente nuevo) {
        return contribuyenteRepository.findById(cuit).map(c -> {
            nuevo.setCuit(cuit);
            return contribuyenteRepository.save(nuevo);
        });
    }

    public void eliminar(Long cuit) {
        contribuyenteRepository.deleteById(cuit);
    }
}
