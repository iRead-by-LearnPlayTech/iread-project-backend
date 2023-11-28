package com.iread.backend.project.exception;

public class MissingRequiredFieldsException extends RuntimeException{
    public MissingRequiredFieldsException(String fieldName) {
        super("El campo '" + fieldName + "' es requerido y no puede ser nulo.");
    }
}
