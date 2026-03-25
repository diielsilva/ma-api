package com.todev.ma.enrollment.order;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class PaymentMethodConstraintValidator implements ConstraintValidator<PaymentMethodConstraint, String> {
    @Override
    public boolean isValid(String paymentMethod, ConstraintValidatorContext context) {
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            return false;
        }

        paymentMethod = paymentMethod.trim().toUpperCase();

        return paymentMethod.equals(PaymentMethod.PIX.name()) || paymentMethod.equals(PaymentMethod.CASH.name());
    }
}
