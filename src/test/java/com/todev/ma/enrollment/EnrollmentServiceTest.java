package com.todev.ma.enrollment;

import com.todev.ma.order.IOrderService;
import com.todev.ma.order.Order;
import com.todev.ma.enrollment.confirmation.IManualConfirmationService;
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

import static com.todev.ma.order.OrderStatus.CANCELLED;
import static com.todev.ma.order.OrderStatus.PENDING;
import static com.todev.ma.order.PaymentMethod.CASH;
import static com.todev.ma.order.PaymentMethod.PIX;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.when;

@ExtendWith(value = MockitoExtension.class)
class EnrollmentServiceTest {
    @InjectMocks
    private EnrollmentService enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private IOrderService orderService;

    @Mock
    private IUserService userService;

    @Mock
    private IManualConfirmationService confirmationService;

    @Test
    void create_MustCreateAnEnrollment_WhenNoExceptionIsThrown() {
        User admin = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                ADMIN,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(admin, 25, LocalDateTime.now().plusDays(1), CASH, PENDING);

        Authentication session = new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword());

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(admin);

        when(orderService.findById(anyLong())).thenReturn(order);

        assertDoesNotThrow(() -> enrollmentService.create(session, 1L));
    }

    @Test
    void create_MustNotCreateAnEnrollment_WhenOrderStatusIsNotPending() {
        User admin = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                ADMIN,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(admin, 25, LocalDateTime.now().minusDays(1), CASH, CANCELLED);

        Authentication session = new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword());

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(admin);

        when(orderService.findById(anyLong())).thenReturn(order);

        assertThrows(InvalidOrderStatusException.class, () -> enrollmentService.create(session, 2L));
    }

    @Test
    void create_MustNotCreateAnEnrollment_WhenOrderIsExpired() {
        User admin = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                ADMIN,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(admin, 25, LocalDateTime.now().minusDays(1), CASH, PENDING);

        Authentication session = new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword());

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(admin);

        when(orderService.findById(anyLong())).thenReturn(order);

        assertThrows(OrderExpiredException.class, () -> enrollmentService.create(session, 2L));
    }

    @Test
    void create_MustNotCreateAnEnrollment_WhenTryingToConfirmAnAutomatedEnrollment() {
        User admin = new User(
                "Martin Ryan Pedro Aparício",
                "martin-aparicio96@sp.senac.com.br",
                "hQbzZZHANd",
                "54463972906",
                "27991911428",
                MALE,
                ADMIN,
                LocalDate.of(1972, 8, 2)
        );

        Order order = new Order(admin, 25, LocalDateTime.now().plusDays(1), PIX, PENDING);

        Authentication session = new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPassword());

        when(userService.getSessionOwner(any(Authentication.class))).thenReturn(admin);

        when(orderService.findById(anyLong())).thenReturn(order);

        assertThrows(ManuallyConfirmingAutomatedEnrollmentException.class, () -> enrollmentService.create(session, 2L));
    }
}