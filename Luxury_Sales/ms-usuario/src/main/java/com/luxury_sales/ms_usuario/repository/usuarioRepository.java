package com.luxury_sales.ms_usuario.repository; 
import com.luxury_sales.ms_usuario.model.Usuario; 
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.stereotype.Repository; 

@Repository 

public interface usuarioRepository  extends JpaRepository<Usuario, Long >{

    
}
