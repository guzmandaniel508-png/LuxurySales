package com.luxury_sales.ms_ventas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VentaResponseDTO {
    private Long id;
    private Long productoId;
    private Long usuarioId;
    private Integer cantidad;
    private Double total;
    private String estadoPago;
    private LocalDateTime fechaVenta;
    private String mensaje;
}