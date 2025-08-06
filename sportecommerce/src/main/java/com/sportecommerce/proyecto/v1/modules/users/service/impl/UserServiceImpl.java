package com.sportecommerce.proyecto.v1.modules.users.service.impl;

import com.sportecommerce.proyecto.v1.modules.users.model.TypeUser;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
import com.sportecommerce.proyecto.v1.modules.users.mapper.MapperUser;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.service.IUserService;
import com.sportecommerce.proyecto.v1.modules.users.repository.IUserRepository;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.DuplicateResourceException;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.IlegalTypeUser;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.InvalidRequestException;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.ResourceNotFoundException;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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

        validateUserDTORequest(userDTORequest);

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


    private void validateUserDTORequest(UserDTORequest userDTORequest) {
        boolean isUserDTORequestNull = Stream.of(
                userDTORequest.getName(),
                userDTORequest.getLastName(),
                userDTORequest.getEmail()
        ).allMatch(Objects::isNull);

        if (isUserDTORequestNull) {
            throw new InvalidRequestException("The user request cannot be null");
        }
        if (userDTORequest.getName() == null || userDTORequest.getName().isBlank()) {
            throw new InvalidRequestException("The name cannot be null or empty");
        }
        if (userDTORequest.getLastName() == null || userDTORequest.getLastName().isBlank()) {
            throw new InvalidRequestException("The last name cannot be null or empty");
        }
        if (iUserRepository.existsByEmail(userDTORequest.getEmail())) {
            throw new DuplicateResourceException("The user already exists with the email: %s".formatted(userDTORequest.getEmail()));
        }
        if (userDTORequest.getEmail() == null || userDTORequest.getEmail().isBlank()) {
            throw new InvalidRequestException("The email cannot be null or empty");
        }
        if (!userDTORequest.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidRequestException("The email format is invalid");
        }
        if (userDTORequest.getTypeUser() == null || userDTORequest.getTypeUser().isBlank()) {
            throw new IlegalTypeUser("The type user cannot be null or empty. Allowed types are: BUYER or SELLER.");
        }
        if (!userDTORequest.getTypeUser().equals("BUYER") &&
                !userDTORequest.getTypeUser().equals("SELLER")) {
            throw new IlegalTypeUser("Invalid type user: %s. Allowed types are: BUYER or SELLER.".formatted(userDTORequest.getTypeUser()));
        }

    }

}
