package com.luxury_sales.ms_ventas.dto;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private Long id;
    private String estado; // Ejemplo: APROBADO, RECHAZADO
}