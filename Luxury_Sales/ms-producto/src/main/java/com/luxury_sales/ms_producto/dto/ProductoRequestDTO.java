package com.luxury_sales.ms_producto.dto;

public class ProductoRequestDTO {

    private String nombre;
    private Double precio;
    private Integer cantidad;

    // Constructor vacío
    public ProductoRequestDTO() {}

    // Constructor completo
    public ProductoRequestDTO(String nombre, Double precio, Integer cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}