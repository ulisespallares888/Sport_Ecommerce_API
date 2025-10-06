package com.sportecommerce.proyecto.v1.modules.categories.service.impl;


import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTORequest;
import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.mapper.MapperCategory;
import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.categories.repository.ICategoryRepository;
import com.sportecommerce.proyecto.v1.modules.categories.service.ICategoryService;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.mapper.MapperProduct;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.DuplicateResourceException;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.InvalidRequestException;
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
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public PageDTO<CategoryDTOResponse> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAllByActiveTrue(pageable);

        List<CategoryDTOResponse> categoryDTOResponseList = categoryPage.getContent()
                .stream()
                .map(MapperCategory.INSTANCE::categoryToCategoryDTOResponse)
                .toList();

        return new PageDTO<>(
                categoryDTOResponseList,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                categoryPage.getTotalElements()
        );

    }

    @Override
    public CategoryDTOResponse findByName(String name) {
        Category category = categoryRepository.findByNameAndActiveTrue(name).orElseThrow(
                () -> new ResourceNotFoundException("Category with name " + name + " not found")
        );
        return MapperCategory.INSTANCE.categoryToCategoryDTOResponse(category);
    }

    @Override
    public CategoryDTOResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category not found with ID = %s".formatted(id))
        );
        return MapperCategory.INSTANCE.categoryToCategoryDTOResponse(category);
    }

    @Override
    public CategoryDTOResponse create(CategoryDTORequest categoryDTORequest) {

        if (categoryDTORequest.getName().isEmpty() || categoryDTORequest.getName().isBlank()){
            throw new InvalidRequestException("Category name cannot be empty or blank");
        }

        if (categoryRepository.findByNameAndActiveTrue(categoryDTORequest.getName()).isPresent()) {
            throw new DuplicateResourceException("Category with name " + categoryDTORequest.getName() + " already exists");
        }
        else {
            Category category = new Category();
            category.setName(categoryDTORequest.getName().toUpperCase());
            categoryRepository.save(category);
            return MapperCategory.INSTANCE.categoryToCategoryDTOResponse(category);
        }
    }

    @Override
    public Category update(CategoryDTORequest categoryDTORequest) {
        return null;
    }

    @Override
    public void delete(String name) {
        Category category = categoryRepository.findByNameAndActiveTrue(name.toUpperCase()).orElseThrow(
                () -> new ResourceNotFoundException("Category with name " + name + " not found")
        );

        category.setActive(false);
        categoryRepository.save(category);
    }

    @Override
    public PageDTO<ProductDTOResponse> findProductsByCategories(List<String> names, Pageable pageable) {

        names = names.stream().map(String::toUpperCase).toList();

        Page<Product> productPage = categoryRepository.findProductsByCategories(names, pageable);


        List<ProductDTOResponse> productDTOResponseList = productPage.getContent()
                .stream()
                .map(MapperProduct.INSTANCE::productToProductDTOResponse)
                .toList();

        return new PageDTO<>(
                productDTOResponseList,
                productPage.getNumber(),
                productPage.getContent().size(),
                productPage.getTotalElements()
        );
    }
}

