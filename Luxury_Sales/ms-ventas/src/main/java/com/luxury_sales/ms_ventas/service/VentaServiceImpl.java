package com.luxury_sales.ms_ventas.service;

import com.luxury_sales.ms_ventas.dto.PagoRequestDTO;
import com.luxury_sales.ms_ventas.dto.PagoResponseDTO;
import com.luxury_sales.ms_ventas.dto.ProductoResponseDTO;
import com.luxury_sales.ms_ventas.dto.VentaRequestDTO;
import com.luxury_sales.ms_ventas.dto.VentaResponseDTO;
import com.luxury_sales.ms_ventas.exception.ResourceNotFoundException;
import com.luxury_sales.ms_ventas.model.Venta;
import com.luxury_sales.ms_ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    @Qualifier("productoWebClient")
    private WebClient productoWebClient;

    @Autowired
    @Qualifier("pagosWebClient")
    private WebClient pagosWebClient;

    @Override
    public VentaResponseDTO registrarVenta(VentaRequestDTO requestDTO) {
        
        
        ProductoResponseDTO producto = null;
        try {
            producto = productoWebClient.get()
                    .uri("/api/productos/" + requestDTO.getProductoId())
                    .retrieve()
                    .bodyToMono(ProductoResponseDTO.class)
                    .block();
        } catch (Exception e) {
            throw new ResourceNotFoundException("El producto con ID " + requestDTO.getProductoId() + " no responde o no existe en el catálogo.");
        }

        if (producto == null) {
            throw new ResourceNotFoundException("Producto no encontrado en el sistema de catálogo.");
        }

        
        Double totalCalculado = producto.getPrecio() * requestDTO.getCantidad();

        
        PagoRequestDTO pagoRequest = new PagoRequestDTO();
        pagoRequest.setProductoId(requestDTO.getProductoId());
        pagoRequest.setCantidad(requestDTO.getCantidad());

        PagoResponseDTO pagoResponse = null;
        try {
            pagoResponse = pagosWebClient.post()
                    .uri("/api/pagos")
                    .bodyValue(pagoRequest)
                    .retrieve()
                    .bodyToMono(PagoResponseDTO.class)
                    .block();
        } catch (Exception e) {
            throw new ResourceNotFoundException("La pasarela de pagos (ms-pagos) no se encuentra disponible.");
        }

        if (pagoResponse == null || !"APROBADO".equalsIgnoreCase(pagoResponse.getEstado())) {
            throw new ResourceNotFoundException("La venta no pudo completarse porque el pago fue RECHAZADO o no se pudo procesar.");
        }

        
        Venta nuevaVenta = new Venta();
        nuevaVenta.setProductoId(requestDTO.getProductoId());
        nuevaVenta.setCantidad(requestDTO.getCantidad());
        nuevaVenta.setTotal(totalCalculado);
        nuevaVenta.setEstadoPago(pagoResponse.getEstado().toUpperCase());

        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        
        VentaResponseDTO responseDTO = new VentaResponseDTO();
        responseDTO.setId(ventaGuardada.getId());
        responseDTO.setProductoId(ventaGuardada.getProductoId());
        responseDTO.setCantidad(ventaGuardada.getCantidad());
        responseDTO.setTotal(ventaGuardada.getTotal());
        responseDTO.setEstadoPago(ventaGuardada.getEstadoPago());
        responseDTO.setFechaVenta(ventaGuardada.getFechaVenta());
        responseDTO.setMensaje("Venta procesada con éxito y registrada en Oracle Cloud.");

        return responseDTO;
    }
}