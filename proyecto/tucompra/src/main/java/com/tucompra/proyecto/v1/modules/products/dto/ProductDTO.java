package com.tucompra.proyecto.v1.modules.products.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {

    private String nombre;
    private String descripcion;
    private Double precio;

}
