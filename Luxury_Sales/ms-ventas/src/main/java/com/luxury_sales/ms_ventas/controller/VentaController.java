package com.luxury_sales.ms_ventas.controller;

import com.luxury_sales.ms_ventas.dto.VentaRequestDTO;
import com.luxury_sales.ms_ventas.dto.VentaResponseDTO;
import com.luxury_sales.ms_ventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@Valid @RequestBody VentaRequestDTO requestDTO) {
        VentaResponseDTO nuevaVenta = ventaService.registrarVenta(requestDTO);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }
}