package com.sportecommerce.proyecto.v1.modules.categories.controller;

import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTORequest;
import com.sportecommerce.proyecto.v1.modules.categories.dto.CategoryDTOResponse;
import com.sportecommerce.proyecto.v1.modules.categories.service.ICategoryService;
import com.sportecommerce.proyecto.v1.modules.products.dto.ProductDTOResponse;
import com.sportecommerce.proyecto.v1.modules.products.model.Product;
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

    @GetMapping(value = "{names}/products")
    public ResponseEntity<PagedModel<EntityModel<ProductDTOResponse>>> getProductsByCategories(
            @PathVariable List<String> names,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        int maxSize = 100;
        if (size > maxSize) {
            size = maxSize;
        }

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Llama al servicio para obtener los productos por categorías
        PageDTO<ProductDTOResponse> productPageDTO = categoryService.findProductsByCategories(names, pageable);

        // Convertimos el PageDTO en un Page real para usar con el assembler
        Page<ProductDTOResponse> productPage = new PageImpl<>(
                productPageDTO.getContent(),
                PageRequest.of(productPageDTO.getPage(), productPageDTO.getSize(), sortOrder),
                productPageDTO.getTotalElements()
        );

        if (productPage.getContent().isEmpty()) {
            return ResponseEntity.ok(PagedModel.empty());
        }

        // Creamos un assembler propio para productos (podés inyectarlo también)
        PagedResourcesAssembler<ProductDTOResponse> productAssembler = new PagedResourcesAssembler<>(null, null);
        PagedModel<EntityModel<ProductDTOResponse>> pagedModel = productAssembler.toModel(productPage, EntityModel::of);

        return ResponseEntity.ok(pagedModel);
    }

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



