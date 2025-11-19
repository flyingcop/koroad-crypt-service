package com.koroad.crypt.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koroad.crypt.model.DriverLicenseInfo;
import com.koroad.crypt.model.DriverLicensePayload;
import kr.or.koroad.dlv.crypt.aria.cipher.ARIACipher256;
import kr.or.koroad.dlv.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.List;

public class KoroadCryptoService {

    private final ARIACipher256 cipher;
    private final ObjectMapper objectMapper;

    public KoroadCryptoService(String clientSecret) {
        if (clientSecret == null || clientSecret.isEmpty()) {
            throw new IllegalArgumentException("KOROAD_CLIENT_SECRET environment variable must be set");
        }
        String encodedKey = Base64.encode(clientSecret.getBytes(StandardCharsets.UTF_8));
        try {
            this.cipher = new ARIACipher256(encodedKey);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Invalid KOROAD_CLIENT_SECRET; failed to initialize ARIACipher256", e);
        }
        this.objectMapper = new ObjectMapper();
    }

    public String encrypt(DriverLicensePayload payload) {
        try {
            List<DriverLicenseInfo> licenses = payload != null ? payload.getLicenses() : null;
            if (licenses == null) {
                throw new IllegalArgumentException("payload.licenses must not be null");
            }
            String json = objectMapper.writeValueAsString(licenses);
            byte[] plainBytes = json.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = cipher.encrypt(plainBytes);
            return Base64.encode(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt payload", e);
        }
    }

    public DriverLicensePayload decrypt(String encryptedBody) {
        try {
            if (encryptedBody == null || encryptedBody.isEmpty()) {
                throw new IllegalArgumentException("encryptedBody must not be null or empty");
            }
            byte[] encryptedBytes = Base64.decode(encryptedBody);
            byte[] decryptedBytes = cipher.decrypt(encryptedBytes);
            String json = new String(decryptedBytes, StandardCharsets.UTF_8);
            List<DriverLicenseInfo> licenses = objectMapper.readValue(
                    json,
                    new TypeReference<List<DriverLicenseInfo>>() {
                    }
            );
            DriverLicensePayload payload = new DriverLicensePayload();
            payload.setLicenses(licenses);
            return payload;
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt payload", e);
        }
    }
}
