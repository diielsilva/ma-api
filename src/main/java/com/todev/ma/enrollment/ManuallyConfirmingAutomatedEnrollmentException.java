package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class ManuallyConfirmingAutomatedEnrollmentException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "";

    ManuallyConfirmingAutomatedEnrollmentException() {
        super(EXCEPTION_MESSAGE);
    }
}
