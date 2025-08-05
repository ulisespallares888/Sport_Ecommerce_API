package com.tucompra.proyecto.v1.modules.users.model;

import com.tucompra.proyecto.v1.shared.exceptions.usuario.IlegalTypeUser;

public enum TypeUser {
    SELLER,
    BUYER;

    public static TypeUser fromString(String type) throws IlegalTypeUser {
        if (type == null) {
            throw new IlegalTypeUser("El type de usuario no puede ser nulo. Solo se permiten: BUYER o SELLER.");
        }
        switch (type.toUpperCase()) {
            case "BUYER": return BUYER;
            case "SELLER": return SELLER;
            default:
                throw new IlegalTypeUser("Tipo inválido: %s. Solo se permiten: BUYER o SELLER.".formatted(type));
        }
    }
}
