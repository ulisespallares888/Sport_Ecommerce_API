package com.sportecommerce.proyecto.v1.modules.products.service.impl;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.mapper.MapperProduct;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import com.sportecommerce.proyecto.v1.modules.products.repository.IProductRepository;
import com.sportecommerce.proyecto.v1.modules.products.service.IProductService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public PageDTO<ProductDTOResponse> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductDTOResponse> productDTOResponseList = productPage.getContent()
                .stream()
                .map(MapperProduct.INSTANCE::productToProductDTOResponse)
                .toList();

        return new PageDTO<>(
                productDTOResponseList,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements()

        );
    }

    @Override
    public ProductDTOResponse findById(Long id) {
        return null;
    }

    @Override
    public ProductDTOResponse create(ProductDTOResponse productDTOResponse) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public ProductDTOResponse update(Long id, ProductDTORequest productDTORequest) {
        return null;
    }
}
