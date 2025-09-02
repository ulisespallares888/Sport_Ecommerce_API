package com.sportecommerce.proyecto.v1.modules.products.mapper;

import com.sportecommerce.proyecto.v1.modules.products.dto.ImageProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.model.ImageProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperImage {
    MapperImage INSTANCE = Mappers.getMapper(MapperImage.class);

    @Mapping(target = "id",source = "id")
    @Mapping(target = "url", source = "url")
    ImageProductDTOResponse toImageProduct(ImageProduct product);
}
