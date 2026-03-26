package com.todev.ma.enrollment.order.pix;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.enrollment.order.payment.CreatePaymentOrderResponseData;

public interface IPixDetailService {
    void create(Order order, CreatePaymentOrderResponseData data);
}
