package com.tucompra.proyecto.v1.controllers.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;
import com.tucompra.proyecto.v1.dto.responses.UsuarioDTO;
import com.tucompra.proyecto.v1.services.usuario.IUsuarioService;
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

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService iUsuarioServicie ;
    private final PagedResourcesAssembler<Usuario> pagedResourcesAssembler;


    @GetMapping(value = "")
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sortOrder = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Usuario> usuarios = iUsuarioServicie.findAll(pageable);

        return ResponseEntity.ok(toPagedModel(usuarios));
    }


    private PagedModel<EntityModel<Usuario>> toPagedModel(Page<Usuario> usuarios) {
        return pagedResourcesAssembler.toModel(usuarios, usuario -> {
            EntityModel<Usuario> usuarioEntityModel = EntityModel.of(usuario);
            usuarioEntityModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                            .findById(usuario.getId())).withSelfRel());
            return usuarioEntityModel;
        });
    }

    @GetMapping(value = "{id}")
    public Usuario findById(@Valid @PathVariable UUID id) {
        return iUsuarioServicie.findById(id);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Usuario> delete(@Valid @PathVariable UUID id) {
         iUsuarioServicie.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "")
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioSave = iUsuarioServicie.create(usuarioDTO);
        return ResponseEntity.ok(usuarioSave);
    }

}