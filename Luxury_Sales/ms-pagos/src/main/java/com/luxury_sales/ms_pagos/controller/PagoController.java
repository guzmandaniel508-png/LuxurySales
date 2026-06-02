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
            // Procesamos el pago y obtenemos el DTO limpio de respuesta
            PagoResponseDTO nuevoPago = pagoService.procesarPago(requestDTO);
            
            // Retornamos un estado 201 Created con el objeto estructurado
            return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
            
        } catch (ResourceNotFoundException e) {
            // Si el WebClient no encuentra el producto, mandamos un 404 limpio
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        } catch (Exception e) {
            // Ante cualquier otro error inesperado en la base de datos, mandamos un 500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}