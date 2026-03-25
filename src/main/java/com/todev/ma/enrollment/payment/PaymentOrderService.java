package com.todev.ma.enrollment.payment;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.shared.InternalApplicationException;
import com.todev.ma.shared.Trackable;
import com.todev.ma.user.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
class PaymentOrderService extends Trackable implements IPaymentOrderService {
    private final PaymentGatewayConfiguration paymentGatewayConfiguration;
    private final RestClient apiClient;

    PaymentOrderService(PaymentGatewayConfiguration paymentGatewayConfiguration, RestClient apiClient) {
        this.paymentGatewayConfiguration = paymentGatewayConfiguration;
        this.apiClient = apiClient;
    }

    @Override
    public CreatePaymentOrderResponseData create(Order order) throws InternalApplicationException {
        try {
            return sendToPaymentGateway(order);
        } catch (Exception exception) {
            log.error(exception.getMessage());

            throw new PaymentGatewayCommunicationException();
        }
    }

    private CreatePaymentOrderResponseData sendToPaymentGateway(Order order) {
        User user = order.getUser();

        PaymentCustomer customer = new PaymentCustomer(user.getName(), user.getEmail(), user.getCpf());

        QrCodeRequestData qrCode = new QrCodeRequestData(
                new QrCodeAmountRequestData(order.getValue() * 100)
        );

        List<QrCodeRequestData> qrCodes = List.of(qrCode);

        CreatePaymentOrderRequestData data = new CreatePaymentOrderRequestData(
                order.getId(), customer, qrCodes, List.of(paymentGatewayConfiguration.getUrl())
        );

        return apiClient
                .post()
                .uri("/orders")
                .header("Authorization", "Bearer " + paymentGatewayConfiguration.getToken())
                .contentType(APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(CreatePaymentOrderResponseData.class);
    }
}
