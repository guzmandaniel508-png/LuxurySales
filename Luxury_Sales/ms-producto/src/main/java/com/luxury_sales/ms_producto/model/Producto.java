package com.luxury_sales.ms_producto.model;

import jakarta.persistence.*;

@Entity  // cuando aparece el entiry es por que esta clase va tener una tabla. 
@Table(name = "productos")
public class Producto {

    @Id  //  indica que es la llave primaria y no puede repetirse 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // bgenerame una identidad 
    private Long id;

    private String nombre;
    private Double precio;
    private Integer cantidad;

    // Constructor vacío obligatorio para JPA
    public Producto() {}

    // Constructor completo
    public Producto(String nombre, Double precio, Integer cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}