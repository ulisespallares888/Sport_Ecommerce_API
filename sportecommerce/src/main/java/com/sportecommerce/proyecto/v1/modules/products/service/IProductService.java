package com.sportecommerce.proyecto.v1.modules.products.service;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    PageDTO<ProductDTOResponse> findAll(Pageable pageable);
    ProductDTOResponse findById(Long id);
    ProductDTOResponse create(ProductDTOResponse productDTOResponse);
    void delete (Integer id);
    ProductDTOResponse update(Long id, ProductDTORequest productDTORequest);
}

