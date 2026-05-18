package com.luxury_sales.ms_producto.controller;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import com.luxury_sales.ms_producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoResponseDTO> listarProductos() {
        return productoService.obtenerTodos();
    }

    @PostMapping
    public ProductoResponseDTO crearProducto(@RequestBody ProductoRequestDTO productoRequestDTO) {
        return productoService.guardar(productoRequestDTO);
    }
}