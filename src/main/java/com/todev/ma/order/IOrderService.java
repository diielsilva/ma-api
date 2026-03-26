package com.todev.ma.order;

import com.todev.ma.shared.ModelNotFoundException;

public interface IOrderService {
    Order findById(Long id) throws ModelNotFoundException;

    void confirm(Order order);
}
