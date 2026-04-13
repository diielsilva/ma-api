package com.todev.ma.order;

import com.todev.ma.order.payment.CreatePaymentOrderResponseData;
import com.todev.ma.order.payment.IPaymentOrderService;
import com.todev.ma.order.pix.DisplayPixDetailResponseData;
import com.todev.ma.order.pix.IPixDetailService;
import com.todev.ma.shared.ModelNotFoundException;
import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.order.OrderStatus.*;
import static com.todev.ma.order.PaymentMethod.PIX;

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
    void create(Authentication session, CreateOrderRequestData data) {
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
    }

    @Transactional
    List<DisplayOrderResponseData> findAll(Authentication session) {
        User user = userService.getSessionOwner(session);
        List<Order> orders = orderRepository.findByUserId(user.getId());
        LocalDateTime now = LocalDateTime.now();

        for (Order order : orders) {
            boolean shouldCancelOrder = order.getDueDate().isBefore(now) && order.getStatus().equals(PENDING);

            if (shouldCancelOrder) {
                order.setStatus(CANCELLED);

                orderRepository.save(order);
            }
        }

        return orders.stream().map(order ->
                new DisplayOrderResponseData(
                        order.getId(),
                        order.getValue(),
                        order.getDueDate(),
                        order.getPaymentMethod(),
                        order.getStatus(),
                        order.getCreatedAt()
                )).toList();
    }

    @Transactional
    List<DisplayOrderResponseData> findAllByUserCpf(String cpf) {
        User user = userService.findByCpf(cpf);
        List<Order> orders = orderRepository.findByUserId(user.getId());
        LocalDateTime now = LocalDateTime.now();

        for (Order order : orders) {
            boolean shouldCancelOrder = order.getDueDate().isBefore(now) && order.getStatus().equals(PENDING);

            if (shouldCancelOrder) {
                order.setStatus(CANCELLED);

                orderRepository.save(order);
            }
        }


        return orders.stream().map(order ->
                new DisplayOrderResponseData(
                        order.getId(),
                        order.getValue(),
                        order.getDueDate(),
                        order.getPaymentMethod(),
                        order.getStatus(),
                        order.getCreatedAt()
                )).toList();
    }

    DisplayPixDetailResponseData findPixDetailsByOrderId(Authentication session, Long orderId) {
        User user = userService.getSessionOwner(session);
        Order order = findById(orderId);

        boolean orderDoesNotBelongToSessionOwner = !user.getId().equals(order.getUser().getId());

        if (orderDoesNotBelongToSessionOwner) {
            throw new UnauthorizedOrderAccessException();
        }

        boolean isAManualOrder = !order.getPaymentMethod().equals(PIX);

        if (isAManualOrder) {
            throw new OrderDoesNotHavePixDetailException();
        }

        boolean isANonPendingOrder = !order.getStatus().equals(PENDING);

        if (isANonPendingOrder) {
            throw new NonPendingOrderAccessException();
        }

        boolean isAnExpiredOrder = order.getDueDate().isBefore(LocalDateTime.now());

        if (isAnExpiredOrder) {
            throw new ExpiredPixDetailException();
        }

        return pixDetailService.findByOrderId(orderId);
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
