package com.todev.ma.enrollment.order.pix;

import com.todev.ma.enrollment.order.Order;
import com.todev.ma.enrollment.order.payment.CreatePaymentOrderResponseData;
import org.springframework.stereotype.Service;

@Service
class PixDetailService implements IPixDetailService {
    private final PixDetailRepository pixDetailRepository;

    PixDetailService(PixDetailRepository pixDetailRepository) {
        this.pixDetailRepository = pixDetailRepository;
    }

    @Override
    public void create(Order order, CreatePaymentOrderResponseData data) {
        String copyAndPasteCode = data.qr_codes().getFirst().text();
        String qrCodeUrl = data.qr_codes().getFirst().links().getFirst().href();
        PixDetail details = new PixDetail(order, copyAndPasteCode, qrCodeUrl);

        pixDetailRepository.save(details);
    }
}
