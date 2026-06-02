package com.luxury_sales.ms_bodega.dto;

public class BodegaResponseDTO {

    private Long id;
    private Long productoId;
    private Integer cantidad;
    private String pasillo;
    private String estante;

    
    public BodegaResponseDTO() {}

    public BodegaResponseDTO(Long id, Long productoId, Integer cantidad, String pasillo, String estante) {
        this.id = id;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.pasillo = pasillo;
        this.estante = estante;
    }

    
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