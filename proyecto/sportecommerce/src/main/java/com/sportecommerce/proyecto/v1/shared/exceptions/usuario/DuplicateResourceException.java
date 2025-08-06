package com.sportecommerce.proyecto.v1.shared.exceptions.usuario;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}