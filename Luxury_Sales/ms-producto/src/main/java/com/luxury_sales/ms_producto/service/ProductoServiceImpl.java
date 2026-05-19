package com.luxury_sales.ms_producto.service;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import com.luxury_sales.ms_producto.model.Producto;
import com.luxury_sales.ms_producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoResponseDTO> obtenerTodos() {
        return null; 
    }

    @Override
    public ProductoResponseDTO guardar(ProductoRequestDTO requestDTO) {
        Producto producto = new Producto();
        producto.setNombre(requestDTO.getNombre());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setCantidad(requestDTO.getCantidad());

        Producto productoGuardado = productoRepository.save(producto);

        ProductoResponseDTO response = new ProductoResponseDTO();
        response.setId(productoGuardado.getId());
        response.setNombre(productoGuardado.getNombre());
        response.setPrecio(productoGuardado.getPrecio());
        response.setCantidad(productoGuardado.getCantidad());
        
        return response;
    }

    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }
}