package com.sportecommerce.proyecto.v1.modules.products.mapper;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface MapperProduct {

    MapperProduct INSTANCE = Mappers.getMapper(MapperProduct.class);

    //@Mapping(source = "name",target = "name")
    //Product productDTOToProduct(ProductDTORequest productRequestDTO);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "categories", source = "categories")
    ProductDTOResponse  productToProductDTOResponse(Product product);

}
