package com.todev.ma.enrollment.payment;

import java.util.List;

public record QrCodeResponseData(String text, List<QrCodeDetailsResponseData> links) {
}
