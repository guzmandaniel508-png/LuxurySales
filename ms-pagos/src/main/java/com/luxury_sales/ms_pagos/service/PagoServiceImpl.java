package com.luxury_sales.ms_pagos.service;

import com.luxury_sales.ms_pagos.dto.PagoRequestDTO;
import com.luxury_sales.ms_pagos.dto.PagoResponseDTO;
import com.luxury_sales.ms_pagos.dto.ProductoResponseDTO;
import com.luxury_sales.ms_pagos.exception.ResourceNotFoundException;
import com.luxury_sales.ms_pagos.model.Pago;
import com.luxury_sales.ms_pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${app.urls.ms-producto}")
    private String urlMsProducto;

    @Override
    public PagoResponseDTO procesarPago(PagoRequestDTO requestDTO) {

        // 1. Obtener producto desde ms-producto con URI correcta /api/productos/
        ProductoResponseDTO productoResponse;
        try {
            productoResponse = webClientBuilder.build()
                    .get()
                    .uri(urlMsProducto + "/api/productos/" + requestDTO.getProductoId())
                    .retrieve()
                    .bodyToMono(ProductoResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new ResourceNotFoundException(
                "Error: El producto con ID " + requestDTO.getProductoId() + " no existe en el catálogo."
            );
        } catch (Exception e) {
            throw new RuntimeException("ms-producto no disponible en: " + urlMsProducto);
        }

        if (productoResponse == null) {
            throw new ResourceNotFoundException(
                "Error: El producto con ID " + requestDTO.getProductoId() + " no existe en el catálogo."
            );
        }

        // 2. Calcular total — el precio viene como String desde ms-producto
        // Se limpia el punto de miles si existe (ej: "45.990" → 45990.0)
        String precioStr = productoResponse.getPrecio().replace(".", "").replace(",", ".");
        Double precioProducto = Double.parseDouble(precioStr);
        Double totalCalculado = precioProducto * requestDTO.getCantidad();

        // 3. Guardar el pago
        Pago pago = new Pago();
        pago.setProductoId(requestDTO.getProductoId());
        pago.setCantidad(requestDTO.getCantidad());
        pago.setMetodoPago(requestDTO.getMetodoPago());
        pago.setTotalPagado(totalCalculado);

        Pago pagoGuardado = pagoRepository.save(pago);

        // 4. Retornar respuesta
        return new PagoResponseDTO(
                pagoGuardado.getId(),
                pagoGuardado.getProductoId(),
                pagoGuardado.getCantidad(),
                pagoGuardado.getTotalPagado(),
                pagoGuardado.getMetodoPago()
        );
    }
}