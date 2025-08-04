package com.mcpd.controller;

import com.mcpd.dto.PedidoDeAdquisicionDto;
import com.mcpd.model.OrdenDeCompraAdquisicion;
import com.mcpd.model.PedidoDeAdquisicion;
import com.mcpd.model.ProveedorFactura;
import com.mcpd.repository.FacturaRepository;
import com.mcpd.repository.OrdenDeCompraAdquisicionRepository;
import com.mcpd.repository.OrdenDePagoRepository;
import com.mcpd.service.PedidoDeAdquisicionDetalleService;
import com.mcpd.service.PedidoDeAdquisicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoDeAdquisicionController {

    @Autowired
    private OrdenDeCompraAdquisicionRepository ordenCompraRepository;

    @Autowired
    private OrdenDePagoRepository ordenDePagoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    PedidoDeAdquisicionDetalleService detalleService;

    private final PedidoDeAdquisicionService service;

    public PedidoDeAdquisicionController(PedidoDeAdquisicionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDeAdquisicionDto> findAll() {
        List<Long> pedidosConStock = service.findPedidosConStockDisponible()
                .stream()
                .map(row -> ((Number) row[1]).longValue())
                .toList();

        return service.findPedidosConDetalles()
                .stream()
                .map(dto -> convertToDto(dto, pedidosConStock))
                .toList();
    }

    @GetMapping("/con-stock-disponible")
    public List<PedidoDeAdquisicionDto> getPedidosConStockDisponible() {
        List<Object[]> resultados = service.findPedidosConStockDisponible();

        // Extraer lista de números de pedido con stock
        List<Long> pedidosConStock = resultados.stream()
                .map(row -> ((Number) row[1]).longValue()) // row[1] es el número
                .toList();

        return resultados.stream()
                .map(row -> convertToDto(row, pedidosConStock))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDeAdquisicion> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PedidoDeAdquisicion create(@RequestBody PedidoDeAdquisicion pedido) {
        return service.save(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDeAdquisicion> update(@PathVariable Long id, @RequestBody PedidoDeAdquisicion pedido) {

        detalleService.eliminarDetallesPorPedidoId(id);
        return service.findById(id).map(existing -> {
            pedido.setNumero(id);
            if (pedido.getDetalles() == null) {
                pedido.setDetalles(new ArrayList<>());
            }
            PedidoDeAdquisicion actualizado = service.save(pedido);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/autorizar")
    public ResponseEntity<?> actualizarAutorizacion(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        String tipo = (String) payload.get("tipo");
        Boolean valor = Boolean.valueOf(payload.get("valor").toString());

        return service.findById(id).map(pedido -> {
            if ("hacienda".equalsIgnoreCase(tipo)) {
                pedido.setHacienda(valor);
            } else if ("pañol".equalsIgnoreCase(tipo)) {
                pedido.setPañol(valor);
            } else {
                return ResponseEntity.badRequest().body("Tipo de autorización inválido");
            }

            service.save(pedido);
            return ResponseEntity.ok().build();

        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/archivar/{numero}")
    public void archivarPedido(@PathVariable Long numero) {
        service.archivarPedido(numero);
    }

    @PutMapping("/entregar/{numero}")
    public void entregarPedido(@PathVariable Long numero) {
        service.entregarPedido(numero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private PedidoDeAdquisicionDto convertToDto(Object[] row, List<Long> pedidosConStock) {

        PedidoDeAdquisicionDto dto = new PedidoDeAdquisicionDto();

        dto.setOrigen((String) row[0]);
        dto.setNumero((Long) row[1]);
        dto.setFechaSolicitud((Date) row[2]);
        dto.setNombreSolicitante((String) row[3]);
        dto.setPresupuesto((Double) row[4]);
        dto.setSecretaria((String) row[5]);
        dto.setDireccion((String) row[6]);
        dto.setPartida((String) row[7]);
        dto.setDestino((String) row[8]);
        dto.setObservacion((String) row[9]);
        dto.setHacienda((Boolean) row[10]);
        dto.setArchivado((Boolean) row[11]);
        dto.setDespacho((Boolean) row[12]);
        dto.setNumeroInstrumentoAdquisicion((String) row[13]);
        dto.setOc((String) row[14]);
        dto.setRemito((String) row[15]);
        dto.setFactura((String) row[16]);
        dto.setResolucion((String) row[17]);
        dto.setOp((String) row[18]);
        dto.setNombreFantasia((String) row[19]);
        dto.setNombre((String) row[20]);
        dto.setCuit(row[21] != null ? String.valueOf(row[21]) : null);
        dto.setTotalFactura(row[22] != null ? new BigDecimal(row[22].toString()) : null);
        dto.setAdquisicion((Boolean) row[23]);
        dto.setNuevoSistema((Boolean) row[24]);
        dto.setPañol((Boolean) row[25]);
        dto.setEntregado((Boolean) row[26]);

        String estado = getEstadoString(dto, pedidosConStock);

        dto.setEstado(estado);

        return dto;
    }

    private static String getEstadoString(PedidoDeAdquisicionDto dto, List<Long> pedidosConStockDisponible) {
        if (!dto.isAdquisicion()) {
            boolean tieneStockCompleto = pedidosConStockDisponible.contains(dto.getNumero());

            if (dto.isArchivado() && dto.isEntregado()) {
                return "Interno: Archivado Con Stock Entregado";
            } else if (dto.isArchivado() && !dto.isEntregado()) {
                return "Interno: Archivado";
            } else if (dto.isHacienda() && !tieneStockCompleto) {
                return "Interno: Sin Stock";
            } else if (dto.isHacienda() && tieneStockCompleto) {
                return "Interno: Listo Para Entrega";
            } else if (dto.isHacienda()) {
                return "Interno: Autorizado Por Hacienda";
            } else if (dto.isPañol()) {
                return "Interno: Autorizado Por Pañol";
            } else {
                return "Interno: Pendiente";
            }
        }

        // Lógica para adquisición == true (como estaba antes)
        String estado = "Pendiente";

        if (dto.isHacienda() && !dto.isArchivado() && dto.getNumeroInstrumentoAdquisicion() == null) {
            estado = "En Proceso De Presupuesto";
        } else if (!dto.isHacienda() && dto.isArchivado()) {
            estado = "Rechazados";
        } else if (dto.isHacienda() && dto.getNumeroInstrumentoAdquisicion() != null && dto.getOc() == null) {
            estado = "En Despacho";
        } else if (dto.isHacienda() && dto.getNumeroInstrumentoAdquisicion() != null && dto.getOc() != null && dto.getFactura() == null) {
            estado = "Con Orden De Compra";
        } else if (dto.isHacienda() && dto.getNumeroInstrumentoAdquisicion() != null && dto.getFactura() != null && dto.getOp() == null) {
            estado = "Facturada";
        } else if (dto.isHacienda() && dto.getNumeroInstrumentoAdquisicion() != null && dto.getFactura() != null && dto.getOp() != null) {
            estado = "Pagado";
        }

        return estado;
    }
}
