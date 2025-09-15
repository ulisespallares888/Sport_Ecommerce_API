package com.sportecommerce.proyecto.v1.modules.products.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageProductDTOResponse {
    @JsonIgnore
    private long id;
    private String url;
}
