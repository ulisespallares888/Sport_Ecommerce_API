package com.sportecommerce.proyecto.v1.modules.users.service;

import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.repository.IUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    Page<User> findAll(Pageable pageable);
    User findById(Long id);
    User create(UserDTORequest userDTORequest)  ;
    void delete (Long id);
    Object update(Long uuid, User user)  throws Exception;

}
