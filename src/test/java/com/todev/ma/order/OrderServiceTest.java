package com.todev.ma.order;

import com.todev.ma.order.payment.IPaymentOrderService;
import com.todev.ma.order.pix.DisplayPixDetailResponseData;
import com.todev.ma.order.pix.IPixDetailService;
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
import java.util.Optional;

import static com.todev.ma.order.OrderStatus.*;
import static com.todev.ma.order.PaymentMethod.CASH;
import static com.todev.ma.order.PaymentMethod.PIX;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

    @Test
    void findById_MustReturnAnOrder_WhenOrderIdWasFound() {
        Order order = new Order(null, 25, LocalDateTime.now().minusDays(1), CASH, CONFIRMED);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> orderService.findById(1L));
    }

    @Test
    void findById_MustNotReturnAnOrder_WhenIdIsNullOrSmallerThanZero() {
        assertAll(() -> {
            assertThrows(OrderNotFoundException.class, () -> orderService.findById(null));
            assertThrows(OrderNotFoundException.class, () -> orderService.findById(-1L));
        });
    }

    @Test
    void findById_MustNotReturnAnOrder_WhenIdWasNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.findById(1L));
    }

    @Test
    void confirm_MustConfirmAnOrder_WhenNoExceptionIsThrown() {
        Order order = new Order(null, 25, LocalDateTime.now().minusDays(1), CASH, PENDING);

        assertDoesNotThrow(() -> orderService.confirm(order));
    }

    @Test
    void findAll_MustReturnAnOrdersList_WhenUserHasRegisteredOrders() {
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

        Order order = new Order(user, 25, LocalDateTime.now().minusDays(1), CASH, CONFIRMED);

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(user);

        when(orderRepository.findByUserId(any())).thenReturn(List.of(order));

        List<DisplayOrderResponseData> data = orderService.findAll(session);

        assertFalse(data.isEmpty());
    }

    @Test
    void findAllByUserCpf_MustReturnAnOrderList_WhenUserHasRegisteredOrders() {
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

        when(userService.findByCpf(anyString())).thenReturn(user);

        when(orderRepository.findByUserId(any())).thenReturn(List.of(order));

        List<DisplayOrderResponseData> data = orderService.findAllByUserCpf("54463972906");

        assertFalse(data.isEmpty());
    }

    @Test
    void findPixDetailsByOrderId_MustReturnAPixDetails_WhenNoExceptionIsThrown() {
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

        Order order = new Order(user, 25, LocalDateTime.now().plusDays(1), PIX, PENDING);

        DisplayPixDetailResponseData details = new DisplayPixDetailResponseData("", "");

        user.setId(1L);

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(user);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        when(pixDetailService.findByOrderId(anyLong())).thenReturn(details);

        assertDoesNotThrow(() -> orderService.findPixDetailsByOrderId(session, 1L));
    }

    @Test
    void findPixDetailsByOrderId_MustNotReturnAPixDetail_WhenSelectedOrderDoesNotBelongToSessionOwner() {
        User bento = new User(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                MALE,
                USER,
                LocalDate.of(2000, 3, 15),
                null
        );

        User martin = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                USER,
                LocalDate.of(1972, 8, 2)
        );

        Authentication session = new UsernamePasswordAuthenticationToken(bento.getEmail(), null, null);

        Order order = new Order(martin, 25, LocalDateTime.now().plusDays(1), PIX, PENDING);

        bento.setId(1L);

        martin.setId(2L);

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(bento);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(UnauthorizedOrderAccessException.class, () -> orderService.findPixDetailsByOrderId(session, 1L));
    }

    @Test
    void findPixDetailsByOrderId_MustNotReturnAPixDetail_WhenOrderPaymentMethodIsEqualsToCash() {
        User bento = new User(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                MALE,
                USER,
                LocalDate.of(2000, 3, 15),
                null
        );

        Authentication session = new UsernamePasswordAuthenticationToken(bento.getEmail(), null, null);

        Order order = new Order(bento, 25, LocalDateTime.now().plusDays(1), CASH, PENDING);

        bento.setId(1L);

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(bento);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(OrderDoesNotHavePixDetailException.class, () -> orderService.findPixDetailsByOrderId(session, 1L));
    }

    @Test
    void findPixDetailsByOrderId_MustNotReturnAPixDetail_WhenOrderIsNotPending() {
        User bento = new User(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                MALE,
                USER,
                LocalDate.of(2000, 3, 15),
                null
        );

        Authentication session = new UsernamePasswordAuthenticationToken(bento.getEmail(), null, null);

        Order order = new Order(bento, 25, LocalDateTime.now().plusDays(1), PIX, CANCELLED);

        bento.setId(1L);

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(bento);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(NonPendingOrderAccessException.class, () -> orderService.findPixDetailsByOrderId(session, 1L));
    }

    @Test
    void findPixDetailsByOrderId_MustNotReturnAPixDetail_WhenOrderIsExpired() {
        User bento = new User(
                "Bento João Alexandre Duarte",
                "bento_duarte@queirozgalvao.com",
                "mOXNClJCRD",
                "18881451026",
                "63988968264",
                MALE,
                USER,
                LocalDate.of(2000, 3, 15),
                null
        );

        Authentication session = new UsernamePasswordAuthenticationToken(bento.getEmail(), null, null);

        Order order = new Order(bento, 25, LocalDateTime.now().minusDays(1), PIX, PENDING);

        bento.setId(1L);

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(bento);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        assertThrows(ExpiredPixDetailException.class, () -> orderService.findPixDetailsByOrderId(session, 1L));
    }
}