package com.sportecommerce.proyecto.v1.modules.categories.controller;

import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.service.ICategoryService;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;
    private final PagedResourcesAssembler<CategoryDTOResponse> pagedResourcesAssembler;

    private PagedModel<EntityModel<CategoryDTOResponse>> toPagedModel(Page<CategoryDTOResponse> categoryDTOResponsePage) {
        return pagedResourcesAssembler.toModel(categoryDTOResponsePage, EntityModel::of);
    }

    @GetMapping("")
    public ResponseEntity<PagedModel<EntityModel<CategoryDTOResponse>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        int maxSize = 100;
        if(size>maxSize) size=maxSize;

        Pageable pageable = buildPageable(page, size, sort, direction);
        PageDTO<CategoryDTOResponse> pageDTO = categoryService.findAll(pageable);

        Page<CategoryDTOResponse> categoryDTOResponsePage = new PageImpl<>(
                pageDTO.getContent(),
                PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), pageable.getSort()),
                pageDTO.getTotalElements()
        );

        if(categoryDTOResponsePage.isEmpty()) return ResponseEntity.ok(PagedModel.empty());
        return ResponseEntity.ok(toPagedModel(categoryDTOResponsePage));
    }

    @GetMapping("{id}")
    public CategoryDTOResponse getCategoryById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @GetMapping("/name/{name}")
    public CategoryDTOResponse getCategoryByName(@PathVariable String name){
        return categoryService.findByName(name);
    }

    @GetMapping("{names}/products")
    public ResponseEntity<PagedModel<EntityModel<ProductDTOResponse>>> getProductsByCategories(
            @PathVariable List<String> names,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        int maxSize = 100;
        if(size>maxSize) size=maxSize;

        Pageable pageable = buildPageable(page, size, sort, direction);
        PageDTO<ProductDTOResponse> productPageDTO = categoryService.findProductsByCategories(names, pageable);

        Page<ProductDTOResponse> productPage = new PageImpl<>(
                productPageDTO.getContent(),
                PageRequest.of(productPageDTO.getPage(), productPageDTO.getSize(), pageable.getSort()),
                productPageDTO.getTotalElements()
        );

        if(productPage.isEmpty()) return ResponseEntity.ok(PagedModel.empty());

        PagedResourcesAssembler<ProductDTOResponse> productAssembler = new PagedResourcesAssembler<>(null, null);
        PagedModel<EntityModel<ProductDTOResponse>> pagedModel = productAssembler.toModel(productPage, EntityModel::of);

        return ResponseEntity.ok(pagedModel);
    }

    private Pageable buildPageable(int page, int size, String sort, String direction) {
        if(sort==null || sort.isBlank()) return PageRequest.of(page, size);

        if(sort.contains(",")) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length>1 ? parts[1] : "asc";
            Sort sortOrder = dir.equalsIgnoreCase("desc") ? Sort.by(field).descending() : Sort.by(field).ascending();
            return PageRequest.of(page, size, sortOrder);
        }

        Sort sortOrder = (direction!=null && direction.equalsIgnoreCase("desc")) ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        return PageRequest.of(page, size, sortOrder);
    }
}
