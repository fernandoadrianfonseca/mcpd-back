package com.mcpd.controller;

import com.mcpd.model.PedidosDestino;
import com.mcpd.model.PedidosDireccion;
import com.mcpd.model.PedidosSecretaria;
import com.mcpd.service.PedidosDestinoService;
import com.mcpd.service.PedidosDireccionService;
import com.mcpd.service.PedidosSecretariaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/opciones")
@CrossOrigin(origins = "*")
public class PedidosOpcionesController {

    private final PedidosDestinoService destinoService;
    private final PedidosDireccionService direccionService;
    private final PedidosSecretariaService secretariaService;

    public PedidosOpcionesController(PedidosDestinoService destinoService,
                                     PedidosDireccionService direccionService,
                                     PedidosSecretariaService secretariaService) {
        this.destinoService = destinoService;
        this.direccionService = direccionService;
        this.secretariaService = secretariaService;
    }

    @GetMapping("/destinos")
    public List<PedidosDestino> getDestinos() {
        return destinoService.findAll();
    }

    @GetMapping("/direcciones")
    public List<PedidosDireccion> getDirecciones() {
        return direccionService.findAll();
    }

    @GetMapping("/secretarias")
    public List<PedidosSecretaria> getSecretarias() {
        return secretariaService.findAll();
    }
}
