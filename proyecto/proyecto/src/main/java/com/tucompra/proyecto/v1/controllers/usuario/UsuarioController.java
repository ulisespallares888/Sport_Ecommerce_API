package com.tucompra.proyecto.v1.controllers.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;
import com.tucompra.proyecto.v1.services.usuario.impl.UsuarioServicieImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

    private final UsuarioServicieImpl usuarioServicie ;
    private final PagedResourcesAssembler<Usuario> pagedResourcesAssembler;


    @GetMapping(value = "")
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        // Creamos el Pageable con los parámetros de paginación y ordenación
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        // Obtenemos los usuarios paginados desde el servicio
        Page<Usuario> usuarios = usuarioServicie.findAll(pageable);

        // Convertimos los usuarios en un PagedModel con enlaces HATEOAS
        PagedModel<EntityModel<Usuario>> usuarioPagedModel = pagedResourcesAssembler.toModel(usuarios, usuario -> {
                    // Envolvemos el usuario en un EntityModel antes de agregarle enlaces
                    EntityModel<Usuario> usuarioEntityModel = EntityModel.of(usuario);
                    usuarioEntityModel.add(
                                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                                                    .findById(usuario.getId()))
                                            .withSelfRel())
                            .add(Link.of("http://google.com").withRel("google"));

                    return usuarioEntityModel;
                }
        );


        return ResponseEntity.ok(usuarioPagedModel);
    }

    @GetMapping(value = "{id}")
    public Usuario findById(@Valid @PathVariable UUID id) {
        return usuarioServicie.findById(id);
    }

}