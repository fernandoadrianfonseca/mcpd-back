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

/**
 * Servicio de dominio para la gestión de unidades individuales
 * de inventario ({@link ProductosInformacion}).
 *
 * <p>
 * Este servicio administra:
 * <ul>
 *   <li>Consulta de bienes unitarios por producto o flujo</li>
 *   <li>Asignación y reasignación de custodia por número de serie</li>
 *   <li>Validación de unicidad (número de serie y código antiguo)</li>
 *   <li>Baja lógica de unidades</li>
 *   <li>Selección controlada de códigos disponibles</li>
 * </ul>
 *
 * Esta capa opera sobre la trazabilidad unitaria del inventario,
 * complementando el modelo consolidado de {@link com.mcpd.model.ProductosStock}
 * y el historial contable de {@link com.mcpd.model.ProductosStockFlujo}.
 *
 * Las operaciones que modifican custodia o estado se ejecutan de manera transaccional.
 */

@Service
public class ProductosInformacionService {

    private final ProductosInformacionRepository repository;

    private final EmpleadoRepository empleadoRepository;

    public ProductosInformacionService(ProductosInformacionRepository repository, EmpleadoRepository empleadoRepository) {
        this.repository = repository;
        this.empleadoRepository = empleadoRepository;
    }

    /**
     * Obtiene todas las unidades registradas en el sistema.
     */
    public List<ProductosInformacion> findAll() {
        return repository.findAll();
    }

    /**
     * Obtiene una unidad individual por su id.
     *
     * @param id identificador de la unidad.
     * @return Optional con la unidad si existe.
     */
    public Optional<ProductosInformacion> findById(Integer id) {
        return repository.findById(id);
    }

    /**
     * Guarda o actualiza una unidad individual.
     *
     * @param entity entidad a persistir.
     * @return entidad persistida.
     */
    public ProductosInformacion save(ProductosInformacion entity) {
        return repository.save(entity);
    }

    /**
     * Elimina una unidad por id.
     *
     * @param id identificador de la unidad.
     */
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Obtiene todas las unidades asociadas a un movimiento específico
     * de alta en {@link com.mcpd.model.ProductosStockFlujo}.
     *
     * @param flujoId id del movimiento de flujo.
     * @return lista de unidades creadas en ese movimiento.
     */
    public List<ProductosInformacion> findByProductoFlujoId(Integer flujoId) {
        return repository.findByProductoFlujo_Id(flujoId);
    }

    /**
     * Obtiene unidades por productoStock con filtros opcionales.
     *
     * Permite:
     * - Filtrar por estado activo/inactivo
     * - Filtrar por empleado en custodia
     *
     * @param productoStockId id del producto_stock.
     * @param activo indica si se filtran solo activos.
     * @param empleadoCustodiaId legajo del empleado (opcional).
     * @return lista de unidades filtradas.
     */
    public List<ProductosInformacion> findByProductoStockId(Integer productoStockId, Boolean activo, Long empleadoCustodiaId) {
        return repository.findByProductoStockIdOpcionalActivoYEmpleado(productoStockId, activo, empleadoCustodiaId);
    }

    /**
     * Guarda múltiples unidades validando previamente
     * que no existan duplicados activos de:
     * - Número de serie
     * - Código antiguo
     *
     * Si se detecta duplicado activo, se lanza RuntimeException.
     *
     * @param lista lista de unidades a persistir.
     * @return lista persistida.
     */
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

    /**
     * Obtiene unidades activas de un productoStock
     * que actualmente no poseen empleado asignado.
     *
     * Se utiliza para procesos de asignación unitaria.
     *
     * @param productoStockId id del producto_stock.
     * @return lista de unidades disponibles.
     */
    public List<ProductosInformacion> obtenerNumerosDeSerieActivosSinCustodia(Integer productoStockId) {
        return repository.findActivosSinCustodiaPorProductoStock(productoStockId);
    }

    /**
     * Asigna custodia de unidades individuales a un empleado.
     *
     * <p>
     * Actualiza el campo {@code empleadoCustodia} de cada unidad indicada.
     * No genera movimientos en ProductosStockFlujo (esa responsabilidad
     * corresponde al servicio de stock consolidado).
     *
     * Devuelve un DTO reducido con:
     * - id
     * - número de serie
     * - legajo asignado
     *
     * @param ids ids de las unidades a asignar.
     * @param legajo legajo del empleado responsable.
     * @return lista de DTOs con el resultado de la asignación.
     */
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

    /**
     * Realiza la baja lógica de unidades individuales.
     *
     * <p>
     * Marca el campo {@code activo} en false,
     * sin eliminar físicamente el registro.
     *
     * @param ids ids de las unidades a dar de baja.
     * @return lista de unidades actualizadas.
     */
    @Transactional
    public List<ProductosInformacion> darDeBajaNumerosDeSerie(List<Integer> ids) {
        List<ProductosInformacion> series = repository.findAllById(ids);
        for (ProductosInformacion serie : series) {
            serie.setActivo(false);
        }
        return repository.saveAll(series);
    }

    /**
     * Obtiene una cantidad limitada de unidades activas
     * sin custodia para un producto_stock determinado.
     *
     * Permite excluir ids ya seleccionados para evitar duplicación
     * en procesos de asignación progresiva.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima a obtener.
     * @param idsYaElegidos ids a excluir (si null o vacío se usa valor dummy).
     * @return lista de unidades disponibles.
     */
    public List<ProductosInformacion> obtenerCodigosLibres(Integer idStock, Integer cantidad, List<Integer> idsYaElegidos) {

        // Si viene null o vacía → usar un valor imposible
        if (idsYaElegidos == null || idsYaElegidos.isEmpty()) {
            idsYaElegidos = List.of(0);
        }

        return repository.findCodigosLibres(idStock, cantidad, idsYaElegidos);
    }

    /**
     * Obtiene unidades activas para un producto_stock,
     * limitando la cantidad y excluyendo ids específicos.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima a obtener.
     * @param idsYaElegidos ids a excluir.
     * @return lista limitada de unidades activas.
     */
    public List<ProductosInformacion> obtenerActivosXStockId(Integer idStock, Integer cantidad, List<Integer> idsYaElegidos) {

        // Si viene null o vacía → usar un valor imposible
        if (idsYaElegidos == null || idsYaElegidos.isEmpty()) {
            idsYaElegidos = List.of(0);
        }

        return repository.findActivosXStockId(idStock, cantidad, idsYaElegidos);
    }

    /**
     * Obtiene una cantidad limitada de unidades activas
     * que se encuentran en custodia de un empleado específico.
     *
     * Se utiliza en procesos de devolución o transferencia unitaria.
     *
     * @param idStock id del producto_stock.
     * @param cantidad cantidad máxima a obtener.
     * @param legajo legajo del empleado.
     * @param idsYaElegidos ids a excluir.
     * @return lista limitada de unidades en custodia.
     */
    public List<ProductosInformacion> obtenerCodigosEnCustodiaXEmpleado(Integer idStock, Integer cantidad, Integer legajo, List<Integer> idsYaElegidos) {

        // Si viene null o vacía → usar un valor imposible
        if (idsYaElegidos == null || idsYaElegidos.isEmpty()) {
            idsYaElegidos = List.of(0);
        }

        return repository.findCodigosEnCustodiaXEmpleado(idStock, cantidad, legajo, idsYaElegidos);
    }
}
