package com.onboarding.crud.api.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public final String field;
    public ResourceNotFoundException (String message, String field) {

        super(message);
        this.field = field;
    }
}
