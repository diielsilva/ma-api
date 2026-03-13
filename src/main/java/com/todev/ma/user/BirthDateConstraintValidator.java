package com.todev.ma.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

class BirthDateConstraintValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();

        if (birthDate == null || birthDate.isAfter(now)) {
            return false;
        }

        Period period = Period.between(birthDate, now);

        return period.getYears() > 11 && period.getYears() < 121;
    }

}
