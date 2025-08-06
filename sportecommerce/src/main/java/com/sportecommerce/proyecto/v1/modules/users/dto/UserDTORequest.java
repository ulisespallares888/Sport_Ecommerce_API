package com.sportecommerce.proyecto.v1.modules.users.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTORequest {
    private String name;

    private String lastName;

    private String typeUser;

    private String email;
}
