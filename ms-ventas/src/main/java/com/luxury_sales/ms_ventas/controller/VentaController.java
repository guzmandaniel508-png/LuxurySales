package com.luxury_sales.ms_ventas.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.luxury_sales.ms_ventas.dto.VentaRequestDTO;
import com.luxury_sales.ms_ventas.dto.VentaResponseDTO;
import com.luxury_sales.ms_ventas.service.VentaService;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardarVenta(
            @Valid @RequestBody VentaRequestDTO request) {
        VentaResponseDTO nuevaVenta = ventaService.registrarVenta(request);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO request) {
        return ventaService.obtenerPorId(id)
                .map(existente -> ResponseEntity.ok(ventaService.actualizar(id, request)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}