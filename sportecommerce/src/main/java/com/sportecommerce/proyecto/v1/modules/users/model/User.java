package com.sportecommerce.proyecto.v1.modules.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column
    @JsonProperty("lastname")
    private String lastName;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    @Column
    private String password;

    @Column
    private String tel;

    @Column
    private String address;

    @Column
    private String city;

}
