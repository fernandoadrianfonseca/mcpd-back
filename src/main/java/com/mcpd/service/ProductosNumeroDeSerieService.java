package com.mcpd.service;

import com.mcpd.dto.ProductosNumeroDeSerieDto;
import com.mcpd.model.Empleado;
import com.mcpd.model.ProductosNumeroDeSerie;
import com.mcpd.repository.EmpleadoRepository;
import com.mcpd.repository.ProductosNumeroDeSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductosNumeroDeSerieService {

    private final ProductosNumeroDeSerieRepository repository;

    private final EmpleadoRepository empleadoRepository;

    public ProductosNumeroDeSerieService(ProductosNumeroDeSerieRepository repository, EmpleadoRepository empleadoRepository) {
        this.repository = repository;
        this.empleadoRepository = empleadoRepository;
    }

    public List<ProductosNumeroDeSerie> findAll() {
        return repository.findAll();
    }

    public Optional<ProductosNumeroDeSerie> findById(Integer id) {
        return repository.findById(id);
    }

    public ProductosNumeroDeSerie save(ProductosNumeroDeSerie entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<ProductosNumeroDeSerie> findByProductoFlujoId(Integer flujoId) {
        return repository.findByProductoFlujo_Id(flujoId);
    }

    public List<ProductosNumeroDeSerie> findByProductoStockId(Integer productoStockId, Boolean activo, Long empleadoCustodiaId) {
        return repository.findByProductoStockIdOpcionalActivoYEmpleado(productoStockId, activo, empleadoCustodiaId);
    }

    public List<ProductosNumeroDeSerie> saveAll(List<ProductosNumeroDeSerie> lista) {
        return repository.saveAll(lista);
    }

    public List<ProductosNumeroDeSerie> obtenerNumerosDeSerieActivosSinCustodia(Integer productoStockId) {
        return repository.findActivosSinCustodiaPorProductoStock(productoStockId);
    }

    @Transactional
    public List<ProductosNumeroDeSerieDto> asignarCustodia(List<Integer> ids, Long legajo) {

        List<ProductosNumeroDeSerie> productos = repository.findAllById(ids);
        Empleado empleado = legajo != null ? empleadoRepository.getReferenceById(legajo) : null;

        productos.forEach(p -> {

            p.setEmpleadoCustodia(empleado);
            repository.save(p);
        });

        return productos.stream().map(p -> {
            ProductosNumeroDeSerieDto dto = new ProductosNumeroDeSerieDto();
            dto.setId(p.getId());
            dto.setNumeroDeSerie(p.getNumeroDeSerie());
            dto.setEmpleadoCustodia(p.getEmpleadoCustodia() != null ? p.getEmpleadoCustodia().getLegajo() : null);
            return dto;
        }).collect(Collectors.toList());
    }
}
