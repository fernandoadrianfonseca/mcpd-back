package com.mcpd.controller;

import com.mcpd.dto.PrestamoPendienteDto;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.service.ProductosStockFlujoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST del módulo de movimientos de stock (productos_stock_flujo).
 *
 * <p>
 * Expone endpoints para:
 * - Consultar el historial completo de movimientos
 * - Filtrar movimientos por producto de stock
 * - Consultar remitos asociados
 * - Consultar custodias activas
 * - Obtener préstamos pendientes de devolución
 *
 * Este controlador delega completamente la lógica de negocio
 * al {@link ProductosStockFlujoService}.
 *
 * Su responsabilidad es:
 * - Exponer el contrato HTTP
 * - Manejar códigos de respuesta
 * - Orquestar la interacción entre cliente y servicio
 *
 * No contiene reglas de negocio.
 */

@RestController
@RequestMapping("/stock-flujo")
@CrossOrigin(origins = "*")
public class ProductosStockFlujoController {

    private final ProductosStockFlujoService service;

    public ProductosStockFlujoController(ProductosStockFlujoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductosStockFlujo> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosStockFlujo> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosStockFlujo create(@RequestBody ProductosStockFlujo entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosStockFlujo> update(@PathVariable Integer id, @RequestBody ProductosStockFlujo entity) {
        Optional<ProductosStockFlujo> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosStockFlujo> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto-stock/{id}")
    public List<ProductosStockFlujo> getByProductoStockId(@PathVariable Integer id) {
        return service.findByProductoStockId(id);
    }

    /**
     * Obtiene los movimientos asociados a remitos
     * para un producto de stock determinado.
     *
     * <p>
     * Generalmente corresponde a movimientos de tipo "alta"
     * que incluyen número de remito.
     *
     * Se utiliza para trazabilidad de ingresos por proveedor.
     *
     * @param id Identificador del producto_stock.
     * @return Lista de movimientos vinculados a remitos.
     */
    @GetMapping("/producto-stock/{id}/remitos")
    public List<ProductosStockFlujo> getRemitosByProductoStockId(@PathVariable Integer id) {
        return service.findRemitosByProductoStockId(id);
    }

    /**
     * Obtiene los movimientos de custodia actualmente activos
     * para un producto de stock determinado.
     *
     * <p>
     * Devuelve registros de tipo "custodia_alta" que aún no fueron
     * completamente compensados por movimientos "custodia_baja".
     *
     * Se utiliza para:
     * - Visualizar asignaciones vigentes
     * - Determinar qué empleados tienen bienes asignados
     *
     * @param id Identificador del producto_stock.
     * @return Lista de movimientos de custodia activos.
     */
    @GetMapping("/producto-stock/{id}/custodias-activas")
    public List<ProductosStockFlujo> getCustodiasActivasByProductoStockId(@PathVariable Integer id) {
        return service.findCustodiasPorProducto(id);
    }

    /**
     * Obtiene los movimientos de tipo "alta" y "baja"
     * asociados a un producto de stock.
     *
     * <p>
     * Permite consultar el historial de ingresos y egresos
     * generales del inventario.
     *
     * Opcionalmente puede filtrarse por legajo de custodia
     * para analizar movimientos relacionados a un empleado específico.
     *
     * @param id Identificador del producto_stock.
     * @param legajoCustodia (Opcional) Legajo para filtrar movimientos asociados.
     * @return Lista de movimientos de alta y baja.
     */
    @GetMapping("/producto-stock/{id}/flujos-altas-bajas")
    public List<ProductosStockFlujo> getAltasYBajasByProductoStockId(
            @PathVariable Integer id,
            @RequestParam(required = false) Long legajoCustodia) {
        return service.findFlujosAltasYBajasByProductoStockId(id, legajoCustodia);
    }

    /**
     * Obtiene el listado de préstamos pendientes de devolución
     * para un empleado específico.
     *
     * <p>
     * El resultado se calcula a partir de movimientos:
     * - "custodia_alta" (asignaciones)
     * - "custodia_baja" (devoluciones)
     *
     * Aplicando lógica FIFO para determinar la cantidad aún pendiente.
     *
     * Devuelve un DTO con:
     * - Movimiento original
     * - Cantidad pendiente
     * - Fecha estimada de devolución
     * - Estado calculado
     *
     * @param legajo Legajo del empleado.
     * @return Lista de préstamos pendientes.
     */
    @GetMapping("/pendientes-devolucion")
    public List<PrestamoPendienteDto> getPrestamosPendientesPorLegajo(@RequestParam Long legajo) {
        return service.getPrestamosPendientesPorLegajo(legajo);
    }
}
