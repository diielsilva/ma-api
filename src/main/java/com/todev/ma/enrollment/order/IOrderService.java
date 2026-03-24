package com.todev.ma.enrollment.order;

import com.todev.ma.shared.ModelNotFoundException;

public interface IOrderService {
    Order findById(Long id) throws ModelNotFoundException;
}
