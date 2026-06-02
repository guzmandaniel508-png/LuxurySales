package com.luxury_sales.ms_pagos.service;

import com.luxury_sales.ms_pagos.dto.PagoRequestDTO;
import com.luxury_sales.ms_pagos.dto.PagoResponseDTO;
import com.luxury_sales.ms_pagos.exception.ResourceNotFoundException;
import com.luxury_sales.ms_pagos.model.Pago;
import com.luxury_sales.ms_pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // Inyectamos la URL desde el application.properties (http://localhost:8082)
    @Value("${app.urls.ms-producto}")
    private String urlMsProducto;

    @Override
    public PagoResponseDTO procesarPago(PagoRequestDTO requestDTO) {
        
        // 1. Viajamos por red a ms-producto usando la URL dinámica del properties
        Object productoResponse = webClientBuilder.build()
                .get()
                .uri(urlMsProducto + "/productos/" + requestDTO.getProductoId())
                .retrieve()
                .bodyToMono(Object.class) // Obtenemos el cuerpo del producto
                .block(); 

        // Si el producto no existe, lanzamos nuestra excepción personalizada
        if (productoResponse == null) {
            throw new ResourceNotFoundException(
                "Error: El producto con ID " + requestDTO.getProductoId() + " no existe en el catálogo."
            );
        }

        // NOTA DE MAÑANA: Aquí mapearemos el precio real que venga en el 'productoResponse'
        // Por ahora simulamos un precio base fijo para que compile y guarde directo.
        Double precioProducto = 55000.0; 
        Double totalCalculado = precioProducto * requestDTO.getCantidad();

        // 2. Mapeamos y guardamos el registro en la base de datos (Entidad)
        Pago pago = new Pago();
        pago.setProductoId(requestDTO.getProductoId());
        pago.setCantidad(requestDTO.getCantidad());
        pago.setMetodoPago(requestDTO.getMetodoPago());
        pago.setTotalPagado(totalCalculado);

        Pago pagoGuardado = pagoRepository.save(pago);

        // 3. Transformamos la entidad guardada en el DTO de salida para el controlador
        return new PagoResponseDTO(
            pagoGuardado.getId(),
            pagoGuardado.getProductoId(),
            pagoGuardado.getCantidad(),
            pagoGuardado.getTotalPagado(),
            pagoGuardado.getMetodoPago()
        );
    }
}