package com.todev.ma.order;

import com.todev.ma.payment.CreatePaymentOrderResponseData;
import com.todev.ma.payment.IPaymentOrderService;
import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.order.OrderStatus.CONFIRMED;
import static com.todev.ma.order.OrderStatus.PENDING;
import static com.todev.ma.order.PaymentMethod.PIX;

@Service
class OrderService {
    private final OrderRepository orderRepository;
    private final IUserService userService;
    private final IPaymentOrderService paymentOrderService;
    private final PixDetailService pixDetailService;

    OrderService(
            OrderRepository orderRepository,
            IUserService userService,
            IPaymentOrderService paymentOrderService,
            PixDetailService pixDetailService
    ) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.paymentOrderService = paymentOrderService;
        this.pixDetailService = pixDetailService;
    }

    @Transactional
    CreateOrderResponseData create(Authentication session, CreateOrderRequestData data) {
        User user = userService.getSessionOwner(session);
        List<Order> orders = orderRepository.findByUserId(user.getId());

        for (Order order : orders) {
            if (order.getStatus().equals(PENDING)) {
                throw new OrderWaitingConfirmationException();
            }

            if (order.getStatus().equals(CONFIRMED)) {
                throw new OrderAlreadyConfirmedException();
            }
        }

        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        Order order = new Order(
                user,
                data.value(),
                dueDate,
                PaymentMethod.valueOf(data.paymentMethod()),
                PENDING
        );

        orderRepository.save(order);

        if (order.getPaymentMethod().equals(PIX)) {
            CreatePaymentOrderResponseData paymentOrder = paymentOrderService.create(order);

            pixDetailService.create(order, paymentOrder);
        }

        return new CreateOrderResponseData(
                order.getValue(),
                order.getDueDate(),
                order.getPaymentMethod(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

}
