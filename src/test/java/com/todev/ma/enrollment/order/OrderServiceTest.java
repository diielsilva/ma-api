package com.todev.ma.enrollment.order;

import com.todev.ma.enrollment.order.pix.IPixDetailService;
import com.todev.ma.enrollment.payment.IPaymentOrderService;
import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.enrollment.order.OrderStatus.*;
import static com.todev.ma.enrollment.order.PaymentMethod.CASH;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.when;

@ExtendWith(value = MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IPaymentOrderService paymentOrderService;

    @Mock
    private IPixDetailService pixDetailService;

    @Test
    void create_MustCreateAnOrderAndAPixDetail_WhenUserHasNoOrders() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        CreateOrderRequestData data = new CreateOrderRequestData(25, "PIX");

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(user);

        when(orderRepository.findByUserId(any())).thenReturn(List.of());

        when(paymentOrderService.create(any(Order.class))).thenReturn(null);

        doNothing().when(pixDetailService).create(any(Order.class), any());

        assertDoesNotThrow(() -> orderService.create(session, data));
    }

    @Test
    void create_MustCreateAnOrder_WhenUserHasCancelledOrders() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(user, 25, LocalDateTime.now().minusDays(1), CASH, CANCELLED);

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        CreateOrderRequestData data = new CreateOrderRequestData(25, "CASH");

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(user);

        when(orderRepository.findByUserId(any())).thenReturn(List.of(order));

        assertDoesNotThrow(() -> orderService.create(session, data));
    }

    @Test
    void create_MustNotCreateAnOrder_WhenUserHasPendingOrders() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(user, 25, LocalDateTime.now().minusDays(1), CASH, PENDING);

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        CreateOrderRequestData data = new CreateOrderRequestData(25, "CASH");

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(user);

        when(orderRepository.findByUserId(any())).thenReturn(List.of(order));

        assertThrows(OrderWaitingConfirmationException.class, () -> orderService.create(session, data));
    }

    @Test
    void create_MustNotCreateAnOrder_WhenUserHasConfirmedOrders() {
        User user = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(user, 25, LocalDateTime.now().minusDays(1), CASH, CONFIRMED);

        Authentication session = new UsernamePasswordAuthenticationToken(user.getEmail(), null, null);

        CreateOrderRequestData data = new CreateOrderRequestData(25, "CASH");

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(user);

        when(orderRepository.findByUserId(any())).thenReturn(List.of(order));

        assertThrows(OrderAlreadyConfirmedException.class, () -> orderService.create(session, data));
    }
}