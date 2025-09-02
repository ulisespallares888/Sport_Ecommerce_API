package com.sportecommerce.proyecto.v1.modules.products.dto;


import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTOResponse {
    private long id;
    private String name;
    private String description;
    private Double price;
    private List<ImageProductDTOResponse> images;
    private List<Category> categories;
}
