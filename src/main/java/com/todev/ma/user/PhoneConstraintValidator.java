package com.todev.ma.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class PhoneConstraintValidator implements ConstraintValidator<PhoneConstraint, String> {
    private static final String PHONE_REGEX = "^(?:55)?\\d{2}9\\d{8}$";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.length() != 11) {
            return false;
        }

        return phone.matches(PHONE_REGEX);
    }

}
