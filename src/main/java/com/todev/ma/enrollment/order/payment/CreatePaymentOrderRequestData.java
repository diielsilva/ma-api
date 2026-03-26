package com.todev.ma.enrollment.order.payment;

import java.util.List;

record CreatePaymentOrderRequestData(
        Long reference_id,
        PaymentCustomer customer,
        List<QrCodeRequestData> qr_codes,
        List<String> notification_urls
) {
}
