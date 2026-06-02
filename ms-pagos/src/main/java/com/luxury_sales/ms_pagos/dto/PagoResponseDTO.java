package com.luxury_sales.ms_pagos.dto;

public class PagoResponseDTO {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double totalPagado;
    private String metodoPago;

   
    public PagoResponseDTO() {
    }

    public PagoResponseDTO(Long id, Long productoId, Integer cantidad, Double totalPagado, String metodoPago) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.totalPagado = totalPagado;
        this.metodoPago = metodoPago;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getTotalPagado() { return totalPagado; }
    public void setTotalPagado(Double totalPagado) { this.totalPagado = totalPagado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}