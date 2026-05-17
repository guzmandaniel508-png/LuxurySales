package com.luxury_sales.ms_usuario.dto;

import lombok.Data;

@Data


public class UsuarioResponseDTO {
    private Long id;          // Le entregas su ID generado en la base de datos
    private String username;  // Le entregas su nombre de usuario
    //  LA PASSWORD NO SE INCLUYE AQUÍ POR SEGURIDAD



}
