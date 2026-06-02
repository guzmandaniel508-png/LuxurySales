package com.luxury_sales.ms_bodega.service;

import com.luxury_sales.ms_bodega.dto.BodegaRequestDTO;
import com.luxury_sales.ms_bodega.dto.BodegaResponseDTO;
import java.util.List;

public interface BodegaService {
    List<BodegaResponseDTO> obtenerTodos();
    BodegaResponseDTO guardar(BodegaRequestDTO bodegaRequestDTO);
}