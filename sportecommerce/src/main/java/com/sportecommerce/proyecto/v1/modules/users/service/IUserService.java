package com.sportecommerce.proyecto.v1.modules.users.service;

import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    Page<User> findAll(Pageable pageable);
    User findById(UUID id);
    User create(UserDTORequest userDTORequest)  ;
    void delete (UUID id);
    Object update(UUID uuid, User user)  throws Exception;

}
