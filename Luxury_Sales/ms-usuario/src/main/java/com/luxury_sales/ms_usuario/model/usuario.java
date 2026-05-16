package com.luxury_sales.ms_usuario.model;

import jakarta.persistence.*; //herramienta para conectar java con sql
import lombok.Data; // crea los getters y setters  automaticamente

//anotaciones
@Entity //le dice a spring :"crea una tabla en la base de datos con esta clase"
@Table (name = "usuarios")//nombre exacto en tabla en oracle
@Data // magia de lombok: nos ahorra 50 linea de codigo



public class Usuario {
    @Id //define que este  campo es el id inico
    @GeneratedValue (strategy =GenerationType.IDENTITY) //oracle asigna el numero 1,2,3...
    private long id;

    @Column (nullable = false, unique = true ) //no puede estar vacio y no se puede repetir 
    private String username;

    @Column (nullable = false) //obligatorio poner clave 
    private String password;
}
