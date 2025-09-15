package com.sportecommerce.proyecto.v1.modules.categories.service.impl;


import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.service.ICategoryService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    @Override
    public PageDTO<CategoryDTOResponse> findAll(Pageable pageable) {
        return null;
    }
}

