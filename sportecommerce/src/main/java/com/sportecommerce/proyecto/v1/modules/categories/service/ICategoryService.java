package com.sportecommerce.proyecto.v1.modules.categories.service;

import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    PageDTO<CategoryDTOResponse> findAll(Pageable pageable);
}