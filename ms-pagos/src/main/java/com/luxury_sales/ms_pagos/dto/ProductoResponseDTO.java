package com.luxury_sales.ms_pagos.dto;

public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String precio;
    private Integer cantidad;

    public ProductoResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
