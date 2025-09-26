package com.sportecommerce.proyecto.v1.modules.products.mapper;

import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.products.dto.ImageProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.model.ImageProduct;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface MapperProduct {

    MapperProduct INSTANCE = Mappers.getMapper(MapperProduct.class);

    //@Mapping(source = "name",target = "name")
    //Product productDTOToProduct(ProductDTORequest productRequestDTO);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "categories", source = "categories")
    Product productDTOResponseToProduct(ProductDTORequest productDTORequest);



    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "categories", source = "categories")
    ProductDTOResponse productToProductDTOResponse(Product product);

/*
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "images", expression = "java(mapImages(product.getImages()))")
    @Mapping(target = "categories", expression = "java(mapCategories(product.getCategories()))")
    ProductDTOResponse productToProductDTOResponse(Product product);

    default Set<ImageProductDTOResponse> mapImages(Collection<ImageProduct> images) {
        return (images == null ? Set.of() : images.stream()
                        .map(
                        i -> new ImageProductDTOResponse(i.getId(), i.getUrl())
                        )
                        .collect(Collectors.toSet()));
    }

    default Set<CategoryDTOResponse> mapCategories(Collection<Category> categories) {
        return categories == null ? Set.of() :
                categories.stream().map(c -> new CategoryDTOResponse(c.getId(), c.getName())).collect(Collectors.toSet());
    }
*/
}

