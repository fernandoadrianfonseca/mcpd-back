package com.mcpd.controller;

import com.mcpd.dto.PedidoDeAdquisicionDto;
import com.mcpd.model.OrdenDeCompraAdquisicion;
import com.mcpd.model.PedidoDeAdquisicion;
import com.mcpd.model.ProveedorFactura;
import com.mcpd.repository.FacturaRepository;
import com.mcpd.repository.OrdenDeCompraAdquisicionRepository;
import com.mcpd.repository.OrdenDePagoRepository;
import com.mcpd.service.PedidoDeAdquisicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    private final PedidoDeAdquisicionService service;

    public PedidoDeAdquisicionController(PedidoDeAdquisicionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDeAdquisicionDto> findAll() {
        return service.findPedidosConDetalles()
                .stream()
                .map(this::convertToDto)
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
        return service.findById(id).map(existing -> {
            pedido.setNumero(id);
            return ResponseEntity.ok(service.save(pedido));
        }).orElse(ResponseEntity.notFound().build());
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

    private PedidoDeAdquisicionDto convertToDto(Object[] row) {

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

        String estado = getEstadoString(dto);

        dto.setEstado(estado);

        return dto;
    }

    private static String getEstadoString(PedidoDeAdquisicionDto dto) {
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
