package com.tucompra.proyecto.v1.domain;

import com.tucompra.proyecto.v1.exceptions.usuario.IlegalTypeUser;

public enum TipoUsuario {
    VENDEDOR,
    COMPRADOR;

    public static TipoUsuario fromString(String tipo) throws IlegalTypeUser {
        if (tipo == null) {
            throw new IlegalTypeUser("El tipo de usuario no puede ser nulo. Solo se permiten: COMPRADOR o VENDEDOR.");
        }
        switch (tipo.toUpperCase()) {
            case "COMPRADOR": return COMPRADOR;
            case "VENDEDOR": return VENDEDOR;
            default:
                throw new IlegalTypeUser("Tipo inválido: %s. Solo se permiten: COMPRADOR o VENDEDOR.".formatted(tipo));
        }
    }
}
