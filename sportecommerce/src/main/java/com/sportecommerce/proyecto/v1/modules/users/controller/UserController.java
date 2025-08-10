package com.sportecommerce.proyecto.v1.modules.users.controller;

import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportecommerce.proyecto.v1.modules.users.validation.ValidatorUser;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;


    @GetMapping(value = "")
    public ResponseEntity<PagedModel<EntityModel<User>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<User> users = iUserService.findAll(pageable);

        return ResponseEntity.ok(toPagedModel(users));
    }


    private PagedModel<EntityModel<User>> toPagedModel(Page<User> users) {
        return pagedResourcesAssembler.toModel(users, user -> {
            EntityModel<User> usuarioEntityModel = EntityModel.of(user);
            usuarioEntityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                            .findById(user.getId())).withSelfRel());
            return usuarioEntityModel;
        });
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

        ValidatorUser.validateUserDTORequest(userDTORequest);

        User userSave = iUserService.create(userDTORequest);
        return ResponseEntity.ok(userSave);
    }

}