package com.luxury_sales.ms_ventas.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String precio;
    private Integer cantidad;
}