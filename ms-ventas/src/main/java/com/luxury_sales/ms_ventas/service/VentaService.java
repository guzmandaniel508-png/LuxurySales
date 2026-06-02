package com.luxury_sales.ms_ventas.service;

import com.luxury_sales.ms_ventas.dto.VentaRequestDTO;
import com.luxury_sales.ms_ventas.dto.VentaResponseDTO;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<VentaResponseDTO> obtenerTodas();
    Optional<VentaResponseDTO> obtenerPorId(Long id);
    VentaResponseDTO registrarVenta(VentaRequestDTO requestDTO);
    VentaResponseDTO actualizar(Long id, VentaRequestDTO requestDTO);
    void eliminar(Long id);
}