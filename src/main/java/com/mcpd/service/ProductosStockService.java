package com.mcpd.service;

import com.mcpd.dto.CustodiaItem;
import com.mcpd.dto.StockCategoriaDto;
import com.mcpd.dto.StockConCustodiaDto;
import com.mcpd.dto.StockProductoDto;
import com.mcpd.exception.StockInsuficienteException;
import com.mcpd.model.ProductosStock;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.repository.ProductosStockFlujoRepository;
import com.mcpd.repository.ProductosStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de dominio para la gestión del inventario físico ({@link ProductosStock})
 * y el registro contable de sus movimientos en {@link ProductosStockFlujo}.
 *
 * <p>
 * Este servicio administra:
 * <ul>
 *   <li>Consulta del stock consolidado (total, en custodia y disponible)</li>
 *   <li>Consulta del stock actualmente en custodia por empleado</li>
 *   <li>Asignación, devolución y transferencia de custodia</li>
 *   <li>Generación de registros en productos_stock_flujo como auditoría/trazabilidad</li>
 * </ul>
 *
 * <h3>Reglas clave</h3>
 * <ul>
 *   <li><b>ProductosStock</b> representa el estado actual (no histórico).</li>
 *   <li><b>ProductosStockFlujo</b> registra el historial contable (movimientos).</li>
 *   <li>Movimientos válidos utilizados por este servicio: "custodia_alta" y "custodia_baja".</li>
 *   <li>Para productos <b>no consumibles</b>: la custodia incrementa/decrementa {@code cantidadCustodia}.</li>
 *   <li>Para productos <b>consumibles</b>: la "asignación" descuenta {@code cantidad} (no hay devolución).</li>
 *   <li>El campo {@code totalLegajoCustodia} en flujo es un snapshot del saldo por empleado y producto.</li>
 * </ul>
 *
 * Las operaciones que modifican stock y generan flujos se ejecutan de manera transaccional.
 */

@Service
public class ProductosStockService {

    private final ProductosStockRepository productosStockRepository;

    private final ProductosStockFlujoRepository flujoRepository;

    public ProductosStockService(ProductosStockRepository productosStockRepository, ProductosStockFlujoRepository flujoRepository) {
        this.productosStockRepository = productosStockRepository;
        this.flujoRepository = flujoRepository;
    }

    /**
     * Obtiene todos los ítems de stock existentes.
     *
     * @return listado completo del inventario consolidado.
     */
    public List<ProductosStock> getAll() {
        return productosStockRepository.findAll();
    }

    /**
     * Obtiene un ítem de stock por su identificador.
     *
     * @param id id del producto_stock.
     * @return Optional con el stock si existe, vacío si no existe.
     */
    public Optional<ProductosStock> getById(Integer id) {
        return productosStockRepository.findById(id);
    }

    /**
     * Crea o actualiza un ítem de stock.
     *
     * <p>
     * No registra flujos automáticamente; este método persiste el estado consolidado.
     *
     * @param productosStock entidad a persistir.
     * @return entidad persistida.
     */
    public ProductosStock save(ProductosStock productosStock) {
        return productosStockRepository.save(productosStock);
    }

    /**
     * Elimina un ítem de stock por id.
     *
     * @param id id del producto_stock.
     */
    public void delete(Integer id) {
        productosStockRepository.deleteById(id);
    }

    /**
     * Devuelve el stock actualmente en custodia de un empleado.
     *
     * <p>
     * Se apoya en una consulta que obtiene el último movimiento de custodia
     * por producto y empleado (snapshot), y devuelve solo aquellos cuyo saldo
     * de custodia es mayor a 0.
     *
     * <p>
     * Para cada resultado se completa el campo transitorio
     * {@code cantidadCustodiaLegajo} con el saldo en custodia del legajo.
     *
     * @param legajoCustodia legajo del empleado.
     * @return lista de {@link ProductosStock} con {@code cantidadCustodiaLegajo} informado.
     */
    public List<ProductosStock> getStockActualPorCustodia(Long legajoCustodia) {
        List<StockConCustodiaDto> resultados = productosStockRepository.findStockActualPorCustodia(legajoCustodia);

        return resultados.stream().map(dto -> {
            ProductosStock stock = dto.stock();
            stock.setCantidadCustodiaLegajo(dto.cantidadCustodia().intValue());
            return stock;
        })
        .sorted(Comparator.comparing(ProductosStock::getId))
        .toList();
    }

