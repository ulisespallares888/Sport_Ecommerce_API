package com.sportecommerce.proyecto.v1.modules.users.controller;

import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTOResponse;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportecommerce.proyecto.v1.modules.users.validation.UserValidator;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;
    private final PagedResourcesAssembler<UserDTOResponse> pagedResourcesAssembler;


    private PagedModel<EntityModel<UserDTOResponse>> toPagedModel(Page<UserDTOResponse> userDTOResponsePage) {
        PagedModel<EntityModel<UserDTOResponse>> model = pagedResourcesAssembler.toModel(
                userDTOResponsePage,
                userDTOResponse -> {
                    EntityModel<UserDTOResponse> entityModel = EntityModel.of(userDTOResponse);
                    entityModel.add(WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(UserController.class)
                                            .findById(userDTOResponse.getId()))
                            .withSelfRel());
                    return entityModel;
                }
        );


        model.removeLinks();
        String sortField = userDTOResponsePage.getSort().stream()
                .findFirst()
                .map(Sort.Order::getProperty)
                .orElse("id");
        String sortDirection = userDTOResponsePage.getSort().isSorted()
                ? (userDTOResponsePage.getSort().iterator().next().isDescending() ? "desc" : "asc")
                : "asc";

        model.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(UserController.class)
                                .getAllUsers(userDTOResponsePage.getNumber(),
                                        userDTOResponsePage.getSize(),
                                        sortField + "," + sortDirection,
                                        null))
                .withSelfRel());

        return model;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDTOResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String direction) {

        int maxSize = 100;
        if (size > maxSize) size = maxSize;

        Pageable pageable = buildPageable(page, size, sort, direction);
        PageDTO<UserDTOResponse> pageDTO = iUserService.findAll(pageable);

        Page<UserDTOResponse> users = new PageImpl<>(
                pageDTO.getContent(),
                PageRequest.of(pageDTO.getPage(), pageDTO.getSize(), pageable.getSort()),
                pageDTO.getTotalElements()
        );

        if (users.isEmpty()) {
            return ResponseEntity.ok(PagedModel.empty());
        }

        return ResponseEntity.ok(toPagedModel(users));
    }


    @GetMapping(value = "{id}")
    public User findById(@Valid @PathVariable Long id) {
        return iUserService.findById(id);
    }


    @DeleteMapping(value = "{id}")
    public ResponseEntity<User> delete(@Valid @PathVariable Long id) {
        iUserService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "")
    public ResponseEntity<User> create(@Valid @RequestBody UserDTORequest userDTORequest) {
        UserValidator.validateUserDTORequest(userDTORequest);
        User userSave = iUserService.create(userDTORequest);
        return ResponseEntity.ok(userSave);
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<UserDTOResponse> update(
            @Valid @PathVariable Long id,
            @RequestBody UserDTORequest userDTORequest) {
        UserValidator.validateUserDTORequest(userDTORequest);
        UserDTOResponse userDTOResponse = iUserService.update(id, userDTORequest);
        return ResponseEntity.ok(userDTOResponse);
    }

    private Pageable buildPageable(int page, int size, String sort, String direction) {
        if (sort == null || sort.isBlank()) {
            return PageRequest.of(page, size);
        }

        if (sort.contains(",")) {
            String[] parts = sort.split(",");
            String field = parts[0];
            String dir = parts.length > 1 ? parts[1] : "asc";
            Sort sortOrder = dir.equalsIgnoreCase("desc")
                    ? Sort.by(field).descending()
                    : Sort.by(field).ascending();
            return PageRequest.of(page, size, sortOrder);
        }

        Sort sortOrder = (direction != null && direction.equalsIgnoreCase("desc"))
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        return PageRequest.of(page, size, sortOrder);
    }
}
