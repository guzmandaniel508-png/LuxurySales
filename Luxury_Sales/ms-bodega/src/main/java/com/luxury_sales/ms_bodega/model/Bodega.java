package com.luxury_sales.ms_bodega.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bodegas")
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "producto_id", nullable = false)
    private Long productoId; // Relación lógica por ID con ms-producto

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private String pasillo;

    @Column(nullable = false)
    private String estante;

    // Construtores
    public Bodega() {}

    public Bodega(Long productoId, Integer cantidad, String pasillo, String estante) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.pasillo = pasillo;
        this.estante = estante;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getPasillo() { return pasillo; }
    public void setPasillo(String pasillo) { this.pasillo = pasillo; }

    public String getEstante() { return estante; }
    public void setEstante(String estante) { this.estante = estante; }
}