package com.todev.ma.order.pix;

import com.todev.ma.order.Order;
import com.todev.ma.order.OrderRepositoryWrapper;
import com.todev.ma.order.OrderStatus;
import com.todev.ma.order.PaymentMethod;
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
import java.util.Optional;

import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(value = {TestcontainersConfiguration.class})
class PixDetailRepositoryTest {
    @Autowired
    private PixDetailRepository pixDetailRepository;

    @Autowired
    private UserRepositoryWrapper userRepository;

    @Autowired
    private OrderRepositoryWrapper orderRepository;

    @AfterEach
    void tearDown() {
        pixDetailRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByOrderId_MustReturnAPixDetail_WhenOrderHasAPixDetail() {
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

        Order order = new Order(user, 25, LocalDateTime.now().plusDays(1L), PaymentMethod.PIX, OrderStatus.PENDING);

        PixDetail pixDetail = new PixDetail(order, "", "");

        userRepository.save(user);

        orderRepository.save(order);

        pixDetailRepository.save(pixDetail);

        Optional<PixDetail> retrieved = pixDetailRepository.findByOrderId(order.getId());

        assertTrue(retrieved.isPresent());
    }

    @Test
    void findByOrderId_MustNotReturnAPixDetail_WhenOrderDoesNotHaveAPixDetail() {
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

        Order order = new Order(user, 25, LocalDateTime.now().plusDays(1L), PaymentMethod.PIX, OrderStatus.PENDING);

        userRepository.save(user);

        orderRepository.save(order);

        Optional<PixDetail> retrieved = pixDetailRepository.findByOrderId(order.getId());

        assertTrue(retrieved.isEmpty());
    }
}