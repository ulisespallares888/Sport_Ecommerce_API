package com.sportecommerce.proyecto.v1.shared.exceptions.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}