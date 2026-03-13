package com.todev.ma.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class GenderConstraintValidator implements ConstraintValidator<GenderConstraint, String> {

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        if (gender == null || gender.isBlank()) {
            return false;
        }

        return gender.equalsIgnoreCase("MALE") || gender.equalsIgnoreCase("FEMALE");
    }

}
