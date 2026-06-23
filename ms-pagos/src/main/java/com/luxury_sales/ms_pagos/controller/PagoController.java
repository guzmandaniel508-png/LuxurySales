package com.luxury_sales.ms_pagos.controller;

import com.luxury_sales.ms_pagos.dto.PagoRequestDTO;
import com.luxury_sales.ms_pagos.dto.PagoResponseDTO;
import com.luxury_sales.ms_pagos.exception.ResourceNotFoundException;
import com.luxury_sales.ms_pagos.service.PagoService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return pagoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO> registrarPago(@RequestBody PagoRequestDTO requestDTO) {
        try {
            PagoResponseDTO nuevoPago = pagoService.procesarPago(requestDTO);
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody PagoRequestDTO requestDTO) {
        return pagoService.obtenerPorId(id)
                .map(existente -> ResponseEntity.ok(pagoService.actualizar(id, requestDTO)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}