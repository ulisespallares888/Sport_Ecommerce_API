package com.tucompra.proyecto.v1.exceptions.usuario;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}