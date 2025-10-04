package com.sportecommerce.proyecto.v1.modules.categories.mapper;

import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTORequest;
import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperCategory {
    MapperCategory INSTANCE = Mappers.getMapper(MapperCategory.class);

    @Mapping(target = "name", source = "name")
    Category categoryDTORequestToCategory(CategoryDTORequest categoryDTORequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTOResponse categoryToCategoryDTOResponse(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Category categoryDTOResponseToCategory(CategoryDTOResponse categoryDTOResponse);
}
