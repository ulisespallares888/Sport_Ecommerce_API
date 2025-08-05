package com.tucompra.proyecto.v1.shared.exceptions.usuario;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}