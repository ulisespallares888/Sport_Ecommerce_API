package com.sportecommerce.proyecto.v1.shared.exceptions.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}