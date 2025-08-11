package com.sportecommerce.proyecto.v1.modules.users.service.impl;

import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTOResponse;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.mapper.MapperUser;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.service.IUserService;
import com.sportecommerce.proyecto.v1.modules.users.repository.IUserRepository;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.DuplicateResourceException;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.InvalidRequestException;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public User findById(Long id) {
        return iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User no found with ID = %s".formatted(id)));
    }

    @Override
    public User create(UserDTORequest userDTORequest) {

        if(iUserRepository.findByEmail(userDTORequest.getEmail()) != null) {
            throw new DuplicateResourceException(
                    "The user already exists with the email: %s".formatted(userDTORequest.getEmail()));
        }
        User userSave = MapperUser.INSTANCIA.userDTOToUser(userDTORequest);

        return iUserRepository.save(userSave);
    }

    @Override
    public void delete(Long id) {
        User user = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User no found with ID = %s".formatted(id)));
        iUserRepository.delete(user);
    }

    @Override
    public UserDTOResponse update(Long id, UserDTORequest userDTORequest) {

        if(!id.equals(userDTORequest.getId()))
            throw new InvalidRequestException("The ID in the path does not match the ID in the request body");

        User usertoUpdate = iUserRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User not found with ID = %s".formatted(id)));

        UserDTOResponse userDTOResponse = new UserDTOResponse();

        if(usertoUpdate != null){
            usertoUpdate = MapperUser.INSTANCIA.userDTOToUser(userDTORequest);
            iUserRepository.save(usertoUpdate);
             userDTOResponse = MapperUser.INSTANCIA.userToUserDTO(usertoUpdate);
        }

        return userDTOResponse;
    }




}
