package com.luxury_sales.ms_ventas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.luxury_sales.ms_ventas.dto.PagoRequestDTO;
import com.luxury_sales.ms_ventas.dto.PagoResponseDTO;
import com.luxury_sales.ms_ventas.dto.ProductoResponseDTO;
import com.luxury_sales.ms_ventas.dto.UsuarioResponseDTO;
import com.luxury_sales.ms_ventas.dto.VentaRequestDTO;
import com.luxury_sales.ms_ventas.dto.VentaResponseDTO;
import com.luxury_sales.ms_ventas.model.Venta;
import com.luxury_sales.ms_ventas.repository.VentaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    @Qualifier("productoWebClient")
    private WebClient productoWebClient;

    @Autowired
    @Qualifier("usuarioWebClient")
    private WebClient usuarioWebClient;

    @Autowired
    @Qualifier("pagosWebClient")
    private WebClient pagosWebClient;

    private VentaResponseDTO mapToDTO(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setProductoId(venta.getProductoId());
        dto.setUsuarioId(venta.getUsuarioId());
        dto.setCantidad(venta.getCantidad());
        dto.setTotal(venta.getTotal());
        dto.setEstadoPago(venta.getEstadoPago());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setMensaje("Operacion exitosa.");
        return dto;
    }

    @Override
    public List<VentaResponseDTO> obtenerTodas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VentaResponseDTO> obtenerPorId(Long id) {
        return ventaRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public VentaResponseDTO registrarVenta(VentaRequestDTO requestDTO) {

        // 1. Validar usuario en ms-usuario
        UsuarioResponseDTO usuario;
        try {
            usuario = usuarioWebClient.get()
                    .uri("/api/usuarios/" + requestDTO.getUsuarioId())
                    .retrieve()
                    .bodyToMono(UsuarioResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Usuario " + requestDTO.getUsuarioId() + " no encontrado.");
        } catch (Exception e) {
            throw new RuntimeException("ms-usuario no disponible en puerto 8081.");
        }

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        // 2. Validar producto en ms-producto y obtener precio
        ProductoResponseDTO producto;
        try {
            producto = productoWebClient.get()
                    .uri("/api/productos/" + requestDTO.getProductoId())
                    .retrieve()
                    .bodyToMono(ProductoResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Producto " + requestDTO.getProductoId() + " no encontrado.");
        } catch (Exception e) {
            throw new RuntimeException("ms-producto no disponible en puerto 8082.");
        }

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado.");
        }

        // 3. Calcular total
        Double precio = Double.parseDouble(producto.getPrecio());
        Double totalCalculado = precio * requestDTO.getCantidad();
        String totalFormateado = totalCalculado.toString();

        // 4. Procesar pago en ms-pagos
        PagoRequestDTO pagoRequest = new PagoRequestDTO();
        pagoRequest.setProductoId(requestDTO.getProductoId());
        pagoRequest.setCantidad(requestDTO.getCantidad());
        pagoRequest.setMetodoPago(requestDTO.getMetodoPago());

        PagoResponseDTO pagoResponse;
        try {
            pagoResponse = pagosWebClient.post()
                    .uri("/pagos")
                    .bodyValue(pagoRequest)
                    .retrieve()
                    .bodyToMono(PagoResponseDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error al procesar el pago.");
        } catch (Exception e) {
            throw new RuntimeException("ms-pagos no disponible en puerto 8084.");
        }

        if (pagoResponse == null) {
            throw new RuntimeException("El pago no pudo ser procesado.");
        }

        // 5. Guardar venta
        Venta venta = new Venta();
        venta.setProductoId(requestDTO.getProductoId());
        venta.setUsuarioId(requestDTO.getUsuarioId());
        venta.setCantidad(requestDTO.getCantidad());
        venta.setTotal(totalFormateado);
        venta.setEstadoPago("APROBADO");

        return mapToDTO(ventaRepository.save(venta));
    }

    @Override
    public VentaResponseDTO actualizar(Long id, VentaRequestDTO requestDTO) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta " + id + " no encontrada."));

        venta.setProductoId(requestDTO.getProductoId());
        venta.setUsuarioId(requestDTO.getUsuarioId());
        venta.setCantidad(requestDTO.getCantidad());
        venta.setEstadoPago(requestDTO.getMetodoPago());

        return mapToDTO(ventaRepository.save(venta));
    }

    @Override
    public void eliminar(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta " + id + " no encontrada.");
        }
        ventaRepository.deleteById(id);
    }
}