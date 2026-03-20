package com.todev.ma.order;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

record CreateEnrollmentOrderRequestData(
        @NotNull(message = "O valor não pode ser nulo")
        @Min(value = 10, message = "O valor fornecido deve ser de ao menos 10 reais")
        @Max(value = 40, message = "O valor fornecido não deve ultrapassar os 40 reais")
        Integer value,

        @NotBlank(message = "O método de pagamento não pode ser nulo")
        @PaymentMethodConstraint(message = "O método de pagamento fornecido é inválido")
        String paymentMethod
) {
}
