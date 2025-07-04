package com.mcpd.controller;

import com.mcpd.dto.ComprasImputacionDto;
import com.mcpd.service.ComprasService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class ComprasController {

    private final ComprasService service;

    public ComprasController(ComprasService service) {
        this.service = service;
    }

    @GetMapping("/imputaciones")
    public List<ComprasImputacionDto> obtenerImputaciones() {
        return service.obtenerImputaciones();
    }
}
