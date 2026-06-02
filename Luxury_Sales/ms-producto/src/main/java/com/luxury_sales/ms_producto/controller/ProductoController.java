package com.luxury_sales.ms_producto.controller;

import com.luxury_sales.ms_producto.dto.ProductoRequestDTO;
import com.luxury_sales.ms_producto.dto.ProductoResponseDTO;
import com.luxury_sales.ms_producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoResponseDTO> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> guardar(@RequestBody ProductoRequestDTO requestDTO) {
        ProductoResponseDTO nuevoProducto = productoService.guardar(requestDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.luxury_sales.ms_producto.model.Producto> obtenerPorId(@PathVariable Long id) {
        java.util.Optional<com.luxury_sales.ms_producto.model.Producto> productoOpt = productoService.obtenerPorId(id);
        
        if (productoOpt.isPresent()) {
            return new ResponseEntity<>(productoOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}