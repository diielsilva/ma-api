package com.todev.ma.enrollment.order;

import com.todev.ma.enrollment.payment.CreatePaymentOrderResponseData;
import org.springframework.stereotype.Service;

@Service
class PixDetailService {
    private final PixDetailRepository pixDetailRepository;

    PixDetailService(PixDetailRepository pixDetailRepository) {
        this.pixDetailRepository = pixDetailRepository;
    }

    void create(Order order, CreatePaymentOrderResponseData data) {
        String copyAndPasteCode = data.qr_codes().getFirst().text();
        String qrCodeUrl = data.qr_codes().getFirst().links().getFirst().href();
        PixDetail details = new PixDetail(order, copyAndPasteCode, qrCodeUrl);

        pixDetailRepository.save(details);
    }
}
