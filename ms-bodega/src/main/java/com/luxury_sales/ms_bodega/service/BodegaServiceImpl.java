package com.luxury_sales.ms_bodega.service;

import com.luxury_sales.ms_bodega.dto.BodegaRequestDTO;
import com.luxury_sales.ms_bodega.dto.BodegaResponseDTO;
import com.luxury_sales.ms_bodega.dto.ProductoResponseDTO;
import com.luxury_sales.ms_bodega.model.Bodega;
import com.luxury_sales.ms_bodega.repository.BodegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BodegaServiceImpl implements BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    // Inyectamos el WebClient que configuramos en la capa Config
    @Autowired
    private WebClient productoWebClient;

    @Override
    public List<BodegaResponseDTO> obtenerTodos() {
        return bodegaRepository.findAll().stream()
                .map(this::convertirAFormatoSalida)
                .collect(Collectors.toList());
    }

    @Override
    public BodegaResponseDTO guardar(BodegaRequestDTO requestDTO) {
        try {
            
            ProductoResponseDTO producto = productoWebClient.get()
                    .uri("/productos/" + requestDTO.getProductoId())
                    .retrieve()
                    .bodyToMono(ProductoResponseDTO.class)
                    .block(); 

           
            Bodega nuevaBodega = new Bodega(
                    requestDTO.getProductoId(),
                    requestDTO.getCantidad(),
                    requestDTO.getPasillo(),
                    requestDTO.getEstante()
            );

            Bodega bodegaGuardada = bodegaRepository.save(nuevaBodega);
            return convertirAFormatoSalida(bodegaGuardada);

        } catch (Exception e) {
            
            throw new RuntimeException("Error de Comunicación: El producto con ID " 
                    + requestDTO.getProductoId() + " no existe en el catálogo de productos o el servicio no responde.");
        }
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