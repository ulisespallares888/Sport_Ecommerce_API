package com.sportecommerce.proyecto.v1.modules.products.dto;


import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTORequest;
import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTORequest {
    private String name;
    private String description;
    private Double price;
    private List<CategoryDTORequest> categories;

}
