package com.sportecommerce.proyecto.v1.modules.users.service.impl;

import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.mapper.MapperUser;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.service.IUserService;
import com.sportecommerce.proyecto.v1.modules.users.repository.IUserRepository;
import com.sportecommerce.proyecto.v1.modules.users.validation.ValidatorUser;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> userPage = iUserRepository.findAll(pageable);
        return userPage;
    }
    @Override
    public User findById(UUID id) {
        return iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User no found with ID = %s".formatted(id)));
    }

    @Override
    public User create(UserDTORequest userDTORequest) {

        ValidatorUser.validateUserDTORequest(userDTORequest, iUserRepository);

        User userSave = MapperUser.INSTANCIA.userDTOToUser(userDTORequest);

        return iUserRepository.save(userSave);
    }

    @Override
    public void delete(UUID id) {
        User user = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User no found with ID = %s".formatted(id)));
        iUserRepository.delete(user);
    }

    @Override
    public Object update(UUID uuid, User user) throws Exception {
        return null;
    }




}
