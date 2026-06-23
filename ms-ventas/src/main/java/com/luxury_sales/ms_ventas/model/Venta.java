package com.luxury_sales.ms_ventas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private Long usuarioId;
    private Integer cantidad;
    private Double total;
    private String estadoPago;
    private LocalDateTime fechaVenta;

    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDateTime.now();
    }
}