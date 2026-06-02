package com.luxury_sales.ms_usuario.service;

import com.luxury_sales.ms_usuario.dto.UsuarioRequestDTO;
import com.luxury_sales.ms_usuario.dto.UsuarioResponseDTO;

import java.util.List;
import java.util.Optional;


public interface UsuarioService {

    List<UsuarioResponseDTO> obtenerTodos();

    Optional<UsuarioResponseDTO> obtenerPorId(Long id);

    UsuarioResponseDTO guardar(UsuarioRequestDTO dto);

    Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto);

    void eliminar(Long id);
}