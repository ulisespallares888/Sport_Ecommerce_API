package com.tucompra.proyecto.v1.exceptions.usuario;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}