package com.luxury_sales.ms_ventas.service;

import com.luxury_sales.ms_ventas.dto.VentaRequestDTO;
import com.luxury_sales.ms_ventas.dto.VentaResponseDTO;

public interface VentaService {
    VentaResponseDTO registrarVenta(VentaRequestDTO requestDTO);
}