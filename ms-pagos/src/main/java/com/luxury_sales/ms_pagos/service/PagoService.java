package com.luxury_sales.ms_pagos.service;

import com.luxury_sales.ms_pagos.dto.PagoRequestDTO;
import com.luxury_sales.ms_pagos.dto.PagoResponseDTO;
import com.luxury_sales.ms_pagos.dto.ProductoResponseDTO;
import com.luxury_sales.ms_pagos.exception.ResourceNotFoundException;
import com.luxury_sales.ms_pagos.model.Pago;
import com.luxury_sales.ms_pagos.repository.PagoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${app.urls.ms-producto}")
    private String urlMsProducto;

    public PagoService(PagoRepository pagoRepository, WebClient.Builder webClientBuilder) {
        this.pagoRepository = pagoRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<PagoResponseDTO> obtenerTodos() {
        return pagoRepository.findAll().stream()
                .map(this::convertirAFormatoSalida)
                .collect(Collectors.toList());
    }

    public Optional<PagoResponseDTO> obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .map(this::convertirAFormatoSalida);
    }

    public PagoResponseDTO procesarPago(PagoRequestDTO requestDTO) {
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
                "El producto con ID " + requestDTO.getProductoId() + " no existe en el catálogo."
            );
        } catch (Exception e) {
            throw new RuntimeException("ms-producto no disponible en: " + urlMsProducto);
        }

        if (productoResponse == null) {
            throw new ResourceNotFoundException(
                "El producto con ID " + requestDTO.getProductoId() + " no existe en el catálogo."
            );
        }

        String precioStr = productoResponse.getPrecio().replace(".", "").replace(",", ".");
        Double precioProducto = Double.parseDouble(precioStr);
        Double totalCalculado = precioProducto * requestDTO.getCantidad();

        Pago p = new Pago();
        p.setProductoId(requestDTO.getProductoId());
        p.setCantidad(requestDTO.getCantidad());
        p.setMetodoPago(requestDTO.getMetodoPago());
        p.setTotalPagado(totalCalculado);

        return convertirAFormatoSalida(pagoRepository.save(p));
    }

    public PagoResponseDTO actualizar(Long id, PagoRequestDTO requestDTO) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con ID " + id + " no encontrado"));

        pago.setProductoId(requestDTO.getProductoId());
        pago.setCantidad(requestDTO.getCantidad());
        pago.setMetodoPago(requestDTO.getMetodoPago());

        return convertirAFormatoSalida(pagoRepository.save(pago));
    }

    public void eliminar(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pago con ID " + id + " no encontrado");
        }
        pagoRepository.deleteById(id);
    }

    private PagoResponseDTO convertirAFormatoSalida(Pago pago) {
        return new PagoResponseDTO(
                pago.getId(),
                pago.getProductoId(),
                pago.getCantidad(),
                pago.getTotalPagado(),
                pago.getMetodoPago()
        );
    }
}