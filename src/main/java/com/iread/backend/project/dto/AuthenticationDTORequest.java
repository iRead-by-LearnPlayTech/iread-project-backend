package com.iread.backend.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTORequest {
    @NotBlank(message = "Por favor ingresa un correo electrónico.")
    @Email(message = "El correo electrónico debe ser válido.")
    private String email;

    @NotBlank(message = "Por favor ingresa una contraseña")
    private String password;
}
