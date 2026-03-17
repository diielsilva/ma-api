package com.todev.ma.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

record LoginUserRequestData(
        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email fornecido é inválido")
        String email,

        @NotBlank(message = "A senha não pode ser nula")
        @Size(min = 5, message = "A senha deve conter ao menos 5 caracteres")
        String password
) {
}
