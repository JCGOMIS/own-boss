package com.ownboss.own_boss.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//DTO DE CREATION
public record CreateUserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email ,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8characters")
        String password


) {
}
