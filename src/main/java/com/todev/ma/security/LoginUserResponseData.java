package com.todev.ma.security;

import com.todev.ma.user.Gender;
import com.todev.ma.user.Role;

import java.time.LocalDate;

public record LoginUserResponseData(
        String name,
        String email,
        String cpf,
        String phone,
        Gender gender,
        Role role,
        LocalDate birthDate,
        String disabilities,
        String token
) {
}
