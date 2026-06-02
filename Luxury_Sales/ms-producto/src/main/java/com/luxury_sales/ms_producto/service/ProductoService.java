package com.luxury_sales.ms_producto.service;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import com.luxury_sales.ms_producto.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoResponseDTO> obtenerTodos();
    ProductoResponseDTO guardar(ProductoRequestDTO requestDTO);
    Optional<Producto> obtenerPorId(Long id);
}