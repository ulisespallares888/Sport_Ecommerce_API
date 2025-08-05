package com.tucompra.proyecto.v1.modules.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @NotBlank(message = "Name don´t be null")
    private String name;

    @NotBlank(message = "LastName don´t be null")
    private String lastName;

    @NotNull(message = "Type don´t be null")
    private String typeUser;

    @NotBlank(message = "Email don´t be null")
    @Email(message = "Email has´t valid format")
    private String email;
}
