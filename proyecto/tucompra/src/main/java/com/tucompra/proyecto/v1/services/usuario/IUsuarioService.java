package com.tucompra.proyecto.v1.services.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUsuarioService {
    Page<Usuario> findAll(Pageable pageable);
    Usuario findById(UUID id);
    Usuario create(Usuario usuario)  ;
    void delete (UUID id);
    Object update(UUID uuid, Usuario usuario)  throws Exception;

}
