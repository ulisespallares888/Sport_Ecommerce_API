package com.tucompra.proyecto.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
public class Usuario {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    @JsonIgnore
    private UUID id;


    @Column(nullable = false)
    private String nombre;

    @Column
    private String apellido;

    @Column
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    @Column
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String telefono;

    @Column
    private String direccion;

    @Column
    private String ciudad;

}
