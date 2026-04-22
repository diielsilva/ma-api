package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class MissingChargeDetailException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "Os detalhes do pagamento não foram fornecidos";

    MissingChargeDetailException() {
        super(EXCEPTION_MESSAGE);
    }
}
