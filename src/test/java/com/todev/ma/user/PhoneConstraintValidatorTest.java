package com.todev.ma.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PhoneConstraintValidatorTest {
    private PhoneConstraintValidator phoneValidator;

    @BeforeEach
    void setUp() {
        phoneValidator = new PhoneConstraintValidator();
    }

    @Test
    void isValid_MustReturnTrue_WhenPhoneValuesAreValid() {
        assertAll(() -> {
            assertFalse(phoneValidator.isValid("31983505752", null));
            assertFalse(phoneValidator.isValid("63992243555", null));
            assertFalse(phoneValidator.isValid("79994201305", null));
        });
    }

    @Test
    void isValid_MustReturnFalse_WhenPhoneValuesAreInvalid() {
        assertAll(() -> {
            assertFalse(phoneValidator.isValid("", null));
            assertFalse(phoneValidator.isValid("12345", null));
            assertFalse(phoneValidator.isValid("12345678901", null));
            assertFalse(phoneValidator.isValid("1234567890123", null));
            assertFalse(phoneValidator.isValid(null, null));
        });
    }
}