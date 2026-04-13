package com.todev.ma.order.pix;

import com.todev.ma.order.Order;
import com.todev.ma.order.payment.CreatePaymentOrderResponseData;
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

    @Override
    public DisplayPixDetailResponseData findByOrderId(Long orderId) {
        PixDetail details = pixDetailRepository.findByOrderId(orderId).orElseThrow(PixDetailNotFoundException::new);

        return new DisplayPixDetailResponseData(details.getCopyAndPasteCode(), details.getQrCodeUrl());
    }
}
