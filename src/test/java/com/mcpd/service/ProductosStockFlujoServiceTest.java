package com.mcpd.service;

import com.mcpd.dto.PrestamoPendienteDto;
import com.mcpd.model.ProductosStock;
import com.mcpd.model.ProductosStockFlujo;
import com.mcpd.model.Producto;
import com.mcpd.model.ProductosCategoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Integración REAL del service contra JPA/DB.
 * - Levanta contexto completo (SpringBootTest)
 * - Usa DB real/config de test
 * - @Transactional -> rollback al terminar cada test
 */
@SpringBootTest
@Transactional
class ProductosStockFlujoServiceTest {

    @Autowired
    private ProductosStockFlujoService service;

    @Autowired
    private EntityManager em;

    // --------------------------
    // Helpers de datos reales
    // --------------------------

    private ProductosCategoria anyCategoria() {
        TypedQuery<ProductosCategoria> q = em.createQuery(
                "SELECT c FROM ProductosCategoria c", ProductosCategoria.class
        ).setMaxResults(1);

        List<ProductosCategoria> out = q.getResultList();
        assumeTrue(!out.isEmpty(), "No hay ProductosCategoria en la DB. Creá una o cargá datos de test.");
        return out.get(0);
    }

    private Producto anyProducto() {
        TypedQuery<Producto> q = em.createQuery(
                "SELECT p FROM Producto p", Producto.class
        ).setMaxResults(1);

        List<Producto> out = q.getResultList();
        assumeTrue(!out.isEmpty(), "No hay Producto en la DB. Creá uno o cargá datos de test.");
        return out.get(0);
    }

    private ProductosStock crearProductoStock(String tipo, boolean consumible, boolean conDevolucion) {
        ProductosCategoria cat = anyCategoria();
        Producto prod = anyProducto();

        ProductosStock ps = new ProductosStock();
        ps.setCategoria(cat);
        ps.setCategoriaNombre(cat.toString()); // si tenés getNombre(), cambialo por cat.getNombre()
        ps.setProducto(prod);
        ps.setProductoNombre(prod.toString()); // si tenés getNombre(), cambialo por prod.getNombre()

        ps.setCantidad(100);
        ps.setCantidadCustodia(0);

        ps.setTipo(tipo);
        ps.setConsumible(consumible);
        ps.setConDevolucion(conDevolucion);

        em.persist(ps);
        em.flush();
        return ps;
    }

    private ProductosStockFlujo crearFlujo(
            ProductosStock ps,
            String tipo,
            long cantidad,
            Long empleadoCustodia,
            long empleadoCarga,
            String remito,
            Date fechaDevolucion,
            Long totalLegajoCustodia
    ) {
        ProductosStockFlujo f = new ProductosStockFlujo();
        f.setProductoStock(ps);
        f.setTipo(tipo);
        f.setCantidad(cantidad);

        // Campo obligatorio nullable=false
        f.setEmpleadoCarga(empleadoCarga);

        // total obligatorio nullable=false (en tu entity)
        f.setTotal(ps.getCantidad().longValue());

        f.setEmpleadoCustodia(empleadoCustodia);
        f.setRemito(remito);
        f.setFechaDevolucion(fechaDevolucion);
        f.setTotalLegajoCustodia(totalLegajoCustodia);

        em.persist(f);
        em.flush();
        return f;
    }

