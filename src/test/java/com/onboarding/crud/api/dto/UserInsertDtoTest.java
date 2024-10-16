package com.onboarding.crud.api.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserInsertDtoTest {

    private final Validator validator;

    public UserInsertDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenValidInput_whenValidatingUserDto_thenNoValidationErrors() {
        // Given
        UserInsertDto userDto = new UserInsertDto(
                "test@example.com",
                "John Doe",
                "Test@1234",
                "johndoe"
        );

        // When
        Set<ConstraintViolation<UserInsertDto>> violations = validator.validate(userDto);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenInvalidEmail_whenValidatingUserDto_thenValidationErrorsReported() {
        // Given
        UserInsertDto userDto = new UserInsertDto(
                "invalid_email",
                "Jane Smith",
                "Test@1234",
                "janesmith"
        );

        // When
        Set<ConstraintViolation<UserInsertDto>> violations = validator.validate(userDto);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals("Please provide a valid email address.", violations.iterator().next().getMessage());
    }
}
