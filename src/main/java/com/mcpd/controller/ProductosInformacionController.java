package com.mcpd.controller;

import com.mcpd.dto.ProductosInformacionDto;
import com.mcpd.model.ProductosInformacion;
import com.mcpd.service.ProductosInformacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de unidades individuales
 * del inventario ({@link ProductosInformacion}).
 *
 * <p>
 * Expone endpoints para:
 * - Consultar bienes unitarios
 * - Filtrar por producto o movimiento de flujo
 * - Asignar o quitar custodia por número de serie
 * - Dar de baja unidades
 * - Obtener códigos disponibles o en custodia
 *
 * Este controlador delega toda la lógica de negocio
 * al {@link ProductosInformacionService}.
 */

@RestController
@RequestMapping("/informacion")
@CrossOrigin(origins = "*")
public class ProductosInformacionController {

    private final ProductosInformacionService service;

    public ProductosInformacionController(ProductosInformacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductosInformacion> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosInformacion> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductosInformacion create(@RequestBody ProductosInformacion entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosInformacion> update(@PathVariable Integer id, @RequestBody ProductosInformacion entity) {
        Optional<ProductosInformacion> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<ProductosInformacion> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todas las unidades asociadas a un movimiento
     * específico de {@link com.mcpd.model.ProductosStockFlujo}.
     *
     * @param id id del movimiento de flujo.
     * @return lista de unidades creadas en ese movimiento.
     */
    @GetMapping("/flujo/{id}")
    public List<ProductosInformacion> getByProductoFlujoId(@PathVariable Integer id) {
        return service.findByProductoFlujoId(id);
    }

    /**
     * Obtiene unidades individuales asociadas a un producto_stock,
     * permitiendo filtros opcionales.
     *
     * Parámetros opcionales:
     * - activo (por defecto true)
     * - empleadoCustodia (filtra por legajo)
     *
     * @param productoStockId id del producto_stock.
     * @param activo indica si se devuelven solo unidades activas.
     * @param empleadoCustodia legajo del empleado (opcional).
     * @return lista de unidades filtradas.
     */
    @GetMapping("/producto-stock/{productoStockId}")
    public List<ProductosInformacion> getByProductoStockId(
            @PathVariable Integer productoStockId,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Long empleadoCustodia) {

        return service.findByProductoStockId(
                productoStockId,
                activo != null ? activo : true,
                empleadoCustodia
        );
    }

    /**
     * Crea múltiples unidades individuales en una sola operación.
     *
     * Antes de persistir, se validan duplicados activos de:
     * - Número de serie
     * - Código antiguo
     *
     * @param lista lista de unidades a crear.
     * @return lista persistida.
     */
    @PostMapping("/lote")
    public List<ProductosInformacion> crearEnLote(@RequestBody List<ProductosInformacion> lista) {
        return service.saveAll(lista);
    }

    /**
     * Obtiene unidades activas de un producto_stock
     * que actualmente no tienen empleado asignado.
     *
     * Se utiliza para procesos de asignación unitaria.
     *
     * @param productoStockId id del producto_stock.
     * @return lista de unidades disponibles.
     */
    @GetMapping("/producto-stock/{productoStockId}/sin-custodia")
    public List<ProductosInformacion> getActivosSinCustodiaPorProductoStock(@PathVariable Integer productoStockId) {
        return service.obtenerNumerosDeSerieActivosSinCustodia(productoStockId);
    }

    /**
     * Asigna custodia de unidades individuales a un empleado.
     *
     * Actualiza el campo {@code empleadoCustodia}
     * de cada unidad indicada.
     *
     * @param ids lista de ids de unidades.
     * @param legajo legajo del empleado responsable (puede ser null para quitar custodia).
     * @return lista de DTOs con el resultado de la operación.
     */
    @PutMapping("/asignar-custodia")
    public List<ProductosInformacionDto> asignarCustodia(
            @RequestBody List<Integer> ids,
            @RequestParam(value = "legajo", required = false) Long legajo) {
        return service.asignarCustodia(ids, legajo);
    }

    /**
     * Realiza la baja lógica de unidades individuales.
     *
     * Marca el campo {@code activo} en false,
     * sin eliminar físicamente los registros.
     *
     * @param ids lista de ids de unidades a dar de baja.
     * @return lista de unidades actualizadas.
     */
    @PutMapping("/darDeBaja")
    public List<ProductosInformacion> darDeBajaNumerosDeSerie(@RequestBody List<Integer> ids) {
        return service.darDeBajaNumerosDeSerie(ids);
    }

    /**
     * Obtiene una cantidad limitada de unidades activas
     * sin custodia para un producto_stock.
     *
     * Permite excluir ids ya seleccionados para evitar duplicaciones
     * en procesos de asignación progresiva.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima a retornar.
     * @param idsYaElegidos ids a excluir.
     * @return lista de unidades disponibles.
     */
    @PostMapping("/codigos-libres/{idStock}/{cantidad}")
    public ResponseEntity<List<ProductosInformacion>> getCodigosLibres(
            @PathVariable Integer idStock,
            @PathVariable Integer cantidad,
            @RequestBody List<Integer> idsYaElegidos) {

        return ResponseEntity.ok(
                service.obtenerCodigosLibres(idStock, cantidad, idsYaElegidos)
        );
    }

    /**
     * Obtiene una cantidad limitada de unidades activas
     * que actualmente se encuentran en custodia
     * de un empleado específico.
     *
     * Se utiliza para procesos de devolución o transferencia unitaria.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima a retornar.
     * @param legajo legajo del empleado.
     * @param idsYaElegidos ids a excluir.
     * @return lista de unidades en custodia del empleado.
     */
    @PostMapping("/codigos-encustodia/{idStock}/{cantidad}/{legajo}")
    public ResponseEntity<List<ProductosInformacion>> getCodigosLibres(
            @PathVariable Integer idStock,
            @PathVariable Integer cantidad,
            @PathVariable Integer legajo,
            @RequestBody List<Integer> idsYaElegidos) {

        return ResponseEntity.ok(
                service.obtenerCodigosEnCustodiaXEmpleado(idStock, cantidad, legajo, idsYaElegidos)
        );
    }

    /**
     * Obtiene una cantidad limitada de unidades activas
     * para un producto_stock determinado,
     * excluyendo ids específicos.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima a retornar.
     * @param idsYaElegidos ids a excluir.
     * @return lista limitada de unidades activas.
     */
    @PostMapping("/codigos-activos/{idStock}/{cantidad}")
    public ResponseEntity<List<ProductosInformacion>> getCodigosActivos(
            @PathVariable Integer idStock,
            @PathVariable Integer cantidad,
            @RequestBody List<Integer> idsYaElegidos) {

        return ResponseEntity.ok(
                service.obtenerActivosXStockId(idStock, cantidad, idsYaElegidos)
        );
    }
}
