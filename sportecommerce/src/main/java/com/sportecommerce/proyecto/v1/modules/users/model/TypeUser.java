package com.sportecommerce.proyecto.v1.modules.users.model;

import com.sportecommerce.proyecto.v1.shared.exceptions.usuario.IlegalTypeUser;

public enum TypeUser {
    SELLER,
    BUYER;

    public static TypeUser fromString(String type) throws IlegalTypeUser {
        if (type == null) {
            throw new IlegalTypeUser("Type from user don't be null. Allows only: BUYER o SELLER.");
        }
        switch (type.toUpperCase()) {
            case "BUYER": return BUYER;
            case "SELLER": return SELLER;
            default:
                throw new IlegalTypeUser("Invalid type: %s. Allows only: BUYER o SELLER.".formatted(type));
        }
    }
}
