package com.luxury_sales.ms_producto.config;

import com.luxury_sales.ms_producto.model.Producto;
import com.luxury_sales.ms_producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {
           
            productoRepository.save(new Producto("Pastillas de Freno Delanteras", "45.990", 20));
            productoRepository.save(new Producto("Amortiguador Hidráulico", "130.000", 15));
            productoRepository.save(new Producto("Filtro de Aceite Sintético", "12.000", 50));
            
            System.out.println(">>> ms-producto: 3 repuestos de autos insertados");
        }
    }
}