package com.todev.ma.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

record UpdateUserRequestData(
        @NotBlank(message = "O nome não pode ser nulo")
        @Size(min = 3, message = "O nome deve conter ao menos 3 caracteres")
        String name,

        @NotNull(message = "O email não pode ser nulo")
        @Email(message = "O email fornecido é inválido")
        String email,

        @Size(min = 5, message = "A senha deve conter ao menos 5 caracteres")
        String password,

        @NotNull(message = "O CPF não pode ser nulo")
        @CPF(message = "O CPF fornecido é inválido")
        String cpf,

        @NotNull(message = "O telefone não pode ser nulo")
        @PhoneConstraint(message = "O telefone fornecido é inválido")
        String phone,

        @NotNull(message = "O gênero não pode ser nulo")
        @GenderConstraint(message = "O gênero fornecido é inválido")
        String gender,

        @NotNull(message = "A data de nascimento não pode ser nula")
        @BirthDateConstraint(message = "O participante deve possuir uma idade entre 12 e 120 anos")
        LocalDate birthDate,

        String disabilities
) {
}
