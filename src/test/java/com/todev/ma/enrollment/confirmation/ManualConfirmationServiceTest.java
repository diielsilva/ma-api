package com.todev.ma.enrollment.confirmation;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.todev.ma.enrollment.order.OrderStatus.PENDING;
import static com.todev.ma.enrollment.order.PaymentMethod.CASH;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(value = MockitoExtension.class)
class ManualConfirmationServiceTest {
    @InjectMocks
    private ManualConfirmationService manualConfirmationService;

    @Mock
    private ManualConfirmationRepository manualConfirmationRepository;

    @Test
    void create_MustCreateAManualConfirmation_WhenNoExceptionIsThrown() {
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

        Order order = new Order(user, 25, LocalDateTime.now().plusDays(1L), CASH, PENDING);

        assertDoesNotThrow(() -> manualConfirmationService.create(user, order));
    }
}