package com.luxury_sales.ms_ventas.dto;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double totalPagado;
    private String metodoPago;
}