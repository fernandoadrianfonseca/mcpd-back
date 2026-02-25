package com.mcpd.repository;

import com.mcpd.model.ProductosStockFlujo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración para {@link ProductosStockFlujoRepository}.
 *
 * <p>Ejecuta consultas reales contra la BD configurada en el entorno de test.
 * Si querés que sea reproducible 100%, lo ideal es usar DB de test (Testcontainers/H2 + dataset).</p>
 */
@SpringBootTest
class ProductosStockFlujoRepositoryTest {

    @Autowired
    private ProductosStockFlujoRepository repository;

    // ⚠️ Ajustá este legajo a uno que exista en tu DB local de tests
    private static final long LEGAJO_TEST = 248L;

    /**
     * Test Minimo
     */
    @Test
    void testFindPrestamosPendientes() {
        List<ProductosStockFlujo> resultado =
                repository.findPrestamosPendientesDeDevolucionPorLegajo(1L);

        System.out.println("Resultados: " + resultado.size());
    }

    @Test
    @DisplayName("findPrestamosPendientesDeDevolucionPorLegajo: devuelve lista (no null) y no rompe")
    void findPrestamosPendientesDeDevolucionPorLegajo_ok() {
        List<ProductosStockFlujo> resultado =
                repository.findPrestamosPendientesDeDevolucionPorLegajo(LEGAJO_TEST);

        assertNotNull(resultado, "La consulta no debe devolver null");

        // Si no tenés datos cargados puede ser 0; esto igual valida que no rompe.
        System.out.println("Prestamos pendientes: " + resultado.size());

        // Validaciones blandas: si hay datos, deberían cumplir condiciones mínimas
        resultado.forEach(f -> {
            assertNotNull(f.getId(), "Cada flujo debería tener ID");
            assertEquals("custodia_alta", f.getTipo(), "La query solo devuelve custodia_alta");
            assertNotNull(f.getFechaDevolucion(), "Debe tener fechaDevolucion");
            assertEquals(LEGAJO_TEST, f.getEmpleadoCustodia(), "Debe ser del legajo consultado");
            assertNotNull(f.getProductoStock(), "Debe tener productoStock");
            assertNotNull(f.getFecha(), "Debe tener fecha");
            assertNotNull(f.getCantidad(), "Debe tener cantidad");
            assertTrue(f.getCantidad() > 0, "Cantidad debería ser > 0");
        });
    }

    @Test
    @DisplayName("findFlujoDeStockConFechaDevolucionPorLegajo: trae altas/bajas de custodia y ordena por producto y fecha")
    void findFlujoDeStockConFechaDevolucionPorLegajo_ok() {
        List<ProductosStockFlujo> resultado =
                repository.findFlujoDeStockConFechaDevolucionPorLegajo(LEGAJO_TEST);

        assertNotNull(resultado, "La consulta no debe devolver null");
        System.out.println("Flujos con fecha devolución: " + resultado.size());

        // Validar que tipos están dentro del set esperado y legajo ok
        resultado.forEach(f -> {
            assertNotNull(f.getId());
            assertNotNull(f.getTipo());
            assertTrue(
                    "custodia_alta".equals(f.getTipo()) || "custodia_baja".equals(f.getTipo()),
                    "Solo debería devolver custodia_alta/custodia_baja"
            );
            assertEquals(LEGAJO_TEST, f.getEmpleadoCustodia(), "Debe ser del legajo consultado");
            assertNotNull(f.getProductoStock(), "Debe tener productoStock");
            assertNotNull(f.getFecha(), "Debe tener fecha");
        });

        // Si hay al menos 2 registros, validar orden (productoStock.id asc, fecha asc)
        if (resultado.size() >= 2) {
            for (int i = 1; i < resultado.size(); i++) {
                ProductosStockFlujo prev = resultado.get(i - 1);
                ProductosStockFlujo curr = resultado.get(i);

                int prevProd = prev.getProductoStock().getId();
                int currProd = curr.getProductoStock().getId();

                if (prevProd == currProd) {
                    assertFalse(
                            curr.getFecha().before(prev.getFecha()),
                            "Para el mismo producto, la fecha debe ser ascendente"
                    );
                } else {
                    assertTrue(
                            currProd >= prevProd,
                            "productoStock.id debe ser ascendente"
                    );
                }
            }
        }
    }

