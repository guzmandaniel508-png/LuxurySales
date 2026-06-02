package com.luxury_sales.ms_pagos.controller;

import com.luxury_sales.ms_pagos.dto.PagoRequestDTO;
import com.luxury_sales.ms_pagos.dto.PagoResponseDTO;
import com.luxury_sales.ms_pagos.exception.ResourceNotFoundException;
import com.luxury_sales.ms_pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

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
}