    private static Date minutesFrom(Date base, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(base);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    private static Date daysFromNow(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    // --------------------------
    // Tests del Service (TODOS)
    // --------------------------

    @Test
    void findAll_devuelveLista() {
        ProductosStock ps = crearProductoStock("Test", false, false);
        crearFlujo(ps, "alta", 10, null, 999L, "REM-1", null, null);

        List<ProductosStockFlujo> out = service.findAll();
        assertNotNull(out);
        assertTrue(out.size() >= 1);
    }

    @Test
    void findById_ok() {
        ProductosStock ps = crearProductoStock("Test", false, false);
        ProductosStockFlujo f = crearFlujo(ps, "alta", 10, null, 999L, null, null, null);

        Optional<ProductosStockFlujo> out = service.findById(f.getId());
        assertTrue(out.isPresent());
        assertEquals(f.getId(), out.get().getId());
    }

    @Test
    void save_ok() {
        ProductosStock ps = crearProductoStock("Test", false, false);

        ProductosStockFlujo f = new ProductosStockFlujo();
        f.setProductoStock(ps);
        f.setTipo("alta");
        f.setCantidad(7L);
        f.setEmpleadoCarga(999L);
        f.setTotal(ps.getCantidad().longValue());

        ProductosStockFlujo saved = service.save(f);
        assertNotNull(saved.getId());
    }

    @Test
    void deleteById_ok() {
        ProductosStock ps = crearProductoStock("Test", false, false);
        ProductosStockFlujo f = crearFlujo(ps, "alta", 10, null, 999L, null, null, null);

        Integer id = f.getId();
        service.deleteById(id);

        Optional<ProductosStockFlujo> out = service.findById(id);
        assertTrue(out.isEmpty());
    }

    @Test
    void findByProductoStockId_ok() {
        ProductosStock ps = crearProductoStock("Test", false, false);
        crearFlujo(ps, "alta", 10, null, 999L, null, null, null);
        crearFlujo(ps, "baja", 2, null, 999L, null, null, null);

        List<ProductosStockFlujo> out = service.findByProductoStockId(ps.getId());
        assertNotNull(out);
        assertTrue(out.size() >= 2);
    }

    @Test
    void findRemitosByProductoStockId_ok() {
        ProductosStock ps = crearProductoStock("Test", false, false);

        crearFlujo(ps, "alta", 10, null, 999L, "REM-OK", null, null);
        crearFlujo(ps, "alta", 5, null, 999L, "", null, null);      // no debería
        crearFlujo(ps, "alta", 3, null, 999L, null, null, null);     // no debería
        crearFlujo(ps, "baja", 1, null, 999L, "REM-NO", null, null); // no debería (tipo)

        List<ProductosStockFlujo> out = service.findRemitosByProductoStockId(ps.getId());
        assertNotNull(out);

        assertTrue(out.stream().allMatch(f -> "alta".equals(f.getTipo())));
        assertTrue(out.stream().allMatch(f -> f.getRemito() != null && !f.getRemito().isBlank()));
    }

    @Test
    void findFlujosAltasYBajasByProductoStockId_filtraPorLegajo() {
        ProductosStock ps = crearProductoStock("Test", false, true);

        long legajo261 = 261L;
        long legajo999 = 999L;

        crearFlujo(ps, "alta", 10, null, 999L, "R", null, null);
        crearFlujo(ps, "custodia_alta", 2, legajo261, 999L, null, daysFromNow(5), 2L);
        crearFlujo(ps, "custodia_baja", 1, legajo999, 999L, null, null, 1L);

        List<ProductosStockFlujo> out = service.findFlujosAltasYBajasByProductoStockId(ps.getId(), legajo261);

        assertNotNull(out);
        // La query deja pasar también alta/baja sin empleadoCustodia
        assertTrue(out.stream().allMatch(f ->
                f.getEmpleadoCustodia() == null || f.getEmpleadoCustodia().equals(legajo261)
        ));
    }

    @Test
    void findCustodiasPorProducto_devuelveSoloVigentes() {
        ProductosStock ps = crearProductoStock("Test", false, true);

        long legajo261 = 261L;
        long legajo777 = 777L;

        // Legajo 261: último flujo deja totalLegajoCustodia > 0 => debe aparecer
        crearFlujo(ps, "custodia_alta", 5, legajo261, 999L, null, daysFromNow(3), 5L);
        crearFlujo(ps, "custodia_baja", 2, legajo261, 999L, null, null, 3L);

        // Legajo 777: último flujo deja 0 => NO debe aparecer
        crearFlujo(ps, "custodia_alta", 2, legajo777, 999L, null, daysFromNow(3), 2L);
        crearFlujo(ps, "custodia_baja", 2, legajo777, 999L, null, null, 0L);

        List<ProductosStockFlujo> out = service.findCustodiasPorProducto(ps.getId());

        assertNotNull(out);
        assertTrue(out.stream().allMatch(f -> f.getTotalLegajoCustodia() != null && f.getTotalLegajoCustodia() > 0));
        assertTrue(out.stream().anyMatch(f -> Objects.equals(f.getEmpleadoCustodia(), legajo261)));
        assertTrue(out.stream().noneMatch(f -> Objects.equals(f.getEmpleadoCustodia(), legajo777)));
    }

    @Test
    void getPrestamosPendientesPorLegajo_descuentaBajas_y_seteaEstado() {
        long legajo = 261L;

        ProductosStock ps1 = crearProductoStock("Test", false, true);
        ProductosStock ps2 = crearProductoStock("Test", false, true);

        Date base = new Date();

        // Producto 1:
        // Alta 10 con devolución futura
        // Baja 4 posterior => pendiente 6 (A TIEMPO)
        Date alta1Fecha = minutesFrom(base, -60);
        Date baja1Fecha = minutesFrom(base, -30);
        Date devolucionFutura = daysFromNow(3);

        // Forzamos fechas usando setFecha (porque tu service compara fechas)
        ProductosStockFlujo alta1 = crearFlujo(ps1, "custodia_alta", 10, legajo, 999L, null, devolucionFutura, 10L);
        alta1.setFecha(alta1Fecha);
        em.merge(alta1);

        ProductosStockFlujo baja1 = crearFlujo(ps1, "custodia_baja", 4, legajo, 999L, null, null, 6L);
        baja1.setFecha(baja1Fecha);
        em.merge(baja1);

        // Producto 2:
        // Alta 5 con devolución pasada
        // Baja 5 posterior => pendiente 0 => NO debe aparecer
        Date alta2Fecha = minutesFrom(base, -120);
        Date baja2Fecha = minutesFrom(base, -90);
        Date devolucionPasada = daysFromNow(-2);

        ProductosStockFlujo alta2 = crearFlujo(ps2, "custodia_alta", 5, legajo, 999L, null, devolucionPasada, 5L);
        alta2.setFecha(alta2Fecha);
        em.merge(alta2);

        ProductosStockFlujo baja2 = crearFlujo(ps2, "custodia_baja", 5, legajo, 999L, null, null, 0L);
        baja2.setFecha(baja2Fecha);
        em.merge(baja2);

        em.flush();

        List<PrestamoPendienteDto> out = service.getPrestamosPendientesPorLegajo(legajo);

        assertNotNull(out);
        assertEquals(1, out.size(), "Debe quedar 1 pendiente (producto 1)");

        PrestamoPendienteDto dto = out.get(0);
        assertNotNull(dto.getFlujo());
        assertEquals(6L, dto.getCantidadPendiente());
        assertEquals("A TIEMPO", dto.getEstadoDevolucion());
        assertNotNull(dto.getFechaDevolucion());
    }
}