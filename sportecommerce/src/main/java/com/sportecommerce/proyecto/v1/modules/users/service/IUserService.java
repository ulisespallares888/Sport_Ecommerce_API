package com.sportecommerce.proyecto.v1.modules.users.service;

import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTOResponse;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    PageDTO<UserDTOResponse> findAll(Pageable pageable);
    User findById(Long id);
    User create(UserDTORequest userDTORequest)  ;
    void delete (Long id);
    UserDTOResponse update(Long id, UserDTORequest userDTORequest);

}
