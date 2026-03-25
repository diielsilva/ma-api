package com.todev.ma.enrollment.order;

import java.time.LocalDateTime;

record CreateOrderResponseData(
        Integer value,
        LocalDateTime dueDate,
        PaymentMethod paymentMethod,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
