package com.todev.ma.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static java.time.LocalDate.of;

class BirthDateConstraintValidatorTest {
    private BirthDateConstraintValidator birthDateValidator;

    @BeforeEach
    void setUp() {
        birthDateValidator = new BirthDateConstraintValidator();
    }

    @Test
    void isValid_MustReturnTrue_WhenBirthDateValuesAreValid() {
        assertAll(() -> {
            assertTrue(birthDateValidator.isValid(of(1997, 10, 23), null));
            assertTrue(birthDateValidator.isValid(of(2000, 4, 24), null));
            assertTrue(birthDateValidator.isValid(of(1963, 9, 9), null));
        });
    }

    @Test
    void isValid_MustReturnFalse_WhenBirthDateValuesAreInvalid() {
        assertAll(() -> {
            assertFalse(birthDateValidator.isValid(of(2016, 10, 23), null));
            assertFalse(birthDateValidator.isValid(of(1900, 4, 24), null));
            assertFalse(birthDateValidator.isValid(of(2027, 9, 9), null));
            assertFalse(birthDateValidator.isValid(null, null));
        });
    }
}