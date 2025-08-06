package com.sportecommerce.proyecto.v1.modules.users.repository;

import com.sportecommerce.proyecto.v1.modules.users.model.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Registered
public interface IUserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
