package com.tucompra.proyecto.v1.exceptions.usuario;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}