    /**
     * Devuelve el stock actualmente en custodia, excluyendo un legajo.
     *
     * <p>
     * Se utiliza principalmente para procesos de transferencia, donde se necesita listar
     * stock asignado a otros empleados.
     *
     * <p>
     * Nota: este método reutiliza la proyección de custodia y setea la cantidad obtenida
     * en {@code cantidadCustodia} del objeto retornado para facilitar el render en frontend.
     *
     * @param legajoCustodia legajo a excluir.
     * @return lista de stock con custodia de otros legajos.
     */
    public List<ProductosStock> getStockActualExcluyendoCustodia(Long legajoCustodia) {
        List<StockConCustodiaDto> resultados = productosStockRepository.findStockActualExcluyendoCustodia(legajoCustodia);

        return resultados.stream().map(dto -> {
            ProductosStock stock = dto.stock();
            stock.setCantidadCustodia(dto.cantidadCustodia().intValue());
            return stock;
        })
        .sorted(Comparator.comparing(ProductosStock::getId))
        .toList();
    }

    /**
     * Obtiene los ítems con stock disponible para asignar.
     *
     * <p>
     * Se consideran disponibles aquellos donde:
     * {@code cantidad - cantidadCustodia > 0}.
     *
     * @return lista de stock disponible para nuevas asignaciones.
     */
    public List<ProductosStock> getStockDisponibleParaAsignar() {
        return productosStockRepository.findStockDisponibleParaAsignar();
    }

    /**
     * Asigna custodia de stock a un empleado y registra el movimiento en flujo.
     *
     * <p>
     * Comportamiento según el tipo de ítem:
     * <ul>
     *   <li><b>No consumible</b>: incrementa {@code cantidadCustodia}.</li>
     *   <li><b>Consumible</b>: descuenta {@code cantidad} (validando stock suficiente).</li>
     * </ul>
     *
     * Además registra un {@link ProductosStockFlujo} con:
     * <ul>
     *   <li>{@code tipo = "custodia_alta"}</li>
     *   <li>{@code total = stock.cantidad} (snapshot post-movimiento)</li>
     *   <li>{@code totalLegajoCustodia} actualizado (snapshot por legajo)</li>
     *   <li>{@code fechaDevolucion} solo si {@code conDevolucion = true}</li>
     * </ul>
     *
     * @param items ítems a asignar (stockId, cantidad, observaciones y opcional fechaDevolucion).
     * @param legajoCustodia legajo del empleado que recibe la custodia.
     * @param legajoCarga legajo del usuario que registra la operación.
     * @throws StockInsuficienteException si el ítem es consumible y no hay stock suficiente.
     */
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
            flujo.setTotal(stock.getCantidad().longValue());
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

    /**
     * Quita custodia de stock a un empleado (devolución) y registra el movimiento en flujo.
     *
     * <p>
     * Decrementa {@code cantidadCustodia} del ítem de stock y registra un flujo con:
     * {@code tipo = "custodia_baja"} y snapshot de {@code totalLegajoCustodia}.
     *
     * @param items ítems a devolver (stockId, cantidad, observaciones).
     * @param legajoCustodia legajo del empleado que devuelve.
     * @param legajoCarga legajo del usuario que registra la operación.
     * @throws RuntimeException si no existe suficiente cantidad en custodia para descontar.
     */
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
            flujo.setTotal(stock.getCantidad().longValue());
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

    /**
     * Transfiere custodia de un empleado a otro, registrando dos movimientos de flujo.
     *
     * <p>
     * Esta operación no modifica {@code cantidadCustodia} del stock consolidado,
     * ya que la custodia total del sistema no cambia; solo cambia el legajo responsable.
     *
     * Se registran:
     * <ul>
     *   <li>Un movimiento {@code "custodia_baja"} para el legajo origen</li>
     *   <li>Un movimiento {@code "custodia_alta"} para el legajo destino</li>
     * </ul>
     *
     * Ambos movimientos incluyen snapshots de {@code totalLegajoCustodia}
     * para cada legajo y se registran con observación "Transferencia de custodia ...".
     *
     * @param items ítems a transferir (stockId, cantidad, observaciones).
     * @param legajoOrigen legajo actual responsable.
     * @param legajoDestino legajo nuevo responsable.
     * @param legajoCarga legajo del usuario que registra la operación.
     */
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

    /**
     * Devuelve un resumen consolidado del inventario agrupado por categoría.
     *
     * Incluye totales de:
     * - cantidad
     * - cantidadCustodia
     * - disponible
     *
     * @return listado de {@link StockCategoriaDto}.
     */
    public List<StockCategoriaDto> obtenerStockPorCategoria() {
        return productosStockRepository.obtenerStockPorCategoria();
    }

    /**
     * Devuelve un resumen consolidado del inventario agrupado por producto.
     *
     * Incluye totales de:
     * - cantidad
     * - cantidadCustodia
     * - disponible
     *
     * @return listado de {@link StockProductoDto}.
     */
    public List<StockProductoDto> obtenerStockPorProducto() {
        return productosStockRepository.obtenerStockPorProducto();
    }
}
