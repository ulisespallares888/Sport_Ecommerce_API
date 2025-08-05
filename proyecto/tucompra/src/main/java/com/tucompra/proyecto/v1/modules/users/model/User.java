package com.tucompra.proyecto.v1.modules.users.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;


    @Column(nullable = false)
    private String name;

    @Column
    private String lastName;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String tel;

    @Column
    private String address;

    @Column
    private String city;

}
