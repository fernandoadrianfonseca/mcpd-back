package com.mcpd.service;

import com.mcpd.dto.AsignarCustodiaItem;
import com.mcpd.dto.StockConCustodiaDto;
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

    public List<ProductosStock> getStockDisponibleParaAsignar() {
        return productosStockRepository.findStockDisponibleParaAsignar();
    }

    @Transactional
    public void asignarCustodia(List<AsignarCustodiaItem> items, Long legajoEmpleado) {
        for (AsignarCustodiaItem item : items) {
            ProductosStock stock = productosStockRepository.findById(item.getStockId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado ID: " + item.getStockId()));

            stock.setCantidadCustodia(stock.getCantidadCustodia() + item.getCantidad().intValue());
            productosStockRepository.save(stock);

            ProductosStockFlujo ultimoFlujo = flujoRepository.findUltimoFlujoCustodia(item.getStockId(), legajoEmpleado);
            long totalLegajoAnterior = ultimoFlujo != null ? ultimoFlujo.getTotalLegajoCustodia() : 0;

            ProductosStockFlujo flujo = new ProductosStockFlujo();
            flujo.setProductoStock(stock);
            flujo.setCantidad(item.getCantidad());
            flujo.setTotal(stock.getCantidad().longValue()); // Stock total
            flujo.setTotalLegajoCustodia(totalLegajoAnterior + item.getCantidad());
            flujo.setTipo("custodia_alta");
            flujo.setEmpleadoCustodia(legajoEmpleado);
            flujo.setEmpleadoCarga(legajoEmpleado);
            flujo.setRemito(null);
            flujo.setOrdenDeCompra(null);
            flujo.setObservaciones(item.getObservaciones());
            flujoRepository.save(flujo);
        }
    }
}
