package com.luxury_sales.ms_usuario.service; //a quie esta la inteligencia y la logica de negocio 

import com.luxury_sales.ms_usuario.model.usuario;
import com.luxury_sales.ms_usuario.repository.usuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    
    private final usuarioRepository UsuarioRepository;
    
    public List <usuario> obtnerUsuarios () {
        return UsuarioRepository.findAll();

    }
     // Este método es clave: ms-usuario llama al endpoint
    // GET /api/categorias/{id} para verificar que el
    // id  existe antes de guardar un usuario.

    public  Optional <usuario> optenerPorId (Long id ) {
        return UsuarioRepository.findById(id);


    }

    public usuario guardar  (usuario Usuario ) {
        return UsuarioRepository.save (Usuario);

    }

    public void eliminar  (Long id) {
        UsuarioRepository.deleteById(id);
    }


 


}
