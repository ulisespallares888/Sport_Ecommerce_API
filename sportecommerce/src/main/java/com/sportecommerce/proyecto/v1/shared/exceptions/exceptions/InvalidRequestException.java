package com.sportecommerce.proyecto.v1.shared.exceptions.exceptions;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
