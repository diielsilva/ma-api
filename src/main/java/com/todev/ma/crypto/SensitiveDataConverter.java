package com.todev.ma.crypto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SensitiveDataConverter implements AttributeConverter<String, String> {
    private final EncodingService encodingService;

    SensitiveDataConverter(EncodingService encodingService) {
        this.encodingService = encodingService;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encodingService.encode(attribute);
    }

    @Override
    public String convertToEntityAttribute(String encodedAttribute) {
        return encodingService.decode(encodedAttribute);
    }
}
