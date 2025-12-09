package com.mcpd.service;

import com.mcpd.dto.ProductosInformacionDto;
import com.mcpd.model.Empleado;
import com.mcpd.model.ProductosInformacion;
import com.mcpd.repository.EmpleadoRepository;
import com.mcpd.repository.ProductosInformacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductosInformacionService {

    private final ProductosInformacionRepository repository;

    private final EmpleadoRepository empleadoRepository;

    public ProductosInformacionService(ProductosInformacionRepository repository, EmpleadoRepository empleadoRepository) {
        this.repository = repository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<ProductosInformacion> findAll() {
        return repository.findAll();
    }

    public Optional<ProductosInformacion> findById(Integer id) {
        return repository.findById(id);
    }

    public ProductosInformacion save(ProductosInformacion entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<ProductosInformacion> findByProductoFlujoId(Integer flujoId) {
        return repository.findByProductoFlujo_Id(flujoId);
    }

    public List<ProductosInformacion> findByProductoStockId(Integer productoStockId, Boolean activo, Long empleadoCustodiaId) {
        return repository.findByProductoStockIdOpcionalActivoYEmpleado(productoStockId, activo, empleadoCustodiaId);
    }

    public List<ProductosInformacion> saveAll(List<ProductosInformacion> lista) {

        // Verificar duplicados antes de guardar
        for (ProductosInformacion producto : lista) {
            if (producto.getNumeroDeSerie() != null &&
                    repository.existsByActivoTrueAndNumeroDeSerie(producto.getNumeroDeSerie())) {
                throw new RuntimeException("El número de serie '" + producto.getNumeroDeSerie() + "' ya existe activo.");
            }

            if (producto.getCodigoAntiguo() != null &&
                    repository.existsByActivoTrueAndCodigoAntiguo(producto.getCodigoAntiguo())) {
                throw new RuntimeException("El código antiguo '" + producto.getCodigoAntiguo() + "' ya existe activo.");
            }
        }

        // Si todo está bien, guardar todos
        return repository.saveAll(lista);
    }

    public List<ProductosInformacion> obtenerNumerosDeSerieActivosSinCustodia(Integer productoStockId) {
        return repository.findActivosSinCustodiaPorProductoStock(productoStockId);
    }

    @Transactional
    public List<ProductosInformacionDto> asignarCustodia(List<Integer> ids, Long legajo) {

        List<ProductosInformacion> productos = repository.findAllById(ids);
        Empleado empleado = legajo != null ? empleadoRepository.getReferenceById(legajo) : null;

        productos.forEach(p -> {

            p.setEmpleadoCustodia(empleado);
            repository.save(p);
        });

        return productos.stream().map(p -> {
            ProductosInformacionDto dto = new ProductosInformacionDto();
            dto.setId(p.getId());
            dto.setNumeroDeSerie(p.getNumeroDeSerie());
            dto.setEmpleadoCustodia(p.getEmpleadoCustodia() != null ? p.getEmpleadoCustodia().getLegajo() : null);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductosInformacion> darDeBajaNumerosDeSerie(List<Integer> ids) {
        List<ProductosInformacion> series = repository.findAllById(ids);
        for (ProductosInformacion serie : series) {
            serie.setActivo(false);
        }
        return repository.saveAll(series);
    }

    public List<ProductosInformacion> obtenerCodigosLibres(Integer idStock, Integer cantidad, List<Integer> idsYaElegidos) {

        // Si viene null o vacía → usar un valor imposible
        if (idsYaElegidos == null || idsYaElegidos.isEmpty()) {
            idsYaElegidos = List.of(0);
        }

        return repository.findCodigosLibres(idStock, cantidad, idsYaElegidos);
    }

    public List<ProductosInformacion> obtenerActivosXStockId(Integer idStock, Integer cantidad, List<Integer> idsYaElegidos) {

        // Si viene null o vacía → usar un valor imposible
        if (idsYaElegidos == null || idsYaElegidos.isEmpty()) {
            idsYaElegidos = List.of(0);
        }

        return repository.findActivosXStockId(idStock, cantidad, idsYaElegidos);
    }

    public List<ProductosInformacion> obtenerCodigosEnCustodiaXEmpleado(Integer idStock, Integer cantidad, Integer legajo, List<Integer> idsYaElegidos) {

        // Si viene null o vacía → usar un valor imposible
        if (idsYaElegidos == null || idsYaElegidos.isEmpty()) {
            idsYaElegidos = List.of(0);
        }

        return repository.findCodigosEnCustodiaXEmpleado(idStock, cantidad, legajo, idsYaElegidos);
    }
}
