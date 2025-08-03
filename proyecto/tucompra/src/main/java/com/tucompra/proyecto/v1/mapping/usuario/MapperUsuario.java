package com.tucompra.proyecto.v1.mapping.usuario;

import com.tucompra.proyecto.v1.domain.Usuario;
import com.tucompra.proyecto.v1.dto.responses.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperUsuario {

    MapperUsuario INSTANCIA = Mappers.getMapper(MapperUsuario.class);

    @Mapping(source = "nombre", target="nombre")
    @Mapping(source = "apellido", target="apellido")
    @Mapping(source = "tipo", target="tipo")
    @Mapping(source = "email", target="email")
    UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);

    @Mapping(source = "nombre", target="nombre")
    @Mapping(source = "apellido", target="apellido")
    @Mapping(source = "email", target="email")
    Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO);


}
