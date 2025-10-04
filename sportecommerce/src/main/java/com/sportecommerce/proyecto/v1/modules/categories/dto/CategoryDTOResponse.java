package com.sportecommerce.proyecto.v1.modules.categories.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.server.core.Relation;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Relation(collectionRelation = "categories")
public class CategoryDTOResponse {
    @JsonIgnore
    private Long id;
    private String name;
}
