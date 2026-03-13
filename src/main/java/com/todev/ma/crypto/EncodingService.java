package com.todev.ma.crypto;

import com.todev.ma.shared.Trackable;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static javax.crypto.Cipher.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
class EncodingService extends Trackable {
    private final EncodingConfiguration encodingConfiguration;

    EncodingService(EncodingConfiguration encodingConfiguration) {
        this.encodingConfiguration = encodingConfiguration;
    }

    String encode(String text) {
        try {
            Cipher cipher = getCipher(ENCRYPT_MODE);
            byte[] encodedTextBytes = cipher.doFinal(text.getBytes(UTF_8));

            return Base64.getEncoder().encodeToString(encodedTextBytes);
        } catch (Exception exception) {
            log.error(exception.getMessage());

            throw new EncodingException();
        }
    }

    String decode(String base64EncodedText) {
        try {
            Cipher cipher = getCipher(DECRYPT_MODE);
            byte[] encodedTextBytes = Base64.getDecoder().decode(base64EncodedText);
            byte[] decodedTextBytes = cipher.doFinal(encodedTextBytes);

            return new String(decodedTextBytes, UTF_8);
        } catch (Exception exception) {
            log.error(exception.getMessage());

            throw new DecodingException();
        }
    }

    private Cipher getCipher(int mode) {
        try {
            byte[] secretBytes = encodingConfiguration.getSecret().getBytes(UTF_8);
            IvParameterSpec iv = new IvParameterSpec(secretBytes);
            SecretKeySpec secretKey = new SecretKeySpec(secretBytes, encodingConfiguration.getStandard());
            Cipher cipher = getInstance(encodingConfiguration.getAlgorithm());

            cipher.init(mode, secretKey, iv);

            return cipher;
        } catch (Exception exception) {
            log.error(exception.getMessage());

            throw new CipherCreationException();
        }
    }
}
