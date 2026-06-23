package com.luxury_sales.ms_bodega.service;

import com.luxury_sales.ms_bodega.dto.BodegaRequestDTO;
import com.luxury_sales.ms_bodega.dto.BodegaResponseDTO;
import com.luxury_sales.ms_bodega.dto.ProductoResponseDTO;
import com.luxury_sales.ms_bodega.exception.ResourceNotFoundException;
import com.luxury_sales.ms_bodega.model.Bodega;
import com.luxury_sales.ms_bodega.repository.BodegaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BodegaService {

    private final BodegaRepository bodegaRepository;

    private final WebClient productoWebClient;

    public List<BodegaResponseDTO> obtenerTodos() {
        return bodegaRepository.findAll().stream()
                .map(this::convertirAFormatoSalida)
                .collect(Collectors.toList());
    }

    public BodegaResponseDTO guardar(BodegaRequestDTO requestDTO) {
        try {
            ProductoResponseDTO producto = productoWebClient.get()
                    .uri("/api/productos/" + requestDTO.getProductoId())
                    .retrieve()
                    .bodyToMono(ProductoResponseDTO.class)
                    .block();

            if (producto == null) {
                throw new RuntimeException("El producto con ID " + requestDTO.getProductoId() + " no existe");
            }

            Bodega nuevaBodega = new Bodega(
                    requestDTO.getProductoId(),
                    requestDTO.getCantidad(),
                    requestDTO.getPasillo(),
                    requestDTO.getEstante()
            );

            Bodega bodegaGuardada = bodegaRepository.save(nuevaBodega);
            return convertirAFormatoSalida(bodegaGuardada);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error de Comunicación: El producto con ID "
                    + requestDTO.getProductoId() + " no existe en el catálogo de productos o el servicio no responde.");
        }
    }

    public BodegaResponseDTO actualizar(Long id, BodegaRequestDTO requestDTO) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bodega con ID " + id + " no encontrada"));

        bodega.setProductoId(requestDTO.getProductoId());
        bodega.setCantidad(requestDTO.getCantidad());
        bodega.setPasillo(requestDTO.getPasillo());
        bodega.setEstante(requestDTO.getEstante());

        return convertirAFormatoSalida(bodegaRepository.save(bodega));
    }

    public void eliminar(Long id) {
        if (!bodegaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bodega con ID " + id + " no encontrada");
        }
        bodegaRepository.deleteById(id);
    }

    private BodegaResponseDTO convertirAFormatoSalida(Bodega bodega) {
        return new BodegaResponseDTO(
                bodega.getId(),
                bodega.getProductoId(),
                bodega.getCantidad(),
                bodega.getPasillo(),
                bodega.getEstante()
        );
    }
}