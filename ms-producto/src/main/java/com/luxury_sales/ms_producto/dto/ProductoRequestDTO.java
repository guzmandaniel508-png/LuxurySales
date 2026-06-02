package com.luxury_sales.ms_producto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")

    private String nombre;

    @NotBlank(message = "El precio no puede estar vacío")

    private String precio;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
 
    private Integer cantidad;
}