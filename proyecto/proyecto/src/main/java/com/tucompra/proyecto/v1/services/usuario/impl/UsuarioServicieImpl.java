package com.tucompra.proyecto.v1.services.usuario.impl;

import com.tucompra.proyecto.v1.domain.Usuario;
import com.tucompra.proyecto.v1.repositories.usuario.IUsuarioRepository;
import com.tucompra.proyecto.v1.services.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServicieImpl implements IUsuarioService {

    private final IUsuarioRepository iUsuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return iUsuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Usuario create(Usuario usuario) {
        return null;
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
