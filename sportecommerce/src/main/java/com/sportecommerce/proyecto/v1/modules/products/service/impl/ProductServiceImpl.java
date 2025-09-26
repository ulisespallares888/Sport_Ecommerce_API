package com.sportecommerce.proyecto.v1.modules.products.service.impl;

import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.categories.repository.ICategoryRepository;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTORequest;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.mapper.MapperProduct;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import com.sportecommerce.proyecto.v1.modules.products.repository.IProductRepository;
import com.sportecommerce.proyecto.v1.modules.products.service.IProductService;
import com.sportecommerce.proyecto.v1.modules.products.validation.ProductValidator;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private static final String MEDIA_DIR = "/sportecommerce/v1/media/images";

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
    public ProductDTOResponse create(ProductDTORequest productDTORequest, MultipartFile[] images) {
        ProductValidator.validateProductDTORequest(productDTORequest);

        Product product = MapperProduct.INSTANCE.productDTOResponseToProduct(productDTORequest);

        /*
        Crear un mapper de catagories
        implementar create y findByName de Catagories

         */
        //product.addCategory(category);
        //product.setCategories(product.getCategories());

        productRepository.save(product);
        log.info("Product Created with ID = {}".formatted(product.getId()));
        return MapperProduct.INSTANCE.productToProductDTOResponse(product);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public ProductDTOResponse update(Long id, ProductDTORequest productDTORequest) {
        return null;
    }

    @Override
    public Object addImagesToProduct(Long id, MultipartFile[] images) {
        return null;
    }
}
