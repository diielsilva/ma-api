package com.todev.ma.payment;

import java.util.List;

public record QrCodeResponseData(String text, List<QrCodeDetailsResponseData> links) {
}
