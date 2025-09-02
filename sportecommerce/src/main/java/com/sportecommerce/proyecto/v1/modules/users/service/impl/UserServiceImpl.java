package com.sportecommerce.proyecto.v1.modules.users.service.impl;

import com.sportecommerce.proyecto.v1.shared.DTOs.PageDTO;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTOResponse;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.mapper.MapperUser;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.service.IUserService;
import com.sportecommerce.proyecto.v1.modules.users.repository.IUserRepository;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.DuplicateResourceException;
import com.sportecommerce.proyecto.v1.shared.exceptions.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;



    @Cacheable(value = "users", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public PageDTO<UserDTOResponse> findAll(Pageable pageable) {
        Page<User> usersPage = iUserRepository.findAll(pageable);

        List<UserDTOResponse> dtoContent = usersPage.getContent().stream()
                .map(MapperUser.INSTANCIA::userToUserDTO)
                .toList();

        return new PageDTO<>(
                dtoContent,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements()
        );
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User no found with ID = %s".formatted(id)));
    }

    @Override
    @CachePut(value = "user", key = "#result.id")
    @CacheEvict(value = "users", allEntries = true)
    public User create(UserDTORequest userDTORequest) {

        if(iUserRepository.findByEmail(userDTORequest.getEmail()) != null) {
            throw new DuplicateResourceException(
                    "The user already exists with the email: %s".formatted(userDTORequest.getEmail()));
        }
        User userSave = MapperUser.INSTANCIA.userDTOToUser(userDTORequest);
        iUserRepository.save(userSave);
        return userSave;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void delete(Long id) {
        User user = iUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User no found with ID = %s".formatted(id)));
        iUserRepository.delete(user);
    }

    @Override
    @CachePut(value = "user", key = "#id")
    @CacheEvict(value = "users", allEntries = true)
    public UserDTOResponse update(Long id, UserDTORequest userDTORequest) {

        User usertoUpdate = iUserRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User not found with ID = %s".formatted(id)));

        usertoUpdate = MapperUser.INSTANCIA.userDTOToUser(userDTORequest);
        usertoUpdate.setId(id);
        iUserRepository.save(usertoUpdate);

        return MapperUser.INSTANCIA.userToUserDTO(usertoUpdate);
    }




}
