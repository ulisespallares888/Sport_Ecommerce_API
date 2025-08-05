package com.tucompra.proyecto.v1.modules.users.mapper;

import com.tucompra.proyecto.v1.modules.users.dto.UserDTO;
import com.tucompra.proyecto.v1.modules.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperUser {

    MapperUser INSTANCIA = Mappers.getMapper(MapperUser.class);

    @Mapping(source = "name", target="name")
    @Mapping(source = "lastName", target="lastName")
    @Mapping(source = "typeUser", target="typeUser")
    @Mapping(source = "email", target="email")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "name", target="name")
    @Mapping(source = "lastName", target="lastName")
    @Mapping(source = "email", target="email")
    User userDTOToUser(UserDTO userDTO);


}
