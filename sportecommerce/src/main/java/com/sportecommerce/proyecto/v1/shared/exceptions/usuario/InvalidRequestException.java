package com.sportecommerce.proyecto.v1.shared.exceptions.usuario;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
