package com.mcpd.controller;

import com.mcpd.dto.CustodiaItem;
import com.mcpd.dto.StockCategoriaDto;
import com.mcpd.dto.StockProductoDto;
import com.mcpd.model.ProductosStock;
import com.mcpd.service.ProductosStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST del módulo de inventario (productos_stock).
 *
 * <p>
 * Expone endpoints para:
 * - Consultar el inventario consolidado (estado actual)
 * - Consultar stock en custodia por empleado
 * - Obtener stock disponible para asignar
 * - Ejecutar operaciones de custodia (asignar, quitar, transferir)
 * - Obtener reportes consolidados por categoría y por producto
 *
 * Este controlador no contiene reglas de negocio: delega toda la lógica
 * en {@link ProductosStockService}.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/stock")
public class ProductosStockController {

    private final ProductosStockService productosStockService;

    public ProductosStockController(ProductosStockService productosStockService) {
        this.productosStockService = productosStockService;
    }

    @GetMapping
    public List<ProductosStock> getAll() {
        return productosStockService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosStock> getById(@PathVariable Integer id) {
        return productosStockService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosStock create(@RequestBody ProductosStock entity) {
        return productosStockService.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosStock> update(@PathVariable Integer id, @RequestBody ProductosStock entity) {
        Optional<ProductosStock> existing = productosStockService.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(productosStockService.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosStock> existing = productosStockService.getById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productosStockService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene el stock actualmente en custodia de un empleado.
     *
     * <p>
     * Devuelve los ítems de stock con saldo en custodia > 0 para el legajo indicado.
     * En cada ítem retornado se informa la cantidad en custodia del legajo a través
     * del campo transitorio {@code cantidadCustodiaLegajo}.
     *
     * @param legajoCustodia legajo del empleado.
     * @return lista de stock en custodia del empleado.
     */
    @GetMapping("/custodia/{legajoCustodia}")
    public List<ProductosStock> getStockActualPorCustodia(@PathVariable Long legajoCustodia) {
        return productosStockService.getStockActualPorCustodia(legajoCustodia);
    }

    /**
     * Obtiene el stock actualmente en custodia excluyendo un legajo.
     *
     * <p>
     * Se utiliza principalmente para transferencias: permite listar stock que está
     * asignado a otros empleados distintos al legajo excluido.
     *
     * @param legajoCustodia legajo a excluir.
     * @return lista de stock con custodia en otros legajos.
     */
    @GetMapping("/excluyendo-custodia/{legajoCustodia}")
    public List<ProductosStock> getStockActualExcluyendoCustodia(@PathVariable Long legajoCustodia) {
        return productosStockService.getStockActualExcluyendoCustodia(legajoCustodia);
    }

    /**
     * Obtiene los ítems con stock disponible para nuevas asignaciones.
     *
     * <p>
     * Se consideran disponibles aquellos donde:
     * {@code cantidad - cantidadCustodia > 0}.
     *
     * @return lista de ítems disponibles para asignar.
     */
    @GetMapping("/disponible-asignar")
    public List<ProductosStock> getStockDisponibleParaAsignar() {
        return productosStockService.getStockDisponibleParaAsignar();
    }

    /**
     * Asigna custodia de stock a un empleado.
     *
     * <p>
     * Registra movimientos de tipo "custodia_alta" en el flujo contable y actualiza
     * el stock consolidado según corresponda:
     * - No consumibles: incrementa {@code cantidadCustodia}
     * - Consumibles: descuenta {@code cantidad} (sin devolución)
     *
     * @param items lista de ítems a asignar (stockId, cantidad, observaciones y opcional fechaDevolucion).
     * @param legajoCustodia legajo del empleado que recibe la custodia.
     * @param legajoCarga legajo del usuario que registra la operación.
     */
    @PostMapping("/asignar-custodia")
    public void asignarCustodia(@RequestBody List<CustodiaItem> items,
                                @RequestParam("legajoCustodia") Long legajoCustodia,
                                @RequestParam("legajoCarga") Long legajoCarga) {
        productosStockService.asignarCustodia(items, legajoCustodia, legajoCarga);
    }

    /**
     * Quita custodia a un empleado (devolución).
     *
     * <p>
     * Registra movimientos de tipo "custodia_baja" en el flujo contable y decrementa
     * {@code cantidadCustodia} en el stock consolidado.
     *
     * @param items lista de ítems a devolver (stockId, cantidad, observaciones).
     * @param legajoCustodia legajo del empleado que devuelve.
     * @param legajoCarga legajo del usuario que registra la operación.
     */
    @PostMapping("/quitar-custodia")
    public void quitarCustodia(@RequestBody List<CustodiaItem> items,
                               @RequestParam("legajoCustodia") Long legajoCustodia,
                               @RequestParam("legajoCarga") Long legajoCarga) {
        productosStockService.quitarCustodia(items, legajoCustodia, legajoCarga);
    }

    /**
     * Transfiere custodia de un empleado a otro.
     *
     * <p>
     * Registra dos movimientos en el flujo contable:
     * - "custodia_baja" para el legajo origen
     * - "custodia_alta" para el legajo destino
     *
     * No modifica {@code cantidadCustodia} total del stock consolidado,
     * ya que la custodia global no cambia: solo cambia el responsable.
     *
     * @param items lista de ítems a transferir (stockId, cantidad, observaciones).
     * @param legajoOrigen legajo actual responsable.
     * @param legajoDestino legajo nuevo responsable.
     * @param legajoCarga legajo del usuario que registra la operación.
     */
    @PostMapping("/transferir-custodia")
    public void transferirCustodia(
            @RequestBody List<CustodiaItem> items,
            @RequestParam("legajoOrigen") Long legajoOrigen,
            @RequestParam("legajoDestino") Long legajoDestino,
            @RequestParam("legajoCarga") Long legajoCarga
    ) {
        productosStockService.transferirCustodia(items, legajoOrigen, legajoDestino, legajoCarga);
    }

    /**
     * Devuelve un resumen consolidado del inventario agrupado por categoría.
     *
     * @return listado de {@link StockCategoriaDto}.
     */
    @GetMapping("/por-categorias")
    public List<StockCategoriaDto> obtenerStockPorCategoria() {
        return productosStockService.obtenerStockPorCategoria();
    }

    /**
     * Devuelve un resumen consolidado del inventario agrupado por producto.
     *
     * @return listado de {@link StockProductoDto}.
     */
    @GetMapping("/por-producto")
    public List<StockProductoDto> stockPorProducto() {
        return productosStockService.obtenerStockPorProducto();
    }
}
