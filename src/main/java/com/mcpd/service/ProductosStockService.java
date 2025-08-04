package com.mcpd.service;

import com.mcpd.dto.CustodiaItem;
import com.mcpd.dto.StockConCustodiaDto;
import com.mcpd.exception.StockInsuficienteException;
import com.mcpd.model.ProductosStock;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.repository.ProductosStockFlujoRepository;
import com.mcpd.repository.ProductosStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosStockService {

    private final ProductosStockRepository productosStockRepository;

    private final ProductosStockFlujoRepository flujoRepository;

    public ProductosStockService(ProductosStockRepository productosStockRepository, ProductosStockFlujoRepository flujoRepository) {
        this.productosStockRepository = productosStockRepository;
        this.flujoRepository = flujoRepository;
    }

    public List<ProductosStock> getAll() {
        return productosStockRepository.findAll();
    }

    public Optional<ProductosStock> getById(Integer id) {
        return productosStockRepository.findById(id);
    }

    public ProductosStock save(ProductosStock productosStock) {
        return productosStockRepository.save(productosStock);
    }

    public void delete(Integer id) {
        productosStockRepository.deleteById(id);
    }

    public List<ProductosStock> getStockActualPorCustodia(Long legajoCustodia) {
        List<StockConCustodiaDto> resultados = productosStockRepository.findStockActualPorCustodia(legajoCustodia);

        return resultados.stream().map(dto -> {
            ProductosStock stock = dto.stock();
            stock.setCantidadCustodia(dto.cantidadCustodia().intValue()); // Seteamos correctamente
            return stock;
        }).toList();
    }

    public List<ProductosStock> getStockActualExcluyendoCustodia(Long legajoCustodia) {
        List<StockConCustodiaDto> resultados = productosStockRepository.findStockActualExcluyendoCustodia(legajoCustodia);

        return resultados.stream().map(dto -> {
            ProductosStock stock = dto.stock();
            stock.setCantidadCustodia(dto.cantidadCustodia().intValue()); // Seteamos correctamente
            return stock;
        }).toList();
    }

    public List<ProductosStock> getStockDisponibleParaAsignar() {
        return productosStockRepository.findStockDisponibleParaAsignar();
    }

    @Transactional
    public void asignarCustodia(List<CustodiaItem> items, Long legajoCustodia, Long legajoCarga) {
        for (CustodiaItem item : items) {
            ProductosStock stock = productosStockRepository.findById(item.getStockId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado ID: " + item.getStockId()));

            if(!stock.getConsumible()){
                stock.setCantidadCustodia(stock.getCantidadCustodia() + item.getCantidad().intValue());
                productosStockRepository.save(stock);
            }
            else{
                int disponible = stock.getCantidad();
                int solicitada = item.getCantidad().intValue();
                int nuevaCantidad = disponible - solicitada;

                if (nuevaCantidad < 0) {
                    throw new StockInsuficienteException(stock.getId(), disponible, solicitada);
                }

                stock.setCantidad(nuevaCantidad);
            }

            ProductosStockFlujo ultimoFlujo = flujoRepository.findUltimoFlujoCustodia(item.getStockId(), legajoCustodia);
            long totalLegajoAnterior = ultimoFlujo != null ? ultimoFlujo.getTotalLegajoCustodia() : 0;

            ProductosStockFlujo flujo = new ProductosStockFlujo();
            flujo.setProductoStock(stock);
            flujo.setCantidad(item.getCantidad());
            flujo.setTotal(stock.getCantidad().longValue()); // Stock total
            flujo.setTotalLegajoCustodia(totalLegajoAnterior + item.getCantidad());
            flujo.setTipo("custodia_alta");
            flujo.setEmpleadoCustodia(legajoCustodia);
            flujo.setEmpleadoCarga(legajoCarga);
            flujo.setRemito(null);
            flujo.setOrdenDeCompra(null);
            flujo.setObservaciones(item.getObservaciones());
            if(stock.getConDevolucion()){
                flujo.setFechaDevolucion(item.getFechaDevolucion());
            }
            flujoRepository.save(flujo);
        }
    }

    @Transactional
    public void quitarCustodia(List<CustodiaItem> items, Long legajoCustodia, Long legajoCarga) {
        for (CustodiaItem item : items) {
            ProductosStock stock = productosStockRepository.findById(item.getStockId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado ID: " + item.getStockId()));

            int nuevaCantidadCustodia = stock.getCantidadCustodia() - item.getCantidad().intValue();
            if (nuevaCantidadCustodia < 0) {
                throw new RuntimeException("No hay suficiente cantidad en custodia para quitar del stock ID: " + item.getStockId());
            }

            stock.setCantidadCustodia(nuevaCantidadCustodia);
            productosStockRepository.save(stock);

            ProductosStockFlujo ultimoFlujo = flujoRepository.findUltimoFlujoCustodia(item.getStockId(), legajoCustodia);
            long totalLegajoAnterior = ultimoFlujo != null ? ultimoFlujo.getTotalLegajoCustodia() : 0;

            ProductosStockFlujo flujo = new ProductosStockFlujo();
            flujo.setProductoStock(stock);
            flujo.setCantidad(item.getCantidad());
            flujo.setTotal(stock.getCantidad().longValue()); // Stock total
            flujo.setTotalLegajoCustodia(totalLegajoAnterior - item.getCantidad());
            flujo.setTipo("custodia_baja");
            flujo.setEmpleadoCustodia(legajoCustodia);
            flujo.setEmpleadoCarga(legajoCarga);
            flujo.setRemito(null);
            flujo.setOrdenDeCompra(null);
            flujo.setObservaciones(item.getObservaciones());
            flujoRepository.save(flujo);
        }
    }

    @Transactional
    public void transferirCustodia(List<CustodiaItem> items, Long legajoOrigen, Long legajoDestino, Long legajoCarga) {
        for (CustodiaItem item : items) {

            ProductosStock stock = productosStockRepository.findById(item.getStockId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado ID: " + item.getStockId()));

            ProductosStockFlujo ultimoFlujoOrigen = flujoRepository.findUltimoFlujoCustodia(item.getStockId(), legajoOrigen);
            long totalOrigenAnterior = ultimoFlujoOrigen != null ? ultimoFlujoOrigen.getTotalLegajoCustodia() : 0;

            ProductosStockFlujo flujoBaja = new ProductosStockFlujo();
            flujoBaja.setProductoStock(stock);
            flujoBaja.setCantidad(item.getCantidad());
            flujoBaja.setTotal(stock.getCantidad().longValue());
            flujoBaja.setTotalLegajoCustodia(totalOrigenAnterior - item.getCantidad());
            flujoBaja.setTipo("custodia_baja");
            flujoBaja.setEmpleadoCustodia(legajoOrigen);
            flujoBaja.setEmpleadoCarga(legajoCarga);
            flujoBaja.setObservaciones("Transferencia de custodia " + Optional.ofNullable(item.getObservaciones()).orElse(""));
            flujoRepository.save(flujoBaja);

            // Alta en legajo destino
            ProductosStockFlujo ultimoFlujoDestino = flujoRepository.findUltimoFlujoCustodia(item.getStockId(), legajoDestino);
            long totalDestinoAnterior = ultimoFlujoDestino != null ? ultimoFlujoDestino.getTotalLegajoCustodia() : 0;

            ProductosStockFlujo flujoAlta = new ProductosStockFlujo();
            flujoAlta.setProductoStock(stock);
            flujoAlta.setCantidad(item.getCantidad());
            flujoAlta.setTotal(stock.getCantidad().longValue());
            flujoAlta.setTotalLegajoCustodia(totalDestinoAnterior + item.getCantidad());
            flujoAlta.setTipo("custodia_alta");
            flujoAlta.setEmpleadoCustodia(legajoDestino);
            flujoAlta.setEmpleadoCarga(legajoCarga);
            flujoAlta.setObservaciones("Transferencia de custodia " + Optional.ofNullable(item.getObservaciones()).orElse(""));
            flujoRepository.save(flujoAlta);
        }
    }
}
