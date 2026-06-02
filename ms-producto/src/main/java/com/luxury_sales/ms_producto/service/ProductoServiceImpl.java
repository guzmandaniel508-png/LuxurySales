package com.luxury_sales.ms_producto.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import com.luxury_sales.ms_producto.model.Producto;
import com.luxury_sales.ms_producto.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service              
@RequiredArgsConstructor 
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    
    private ProductoResponseDTO mapToDTO(Producto producto) {
        ProductoResponseDTO response = new ProductoResponseDTO();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setCantidad(producto.getCantidad());
        return response;
    }

    @Override
    public List<ProductoResponseDTO> obtenerTodos() {
       
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
      
        return productoRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public ProductoResponseDTO guardar(ProductoRequestDTO requestDTO) {
       
        Producto producto = new Producto();
        producto.setNombre(requestDTO.getNombre());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setCantidad(requestDTO.getCantidad());

    
        return mapToDTO(productoRepository.save(producto));
    }

    @Override
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO requestDTO) {
       
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto con ID " + id + " no encontrado."));

       
        producto.setNombre(requestDTO.getNombre());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setCantidad(requestDTO.getCantidad());

        return mapToDTO(productoRepository.save(producto));
    }

    @Override
    public void eliminar(Long id) {
      
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto con ID " + id + " no encontrado.");
        }
        
        productoRepository.deleteById(id);
    }
}