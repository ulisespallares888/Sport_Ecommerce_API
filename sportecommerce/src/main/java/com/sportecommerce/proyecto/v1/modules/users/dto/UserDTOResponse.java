package com.sportecommerce.proyecto.v1.modules.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTOResponse {
    private long id;
    private String name;
    private String lastName;
    private String typeUser;
    private String email;
    private String role;
}
