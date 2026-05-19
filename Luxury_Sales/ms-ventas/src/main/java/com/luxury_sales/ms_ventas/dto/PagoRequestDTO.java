package com.luxury_sales.ms_ventas.dto;

import lombok.Data;

@Data
public class PagoRequestDTO {
    private Long productoId;
    private Integer cantidad;
}