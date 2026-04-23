package com.todev.ma.enrollment;

import com.todev.ma.order.Order;
import com.todev.ma.order.OrderRepositoryWrapper;
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

import static com.todev.ma.enrollment.EnrollmentType.*;
import static com.todev.ma.order.OrderStatus.PENDING;
import static com.todev.ma.order.PaymentMethod.PIX;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(value = {TestcontainersConfiguration.class})
class EnrollmentRepositoryTest {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepositoryWrapper userRepository;

    @Autowired
    private OrderRepositoryWrapper orderRepository;

    @AfterEach
    void tearDown() {
        enrollmentRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByOrderUserId_MustReturnAnEnrollment_WhenUserHasARegisteredEnrollment() {
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

        Order order = new Order(
                user,
                25,
                LocalDateTime.now().plusDays(1L),
                PIX,
                PENDING
        );

        Enrollment enrollment = new Enrollment(order, AUTOMATED);

        userRepository.save(user);

        orderRepository.save(order);

        enrollmentRepository.save(enrollment);

        Optional<Enrollment> retrieved = enrollmentRepository.findByOrderUserId(user.getId());

        assertTrue(retrieved.isPresent());
    }

    @Test
    void findByOrderUserId_MustNotReturnAnEnrollment_WhenUserHasNotARegisteredEnrollment() {
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

        Order order = new Order(
                user,
                25,
                LocalDateTime.now().plusDays(1L),
                PIX,
                PENDING
        );

        Enrollment enrollment = new Enrollment(order, AUTOMATED);

        userRepository.save(user);

        orderRepository.save(order);

        enrollmentRepository.save(enrollment);

        Optional<Enrollment> retrieved = enrollmentRepository.findByOrderUserId(0L);

        assertTrue(retrieved.isEmpty());
    }
}