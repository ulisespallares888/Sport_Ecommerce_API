package com.sportecommerce.proyecto.v1.modules.users.mapper;

import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTORequest;
import com.sportecommerce.proyecto.v1.modules.users.dto.UserDTOResponse;
import com.sportecommerce.proyecto.v1.modules.users.model.User;
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
    @Mapping(source = "role", target="role")
    UserDTOResponse userToUserDTO(User user);

    @Mapping(source = "name", target="name")
    @Mapping(source = "lastName", target="lastName")
    @Mapping(source = "typeUser", target="typeUser")
    @Mapping(target = "role", expression = "java(com.sportecommerce.proyecto.v1.modules.users.model.Role.CUSTOMER)")
    User userDTOToUser(UserDTORequest userDTORequest);




}
