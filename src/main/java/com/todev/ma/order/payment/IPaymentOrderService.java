package com.todev.ma.order.payment;

import com.todev.ma.order.Order;
import com.todev.ma.shared.InternalApplicationException;

public interface IPaymentOrderService {
    CreatePaymentOrderResponseData create(Order order) throws InternalApplicationException;
}
