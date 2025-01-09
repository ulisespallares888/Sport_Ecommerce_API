package com.tucompra.proyecto.v1.controllers.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;
import com.tucompra.proyecto.v1.services.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final IUsuarioService iUsuarioService;

    @GetMapping(value = "")
    public ResponseEntity<List<Usuario>>  findAll(){
        List<Usuario> usuarios = iUsuarioService.findAll();
        log.info("Usuarios encontrados: {}",usuarios);
        System.out.println(usuarios);
        return ResponseEntity.status(200).body(usuarios);
    }
}
