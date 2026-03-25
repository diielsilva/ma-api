package com.todev.ma.enrollment.payment;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.shared.InternalApplicationException;

public interface IPaymentOrderService {
    CreatePaymentOrderResponseData create(Order order) throws InternalApplicationException;
}
