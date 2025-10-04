package com.sportecommerce.proyecto.v1.modules.products.dto;


import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "products")
public class ProductDTOResponse {
    private long id;
    private String name;
    private String description;
    private Double price;
    private List<ImageProductDTOResponse> images;
    private List<CategoryDTOResponse> categories;
}
