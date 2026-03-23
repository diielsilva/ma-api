package com.todev.ma.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodConstraintValidatorTest {
    private PaymentMethodConstraintValidator paymentMethodValidator;

    @BeforeEach
    void setUp() {
        paymentMethodValidator = new PaymentMethodConstraintValidator();
    }

    @Test
    void isValid_MustReturnTrue_WhenPaymentMethodValuesAreValid() {
        assertAll(() -> {
            assertTrue(paymentMethodValidator.isValid("CASH", null));
            assertTrue(paymentMethodValidator.isValid("PIX", null));
            assertTrue(paymentMethodValidator.isValid("cash", null));
            assertTrue(paymentMethodValidator.isValid("pix", null));
        });
    }

    @Test
    void isValid_MustReturnFalse_WhenPaymentMethodValuesAreInvalid() {
        assertAll(() -> {
            assertFalse(paymentMethodValidator.isValid(null, null));
            assertFalse(paymentMethodValidator.isValid("", null));
            assertFalse(paymentMethodValidator.isValid("CARD", null));
        });
    }
}