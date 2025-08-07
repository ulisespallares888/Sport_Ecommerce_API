package com.sportecommerce.proyecto.v1.modules.users.validation;

import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.repository.IUserRepository;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.DuplicateResourceException;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.IlegalTypeUser;
import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.InvalidRequestException;

import java.util.Objects;
import java.util.stream.Stream;

public class ValidatorUser {
    public static void validateUserDTORequest(UserDTORequest userDTORequest,
                                        IUserRepository iUserRepository) {
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
