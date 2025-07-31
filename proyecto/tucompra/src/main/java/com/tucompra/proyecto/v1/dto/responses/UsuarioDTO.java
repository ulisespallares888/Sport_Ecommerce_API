package com.tucompra.proyecto.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {
    private UUID id;
    private String name;
}
