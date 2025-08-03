package com.tucompra.proyecto.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {
    private String nombre;
    private String apellido;
    private String tipo;
    private String email;
}
