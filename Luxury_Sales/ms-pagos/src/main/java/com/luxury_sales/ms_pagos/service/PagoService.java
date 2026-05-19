package com.luxury_sales.ms_pagos.service;

import com.luxury_sales.ms_pagos.dto.PagoRequestDTO;
import com.luxury_sales.ms_pagos.dto.PagoResponseDTO;

public interface PagoService {
    PagoResponseDTO procesarPago(PagoRequestDTO requestDTO);
}