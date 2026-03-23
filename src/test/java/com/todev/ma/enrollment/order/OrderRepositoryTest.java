package com.todev.ma.enrollment.order;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.enrollment.order.OrderRepository;
import com.todev.ma.enrollment.order.PixDetailRepository;
import com.todev.ma.user.User;
import com.todev.ma.user.UserRepositoryWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.enrollment.order.OrderStatus.PENDING;
import static com.todev.ma.enrollment.order.PaymentMethod.PIX;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(value = {TestcontainersConfiguration.class})
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PixDetailRepository pixDetailRepository;

    @Autowired
    private UserRepositoryWrapper userRepository;

    @AfterEach
    void tearDown() {
        pixDetailRepository.deleteAll();
        orderRepository.deleteAll();
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

        Order order = new Order(
                user,
                25,
                LocalDateTime.now().plusDays(1L),
                PIX,
                PENDING
        );

        orderRepository.save(order);

        List<Order> orders = orderRepository.findByUserId(user.getId());

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

        List<Order> orders = orderRepository.findByUserId(user.getId());

        assertTrue(orders.isEmpty());
    }
}