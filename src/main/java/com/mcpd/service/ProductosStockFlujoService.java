package com.mcpd.service;

import com.mcpd.dto.PrestamoPendienteDto;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.repository.ProductosStockFlujoRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductosStockFlujoService {

    private final ProductosStockFlujoRepository repository;

    public ProductosStockFlujoService(ProductosStockFlujoRepository repository) {
        this.repository = repository;
    }

    public List<ProductosStockFlujo> findAll() {
        return repository.findAll();
    }

    public Optional<ProductosStockFlujo> findById(Integer id) {
        return repository.findById(id);
    }

    public ProductosStockFlujo save(ProductosStockFlujo entity) {
        return repository.save(entity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<ProductosStockFlujo> findByProductoStockId(Integer productoStockId) {
        return repository.findByProductoStock_Id(productoStockId);
    }

    public List<ProductosStockFlujo> findRemitosByProductoStockId(Integer productoStockId) {
        return repository.findRemitosByProductoStockId(productoStockId);
    }

    public List<ProductosStockFlujo> findCustodiasPorProducto(Integer productoStockId) {
        return repository.findCustodiasPorProducto(productoStockId);
    }

    public List<ProductosStockFlujo> findFlujosAltasYBajasByProductoStockId(Integer productoStockId, Long legajoCustodia) {
        return repository.findFlujosAltasYBajasByProductoStockId(productoStockId, legajoCustodia);
    }

    public List<PrestamoPendienteDto> getPrestamosPendientesPorLegajo(Long legajo) {
        List<ProductosStockFlujo> flujos = repository.findFlujoDeStockConFechaDevolucionPorLegajo(legajo);

        Map<Long, List<ProductosStockFlujo>> flujosPorProducto = flujos.stream()
                .collect(Collectors.groupingBy(f -> f.getProductoStock().getId().longValue(), LinkedHashMap::new, Collectors.toList()));

        List<PrestamoPendienteDto> pendientes = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date hoy = new Date();

        for (Map.Entry<Long, List<ProductosStockFlujo>> entry : flujosPorProducto.entrySet()) {
            List<ProductosStockFlujo> lista = entry.getValue();

            List<ProductosStockFlujo> altas = lista.stream()
                    .filter(f -> "custodia_alta".equals(f.getTipo()) && f.getFechaDevolucion() != null)
                    .sorted(Comparator.comparing(ProductosStockFlujo::getFechaDevolucion))
                    .collect(Collectors.toList());

            List<ProductosStockFlujo> bajas = lista.stream()
                    .filter(f -> "custodia_baja".equals(f.getTipo()))
                    .sorted(Comparator.comparing(ProductosStockFlujo::getFecha))
                    .collect(Collectors.toList());

            for (ProductosStockFlujo baja : bajas) {
                Long restante = baja.getCantidad();
                int i = 0;

                while (restante > 0 && i < altas.size()) {
                    ProductosStockFlujo alta = altas.get(i);

                    if (baja.getFecha().after(alta.getFecha())) {
                        if (alta.getCantidad() > 0) {
                            long descontar = Math.min(restante, alta.getCantidad());
                            alta.setCantidad(alta.getCantidad() - descontar);
                            restante -= descontar;
                        }

                        if (alta.getCantidad() == 0) {
                            i++;
                        }
                    } else {
                        i++;
                    }
                }
            }

            for (ProductosStockFlujo altaPendiente : altas) {
                if (altaPendiente.getCantidad() > 0) {
                    String fechaDevStr = formatter.format(altaPendiente.getFechaDevolucion());
                    String estado = altaPendiente.getFechaDevolucion().after(hoy) ? "A TIEMPO" : "ATRASADO";

                    PrestamoPendienteDto dto = new PrestamoPendienteDto(
                            altaPendiente.getId(),
                            altaPendiente,
                            altaPendiente.getCantidad(),
                            fechaDevStr,
                            estado
                    );
                    pendientes.add(dto);
                }
            }
        }

        return pendientes;
    }
}
