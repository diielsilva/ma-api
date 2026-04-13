package com.todev.ma.order.pix;

import com.todev.ma.order.Order;
import com.todev.ma.order.payment.CreatePaymentOrderResponseData;

public interface IPixDetailService {
    void create(Order order, CreatePaymentOrderResponseData data);

    DisplayPixDetailResponseData findByOrderId(Long orderId);
}
