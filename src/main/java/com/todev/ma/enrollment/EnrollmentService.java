package com.todev.ma.enrollment;

import com.todev.ma.enrollment.confirmation.IManualConfirmationService;
import com.todev.ma.order.IOrderService;
import com.todev.ma.order.Order;
import com.todev.ma.user.IUserService;
import com.todev.ma.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.todev.ma.enrollment.EnrollmentType.MANUAL;
import static com.todev.ma.order.OrderStatus.PENDING;
import static com.todev.ma.order.PaymentMethod.CASH;

@Service
class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final IUserService userService;
    private final IOrderService orderService;
    private final IManualConfirmationService confirmationService;

    EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            IUserService userService,
            IOrderService orderService,
            IManualConfirmationService confirmationService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.userService = userService;
        this.orderService = orderService;
        this.confirmationService = confirmationService;
    }

    @Transactional
    void create(Authentication session, Long orderId) {
        User admin = userService.getSessionOwner(session);
        Order order = orderService.findById(orderId);
        LocalDateTime now = LocalDateTime.now();

        if (order.getStatus() != PENDING) {
            throw new InvalidOrderStatusException();
        }

        if (order.getDueDate().isBefore(now)) {
            throw new OrderExpiredException();
        }

        if (!order.getPaymentMethod().equals(CASH)) {
            throw new ManuallyConfirmingAutomatedEnrollmentException();
        }

        Enrollment enrollment = new Enrollment(order, MANUAL);

        enrollmentRepository.save(enrollment);

        orderService.confirm(order);

        confirmationService.create(admin, enrollment);
    }
}
