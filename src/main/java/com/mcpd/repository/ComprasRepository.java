package com.mcpd.repository;

import com.mcpd.dto.ComprasImputacionDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class ComprasRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<ComprasImputacionDto> obtenerImputaciones() {
        Query query = entityManager.createNativeQuery("EXEC comprasImputaciones");
        List<Object[]> resultados = query.getResultList();
        List<ComprasImputacionDto> lista = new ArrayList<>();

        for (Object[] fila : resultados) {
            lista.add(new ComprasImputacionDto(
                    (String) fila[0],
                    (String) fila[1],
                    (String) fila[2]
            ));
        }

        /*lista.sort(Comparator.comparing(
                ComprasImputacionDto::getImputacion,
                Comparator.nullsLast(String::compareToIgnoreCase)
        ));*/

        return lista;
    }
}
