package com.tucompra.proyecto.v1.repositories.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Registered
public interface IUsuarioRepository  extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
}
