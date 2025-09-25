package com.sportecommerce.proyecto.v1.modules.products.validation;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.InvalidRequestException;

public class ProductValidator {

    public static void validateProductDTORequest(ProductDTORequest productDTORequest) {

        if(productDTORequest.getName() == null ||  productDTORequest.getName().isEmpty() || productDTORequest.getName().isBlank()) {
            throw new InvalidRequestException("Product name is required");
        }

        if (productDTORequest.getCategories().isEmpty()) {
            throw new InvalidRequestException("At least one category is required");
        }

    }
}
