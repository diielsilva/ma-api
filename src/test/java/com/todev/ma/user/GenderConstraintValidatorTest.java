package com.todev.ma.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderConstraintValidatorTest {
    private GenderConstraintValidator genderValidator;

    @BeforeEach
    void setUp() {
        genderValidator = new GenderConstraintValidator();
    }

    @Test
    void isValid_MustReturnTrue_WhenGenderValuesAreValid() {
        assertAll(() -> {
            assertTrue(genderValidator.isValid("MALE", null));
            assertTrue(genderValidator.isValid("male", null));
            assertTrue(genderValidator.isValid("FEMALE", null));
            assertTrue(genderValidator.isValid("female", null));
        });
    }

    @Test
    void isValid_MustReturnFalse_WhenGenderValuesAreInvalid() {
        assertAll(() -> {
            assertFalse(genderValidator.isValid("", null));
            assertFalse(genderValidator.isValid(null, null));
            assertFalse(genderValidator.isValid("other", null));
        });
    }
}