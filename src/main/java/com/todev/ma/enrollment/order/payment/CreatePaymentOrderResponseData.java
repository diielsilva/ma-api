package com.todev.ma.enrollment.order.payment;

import java.util.List;

public record CreatePaymentOrderResponseData(
        Long reference_id,
        PaymentCustomer customer,
        List<QrCodeResponseData> qr_codes
) {
}
