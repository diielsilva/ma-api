package com.todev.ma.enrollment.order.payment;

import com.todev.ma.shared.InternalApplicationException;

class PaymentGatewayCommunicationException extends InternalApplicationException {
    private static final String EXCEPTION_MESSAGE = "Não foi possível se comunicar com o gateway de pagamento";

    PaymentGatewayCommunicationException() {
        super(EXCEPTION_MESSAGE);
    }
}
