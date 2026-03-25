package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.user.User;

public interface IManualConfirmationService {
    void create(User admin, Order order);
}
