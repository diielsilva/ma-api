package com.todev.ma.order;

import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.todev.ma.order.OrderStatus.*;

@Service
class EnrollmentOrderService {
    private final EnrollmentOrderRepository orderRepository;
    private final IUserService userService;

    EnrollmentOrderService(EnrollmentOrderRepository orderRepository, IUserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    DisplayEnrollmentOrderResponseData create(Authentication session, CreateEnrollmentOrderRequestData data) {
        User user = userService.getSessionOwner(session);
        List<EnrollmentOrder> orders = orderRepository.findByUserId(user.getId());

        for (EnrollmentOrder order : orders) {
            if (order.getStatus().equals(WAITING_PAYMENT) || order.getStatus().equals(COMPLETED)) {
                throw new RuntimeException();
            }
        }

        if (data.value() < 10 || data.value() > 40) {
            throw new RuntimeException();
        }

    }
}
