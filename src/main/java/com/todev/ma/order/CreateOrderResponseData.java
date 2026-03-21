package com.todev.ma.order;

import java.time.LocalDateTime;

record DisplayOrderResponseData(
        Integer value,
        LocalDateTime dueDate,
        PaymentMethod paymentMethod,
        OrderStatus status,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {
}
