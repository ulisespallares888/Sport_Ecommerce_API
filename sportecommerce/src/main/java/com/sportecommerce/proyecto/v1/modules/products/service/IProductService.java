package com.sportecommerce.proyecto.v1.modules.products.service;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {
    PageDTO<ProductDTOResponse> findAll(Pageable pageable);
    ProductDTOResponse findById(Long id);
    ProductDTOResponse create(ProductDTORequest productDTORequest, MultipartFile[] images);
    void delete (Integer id);
    ProductDTOResponse update(Long id, ProductDTORequest productDTORequest);
    Object addImagesToProduct(MultipartFile[] images);
}





