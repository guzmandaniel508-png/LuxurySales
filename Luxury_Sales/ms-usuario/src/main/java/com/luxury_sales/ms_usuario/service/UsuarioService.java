package com.luxury_sales.ms_usuario.service; //a quie esta la inteligencia y la logica de negocio 

import com.luxury_sales.ms_usuario.model.Usuario;
import com.luxury_sales.ms_usuario.repository.usuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    
    private final usuarioRepository UsuarioRepository;
    
    public List <Usuario> obtnerUsuarios () {
        return UsuarioRepository.findAll();

    }
     // Este método es clave: ms-usuario llama al endpoint
    // GET /api/categorias/{id} para verificar que el
    // id  existe antes de guardar un usuario.

    public  Optional <Usuario> optenerPorId (Long id ) {
        return UsuarioRepository.findById(id);


    }

    public Usuario guardar  (Usuario Usuario ) {
        return UsuarioRepository.save (Usuario);

    }

    public void eliminar  (Long id) {
        UsuarioRepository.deleteById(id);
    }


 


}
