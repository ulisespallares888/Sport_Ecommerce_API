package com.sportecommerce.proyecto.v1.modules.products.service.impl;

import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.mapper.MapperProduct;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import com.sportecommerce.proyecto.v1.modules.products.repository.IProductRepository;
import com.sportecommerce.proyecto.v1.modules.products.service.IProductService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private static final String MEDIA_DIR = "/sportecommerce/media/images";

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
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product Not Found ID = %s".formatted(id))
                );

        return MapperProduct.INSTANCE.productToProductDTOResponse(product);
    }

    @Override
    public ProductDTOResponse create(ProductDTORequest productDTORequest) {
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
