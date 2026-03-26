package com.todev.ma.order;

import java.time.LocalDateTime;

record DisplayOrderResponseData(
        Long id,
        Integer value,
        LocalDateTime dueDate,
        PaymentMethod paymentMethod,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
