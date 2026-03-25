package com.todev.ma.enrollment.order;

import com.todev.ma.enrollment.order.pix.IPixDetailService;
import com.todev.ma.enrollment.payment.CreatePaymentOrderResponseData;
import com.todev.ma.enrollment.payment.IPaymentOrderService;
import com.todev.ma.shared.ModelNotFoundException;
import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.enrollment.order.OrderStatus.CONFIRMED;
import static com.todev.ma.enrollment.order.OrderStatus.PENDING;
import static com.todev.ma.enrollment.order.PaymentMethod.PIX;

@Service
class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final IUserService userService;
    private final IPaymentOrderService paymentOrderService;
    private final IPixDetailService pixDetailService;

    OrderService(
            OrderRepository orderRepository,
            IUserService userService,
            IPaymentOrderService paymentOrderService,
            IPixDetailService pixDetailService
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

    @Override
    public Order findById(Long id) throws ModelNotFoundException {
        if (id == null || id <= 0) {
            throw new OrderNotFoundException();
        }

        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public void confirm(Order order) {
        order.setStatus(CONFIRMED);

        orderRepository.save(order);
    }
}
