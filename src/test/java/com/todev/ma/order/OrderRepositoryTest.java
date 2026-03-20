package com.todev.ma.order;

import com.todev.ma.user.UserRepositoryWrapper;
import com.todev.ma.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(value = {TestcontainersConfiguration.class})
class EnrollmentOrderRepositoryTest {
    @Autowired
    private EnrollmentOrderRepository enrollmentOrderRepository;

    @Autowired
    private UserRepositoryWrapper userRepository;

    @AfterEach
    void tearDown() {
        enrollmentOrderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByUserId_MustReturnANonEmptyEnrollmentOrderList_WhenUserHasRegisteredEnrollmentOrders() {
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

        userRepository.save(user);

        EnrollmentOrder order = new EnrollmentOrder(
                user,
                25,
                LocalDateTime.now().plusDays(1L),
                PaymentMethod.PIX,
                OrderStatus.WAITING_PAYMENT
        );

        enrollmentOrderRepository.save(order);

        List<EnrollmentOrder> orders = enrollmentOrderRepository.findByUserId(user.getId());

        assertEquals(1, orders.size());
    }

    @Test
    void findByUserId_MustReturnAnEmptyEnrollmentOrderList_WhenUserHasNoRegisteredEnrollmentOrders() {
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

        userRepository.save(user);

        List<EnrollmentOrder> orders = enrollmentOrderRepository.findByUserId(user.getId());

        assertTrue(orders.isEmpty());
    }
}