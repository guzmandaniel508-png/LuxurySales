package com.luxury_sales.ms_bodega.controller;

import com.luxury_sales.ms_bodega.dto.BodegaRequestDTO;
import com.luxury_sales.ms_bodega.dto.BodegaResponseDTO;
import com.luxury_sales.ms_bodega.service.BodegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bodegas")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    @GetMapping
    public ResponseEntity<List<BodegaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(bodegaService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<BodegaResponseDTO> guardar(@RequestBody BodegaRequestDTO requestDTO) {
        BodegaResponseDTO nuevaBodega = bodegaService.guardar(requestDTO);
        return new ResponseEntity<>(nuevaBodega, HttpStatus.CREATED);
    }
}