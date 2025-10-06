package com.sportecommerce.proyecto.v1.modules.categories.controller;

import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTORequest;
import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.service.ICategoryService;
import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    private final PagedResourcesAssembler<CategoryDTOResponse> pagedResourcesAssembler;

    private PagedModel<EntityModel<CategoryDTOResponse>> toPagedModel(Page<CategoryDTOResponse> categoryDTOResponsePage){
        return pagedResourcesAssembler.toModel(
                categoryDTOResponsePage, EntityModel::of
        );
    }

    @GetMapping(value = "")
    public ResponseEntity<PagedModel<EntityModel<CategoryDTOResponse>>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sort,
        @RequestParam(defaultValue = "asc") String direction){

        int maxSize = 100;
        if (size > maxSize){
            size = maxSize;
        }

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        PageDTO<CategoryDTOResponse> categoryDTOResponsePage = categoryService.findAll(pageable);

        Page<CategoryDTOResponse> categoryDTOResponsePageDTO = new PageImpl<>(
                categoryDTOResponsePage.getContent(),
                PageRequest.of(
                        categoryDTOResponsePage.getPage(),
                        categoryDTOResponsePage.getSize(),
                        sortOrder),
                categoryDTOResponsePage.getTotalElements()
        );

        if (categoryDTOResponsePageDTO.getContent().isEmpty()){
            return ResponseEntity.ok(PagedModel.empty());
        }
        return ResponseEntity.ok(toPagedModel(categoryDTOResponsePageDTO));

    }

    //@GetMapping(value = "{names}/products")
    //public ResponseEntity<CategoryDTOResponse> getProductsByCategories(@PathVariable List<String> names){
    //    return ResponseEntity.ok(categoryService.findProductsByCategories(names,));
    //}

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<CategoryDTOResponse> getCategoryByName(@PathVariable String name){
        return ResponseEntity.ok(categoryService.findByName(name));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CategoryDTOResponse> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<CategoryDTOResponse> createCategory(@RequestBody CategoryDTORequest categoryDTORequest){
        return ResponseEntity.ok(categoryService.create(categoryDTORequest));
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<?> deleteCategory(@PathVariable String name){
        categoryService.delete(name);
        return ResponseEntity.noContent().build();
    }

}



