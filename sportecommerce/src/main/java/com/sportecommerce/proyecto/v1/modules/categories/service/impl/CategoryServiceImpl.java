package com.sportecommerce.proyecto.v1.modules.categories.service.impl;


import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTORequest;
import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.mapper.MapperCategory;
import com.sportecommerce.proyecto.v1.modules.categories.model.Category;
import com.sportecommerce.proyecto.v1.modules.categories.repository.ICategoryRepository;
import com.sportecommerce.proyecto.v1.modules.categories.service.ICategoryService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.DuplicateResourceException;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.InvalidRequestException;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public PageDTO<CategoryDTOResponse> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

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
        Category category = categoryRepository.findByName(name).orElseThrow(
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

        if (categoryRepository.findByName(categoryDTORequest.getName()).isPresent()) {
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

    }
}

