package com.todev.ma.order;

import java.time.LocalDateTime;

record CreateOrderResponseData(
        Integer value,
        LocalDateTime dueDate,
        PaymentMethod paymentMethod,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
