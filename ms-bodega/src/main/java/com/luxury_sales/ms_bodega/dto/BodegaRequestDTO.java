package com.luxury_sales.ms_bodega.dto;

public class BodegaRequestDTO {

    private Long productoId;
    private Integer cantidad;
    private String pasillo;
    private String estante;

    public BodegaRequestDTO() {}

    public BodegaRequestDTO(Long productoId, Integer cantidad, String pasillo, String estante) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.pasillo = pasillo;
        this.estante = estante;
    }

    
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getPasillo() { return pasillo; }
    public void setPasillo(String pasillo) { this.pasillo = pasillo; }

    public String getEstante() { return estante; }
    public void setEstante(String estante) { this.estante = estante; }
}