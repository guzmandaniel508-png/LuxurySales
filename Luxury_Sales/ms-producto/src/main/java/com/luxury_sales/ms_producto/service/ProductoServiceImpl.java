package com.luxury_sales.ms_producto.service;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import com.luxury_sales.ms_producto.model.Producto;
import com.luxury_sales.ms_producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoResponseDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getCantidad()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        // Convertir RequestDTO a Entidad
        Producto producto = new Producto(dto.getNombre(), dto.getPrecio(), dto.getCantidad());
        Producto guardado = productoRepository.save(producto);
        
        // Convertir Entidad guardada a ResponseDTO
        return new ProductoResponseDTO(guardado.getId(), guardado.getNombre(), guardado.getPrecio(), guardado.getCantidad());
    }
}