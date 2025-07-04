package com.mcpd.controller;

import com.mcpd.dto.PedidoDeAdquisicionDto;
import com.mcpd.model.PedidoDeAdquisicion;
import com.mcpd.service.PedidoDeAdquisicionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
public class PedidoDeAdquisicionController {

    private final PedidoDeAdquisicionService service;

    public PedidoDeAdquisicionController(PedidoDeAdquisicionService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDeAdquisicionDto> findAll() {
        return service.findAll()
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

    private PedidoDeAdquisicionDto convertToDto(PedidoDeAdquisicion pedido) {
        PedidoDeAdquisicionDto dto = new PedidoDeAdquisicionDto();
        dto.setNumero(pedido.getNumero());
        dto.setFechaSolicitud(pedido.getFechaSolicitud());
        dto.setNombreSolicitante(pedido.getNombreSolicitante());
        dto.setPrioridad(pedido.getPrioridad());
        dto.setPresupuesto(pedido.getPresupuesto());
        dto.setSecretaria(pedido.getSecretaria());
        dto.setDireccion(pedido.getDireccion());
        dto.setObservacion(pedido.getObservacion());
        dto.setAdministracion(pedido.getAdministracion());
        dto.setHacienda(pedido.isHacienda());
        dto.setArchivado(pedido.isArchivado());
        dto.setDespacho(pedido.isDespacho());
        dto.setPresupuestado(pedido.isPresupuestado());
        dto.setNumeroInstrumentoAdquisicion(pedido.getNumeroInstrumentoAdquisicion());
        dto.setDestino(pedido.getDestino());
        dto.setCompleto(pedido.isCompleto());
        dto.setOfertado(pedido.isOfertado());
        dto.setPase(pedido.getPase());
        dto.setObra(pedido.isObra());
        dto.setDirecta(pedido.isDirecta());
        dto.setNota(pedido.getNota());
        dto.setPresentaPre(pedido.isPresentaPre());
        dto.setPresentes(pedido.getPresentes());
        dto.setPañol(pedido.isPañol());
        dto.setMotivoRechazo(pedido.getMotivoRechazo());
        dto.setImputacion(pedido.getImputacion());
        dto.setLegajoSolicitante(pedido.getLegajoSolicitante());
        dto.setHaciendaEmpleado(pedido.getHaciendaEmpleado());
        dto.setHaciendaLegajoEmpleado(pedido.getHaciendaLegajoEmpleado());
        dto.setPañolEmpleado(pedido.getPañolEmpleado());
        dto.setPañolLegajoEmpleado(pedido.getPañolLegajoEmpleado());
        dto.setAdquisicion(pedido.isAdquisicion());
        dto.setNuevoSistema(pedido.isNuevoSistema());
        dto.setUpdated(pedido.getUpdated());
        return dto;
    }
}
