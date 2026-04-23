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
import java.util.List;
import java.util.Optional;

import static com.todev.ma.enrollment.EnrollmentType.AUTOMATED;
import static com.todev.ma.enrollment.EnrollmentType.MANUAL;
import static com.todev.ma.order.OrderStatus.PENDING;
import static com.todev.ma.order.PaymentMethod.CASH;
import static com.todev.ma.order.PaymentMethod.PIX;

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

    //TODO Verify if it was sent by PagBank
    @Transactional
    void create(CreateEnrollmentRequestData request) {
        Long orderId = request.reference_id();
        Order order = orderService.findById(orderId);
        LocalDateTime now = LocalDateTime.now();

        if (request.charges().isEmpty()) {
            throw new MissingChargeDetailException();
        }

        EnrollmentChargeDetail charge = request.charges().getFirst();
        EnrollmentChargeAmount paidAmount = charge.amount();

        if (!charge.status().equals("PAID")) {
            throw new ChargeNotPaidException();
        }

        if (paidAmount.value() < order.getValue()) {
            throw new InsufficientPaidAmountException();
        }

        if (!paidAmount.currency().equals("BRL")) {
            throw new InvalidCurrencyException();
        }

        if (order.getStatus() != PENDING) {
            throw new InvalidOrderStatusException();
        }

        if (order.getDueDate().isBefore(now)) {
            throw new OrderExpiredException();
        }

        if (!order.getPaymentMethod().equals(PIX)) {
            throw new ManuallyConfirmingAutomatedEnrollmentException();
        }

        Enrollment enrollment = new Enrollment(order, AUTOMATED);

        enrollmentRepository.save(enrollment);

        orderService.confirm(order);
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

    @Transactional
    List<DisplayEnrollmentResponseData> findAllBySessionOwner(Authentication session) {
        String email = session.getName();
        User user = userService.findByEmail(email);

        Optional<Enrollment> retrievedEnrollment = enrollmentRepository.findByOrderUserId(user.getId());

        if (retrievedEnrollment.isEmpty()) {
            return List.of();
        }

        Enrollment enrollment = retrievedEnrollment.get();

        DisplayEnrollmentResponseData data = new DisplayEnrollmentResponseData(
                enrollment.getId(),
                user.getName(),
                user.getCpf(),
                user.getPhone(),
                enrollment.getOrder().getValue(),
                enrollment.getType(),
                enrollment.getCreatedAt()
        );

        return List.of(data);
    }
}