    @Test
    @DisplayName("findRemitosByProductoStockId: no devuelve registros con remito null/vacío")
    void findRemitosByProductoStockId_ok() {
        // ⚠️ Ajustá el ID a uno existente en tu DB local
        Integer productoStockId = 1;

        List<ProductosStockFlujo> resultado = repository.findRemitosByProductoStockId(productoStockId);

        assertNotNull(resultado, "La consulta no debe devolver null");
        System.out.println("Remitos encontrados: " + resultado.size());

        resultado.forEach(f -> {
            assertEquals("alta", f.getTipo(), "La query solo devuelve tipo alta");
            assertNotNull(f.getRemito(), "Remito no debe ser null");
            assertFalse(f.getRemito().trim().isEmpty(), "Remito no debe ser vacío");
            assertNotNull(f.getProductoStock());
            assertEquals(productoStockId, f.getProductoStock().getId());
        });
    }

    @Test
    @DisplayName("findFlujosAltasYBajasByProductoStockId: filtra por legajo cuando se envía, si no trae todo")
    void findFlujosAltasYBajasByProductoStockId_ok() {
        // ⚠️ Ajustá el ID a uno existente en tu DB local
        Integer productoStockId = 1;

        List<ProductosStockFlujo> sinFiltro = repository.findFlujosAltasYBajasByProductoStockId(productoStockId, null);
        assertNotNull(sinFiltro);

        List<ProductosStockFlujo> conFiltro = repository.findFlujosAltasYBajasByProductoStockId(productoStockId, LEGAJO_TEST);
        assertNotNull(conFiltro);

        System.out.println("Flujos sin filtro: " + sinFiltro.size());
        System.out.println("Flujos con filtro: " + conFiltro.size());

        // Si hay resultados filtrados, validar legajo
        conFiltro.forEach(f -> assertEquals(LEGAJO_TEST, f.getEmpleadoCustodia(), "Debe filtrar por legajo"));

        // Validar tipos permitidos
        sinFiltro.forEach(f -> assertTrue(
                List.of("alta", "baja", "custodia_alta", "custodia_baja").contains(f.getTipo()),
                "Tipo debe ser uno de alta/baja/custodia_alta/custodia_baja"
        ));
    }

    @Test
    @DisplayName("findCustodiasPorProducto: devuelve solo custodias vigentes (totalLegajoCustodia > 0) y último por empleado")
    void findCustodiasPorProducto_ok() {
        // ⚠️ Ajustá el ID a uno existente en tu DB local
        Integer productoStockId = 1;

        List<ProductosStockFlujo> resultado = repository.findCustodiasPorProducto(productoStockId);

        assertNotNull(resultado);
        System.out.println("Custodias vigentes: " + resultado.size());

        // Validar constraints básicas de la query
        resultado.forEach(f -> {
            assertNotNull(f.getTipo());
            assertTrue(
                    "custodia_alta".equals(f.getTipo()) || "custodia_baja".equals(f.getTipo()),
                    "Solo debería devolver custodia_alta/custodia_baja"
            );
            assertNotNull(f.getProductoStock());
            assertEquals(productoStockId, f.getProductoStock().getId());
            assertNotNull(f.getEmpleadoCustodia());
            assertNotNull(f.getFecha());
            assertNotNull(f.getTotalLegajoCustodia());
            assertTrue(f.getTotalLegajoCustodia() > 0, "totalLegajoCustodia debe ser > 0");
        });

        // Validar "último por empleado" (no debería repetirse empleadoCustodia)
        long distinctLegajos = resultado.stream()
                .map(ProductosStockFlujo::getEmpleadoCustodia)
                .distinct()
                .count();

        assertEquals(distinctLegajos, resultado.size(), "No debería haber más de un registro por empleadoCustodia");
    }

    @Test
    @DisplayName("findUltimoFlujoCustodia: trae el más reciente por producto y empleado")
    void findUltimoFlujoCustodia_ok() {
        // ⚠️ Ajustá IDs a valores existentes en tu DB local
        Integer productoStockId = 1;
        Long empleadoCustodia = LEGAJO_TEST;

        ProductosStockFlujo ultimo = repository.findUltimoFlujoCustodia(productoStockId, empleadoCustodia);

        // Puede ser null si no hay datos: lo aceptamos
        if (ultimo == null) {
            System.out.println("No hay flujo de custodia para producto=" + productoStockId + " empleado=" + empleadoCustodia);
            return;
        }

        assertNotNull(ultimo.getId());
        assertNotNull(ultimo.getFecha());
        assertNotNull(ultimo.getTipo());
        assertTrue(
                "custodia_alta".equals(ultimo.getTipo()) || "custodia_baja".equals(ultimo.getTipo()),
                "Tipo debe ser custodia_alta/custodia_baja"
        );
        assertNotNull(ultimo.getProductoStock());
        assertEquals(productoStockId, ultimo.getProductoStock().getId());
        assertEquals(empleadoCustodia, ultimo.getEmpleadoCustodia());
    }
}