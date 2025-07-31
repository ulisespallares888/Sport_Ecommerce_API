package com.tucompra.proyecto.v1.services.usuario.impl;

import com.tucompra.proyecto.v1.domain.Usuario;
import com.tucompra.proyecto.v1.exceptions.usuario.UserNotFoundException;
import com.tucompra.proyecto.v1.repositories.usuario.IUsuarioRepository;
import com.tucompra.proyecto.v1.services.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServicieImpl implements IUsuarioService {

    private final IUsuarioRepository iUsuarioRepository;

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        Page<Usuario> usuarioPage = iUsuarioRepository.findAll(pageable);
        return usuarioPage;
    }
    @Override
    public Usuario findById(UUID id) {
        return iUsuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID = " + id));
    }

    @Override
    public Usuario create(Usuario usuario) {
        return iUsuarioRepository.save(usuario);
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }

    @Override
    public Object update(UUID uuid, Usuario usuario) throws Exception {
        return null;
    }
}
