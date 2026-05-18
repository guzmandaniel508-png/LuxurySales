package com.luxury_sales.ms_producto.service;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoResponseDTO> obtenerTodos();
    ProductoResponseDTO guardar(ProductoRequestDTO productoRequestDTO);
}