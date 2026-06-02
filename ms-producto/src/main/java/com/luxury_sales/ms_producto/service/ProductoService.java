package com.luxury_sales.ms_producto.service;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoResponseDTO> obtenerTodos();
    Optional<ProductoResponseDTO> obtenerPorId(Long id);
    ProductoResponseDTO guardar(ProductoRequestDTO requestDTO);
    ProductoResponseDTO actualizar(Long id, ProductoRequestDTO requestDTO);
    void eliminar(Long id);
}