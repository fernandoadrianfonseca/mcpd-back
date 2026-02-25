package com.mcpd.service;

import com.mcpd.dto.PrestamoPendienteDto;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.repository.ProductosStockFlujoRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la gestión del libro contable de stock (ProductosStockFlujo),
 * incluyendo movimientos de alta, baja, custodia y remitos.
 */
@Service
public class ProductosStockFlujoService {

    private final ProductosStockFlujoRepository repository;

    /**
     * Constructor del servicio de flujos de stock.
     *
     * @param repository repositorio de acceso a datos para {@link ProductosStockFlujo}.
     */
    public ProductosStockFlujoService(ProductosStockFlujoRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todos los registros de flujo de stock.
     *
     * @return lista completa de movimientos registrados en el libro contable de stock.
     */
    public List<ProductosStockFlujo> findAll() {
        return repository.findAll();
    }

    /**
     * Busca un flujo de stock por su identificador.
     *
     * @param id identificador del flujo.
     * @return {@link Optional} que contiene el flujo si existe, o vacío si no se encuentra.
     */
    public Optional<ProductosStockFlujo> findById(Integer id) {
        return repository.findById(id);
    }

    /**
     * Guarda o actualiza un registro de flujo de stock.
     *
     * <p>Si la entidad no posee ID, se crea un nuevo registro.
     * Si ya existe, se actualizan sus valores.</p>
     *
     * @param entity entidad {@link ProductosStockFlujo} a persistir.
     * @return entidad guardada con los valores actualizados desde la base de datos.
     */
    public ProductosStockFlujo save(ProductosStockFlujo entity) {
        return repository.save(entity);
    }

    /**
     * Elimina un flujo de stock por su identificador.
     *
     * @param id identificador del flujo a eliminar.
     */
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }


    /**
     * Obtiene todos los flujos asociados a un producto de stock específico.
     *
     * @param productoStockId identificador del producto de stock.
     * @return lista de movimientos registrados para el producto.
     */
    public List<ProductosStockFlujo> findByProductoStockId(Integer productoStockId) {
        return repository.findByProductoStock_Id(productoStockId);
    }

    /**
     * Obtiene los movimientos de tipo {@code alta} de un producto que poseen número de remito registrado.
     *
     * @param productoStockId identificador del producto de stock.
     * @return lista de flujos de ingreso con remito asociado.
     */
    public List<ProductosStockFlujo> findRemitosByProductoStockId(Integer productoStockId) {
        return repository.findRemitosByProductoStockId(productoStockId);
    }

    /**
     * Obtiene las custodias activas actuales de un producto.
     *
     * <p>Devuelve los últimos movimientos de tipo {@code custodia_alta} o {@code custodia_baja}
     * por empleado que aún mantienen stock en custodia (totalLegajoCustodia > 0).</p>
     *
     * @param productoStockId identificador del producto de stock.
     * @return lista de flujos que representan custodias vigentes.
     */
    public List<ProductosStockFlujo> findCustodiasPorProducto(Integer productoStockId) {
        return repository.findCustodiasPorProducto(productoStockId);
    }

    /**
     * Obtiene el historial de movimientos de tipo alta, baja y custodia
     * para un producto específico, con filtro opcional por legajo.
     *
     * <p>Si {@code legajoCustodia} es {@code null}, devuelve todos los movimientos del producto.
     * Si se especifica un legajo, filtra únicamente los movimientos asociados a ese empleado.</p>
     *
     * @param productoStockId identificador del producto de stock.
     * @param legajoCustodia legajo del empleado (opcional).
     * @return lista de flujos ordenados por fecha descendente.
     */
    public List<ProductosStockFlujo> findFlujosAltasYBajasByProductoStockId(Integer productoStockId, Long legajoCustodia) {
        return repository.findFlujosAltasYBajasByProductoStockId(productoStockId, legajoCustodia);
    }

    /**
     * Devuelve los préstamos pendientes de devolución para un legajo.
     *
     * <p>Obtiene los flujos de tipo {@code custodia_alta} con {@code fechaDevolucion != null} y los flujos
     * {@code custodia_baja} del mismo legajo. Agrupa por producto y descuenta las bajas sobre las altas
     * en memoria (reduciendo cantidades) para determinar qué préstamos mantienen cantidad remanente.</p>
     *
     * <p>Para cada alta con remanente, genera un {@link PrestamoPendienteDto} con la cantidad restante,
     * la fecha de devolución formateada y un estado {@code A TIEMPO} o {@code ATRASADO} según comparación con la fecha actual.</p>
     */
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
                            alta.setCantidad(alta.getCantidad() - descontar); //MUTACION EN MEMORIA NO PERSISTE
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
