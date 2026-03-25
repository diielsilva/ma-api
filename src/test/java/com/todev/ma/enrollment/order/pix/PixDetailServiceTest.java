package com.todev.ma.enrollment.order.pix;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.enrollment.payment.CreatePaymentOrderResponseData;
import com.todev.ma.enrollment.payment.PaymentCustomer;
import com.todev.ma.enrollment.payment.QrCodeDetailsResponseData;
import com.todev.ma.enrollment.payment.QrCodeResponseData;
import com.todev.ma.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.todev.ma.enrollment.order.OrderStatus.PENDING;
import static com.todev.ma.enrollment.order.PaymentMethod.PIX;
import static com.todev.ma.user.Gender.MALE;
import static com.todev.ma.user.Role.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(value = MockitoExtension.class)
class PixDetailServiceTest {
    @InjectMocks
    private PixDetailService pixDetailService;

    @Mock
    private PixDetailRepository pixDetailRepository;

    @Test
    void create_MustCreateAPixDetail_WhenNoExceptionIsThrown() {
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

        Order order = new Order(user, 25, LocalDateTime.now().plusDays(1), PIX, PENDING);

        List<QrCodeResponseData> qrCode = List.of(new QrCodeResponseData(
                "copy and paste code",
                List.of(new QrCodeDetailsResponseData("", "", "", ""))
        ));

        CreatePaymentOrderResponseData data = new CreatePaymentOrderResponseData(
                1L,
                new PaymentCustomer(user.getName(), user.getEmail(), user.getCpf()),
                qrCode
        );

        assertDoesNotThrow(() -> pixDetailService.create(order, data));
    }
}