package com.sportecommerce.proyecto.v1.modules.users.service;

import com.sportecommerce.proyecto.v1.modules.users.dto.PageDTO;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTOResponse;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    PageDTO<User> findAll(Pageable pageable);
    User findById(Long id);
    User create(UserDTORequest userDTORequest)  ;
    void delete (Long id);
    UserDTOResponse update(Long id, UserDTORequest userDTORequest);
    List<User> findAllUsers();
}
