package com.tucompra.proyecto.v1.services.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(UUID uuid);
    Usuario create(Usuario usuario)  ;
    boolean delete (UUID uuid);
    Object update(UUID uuid, Usuario usuario)  throws Exception;

}
