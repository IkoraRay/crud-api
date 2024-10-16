package com.onboarding.crud.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserInsertDto(
        @Email(message = "Please provide a valid email address.")
        @NotBlank(message = "Email cannot be blank.")
        String email,
        @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
        @NotBlank(message = "Name cannot be blank.")
        String name,
        @NotNull(message = "Password cannot be null.")
        String password,
        @NotNull(message = "Username cannot be null.")
        @NotBlank(message = "Username cannot be blank.")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters.")
        String username) {
}
