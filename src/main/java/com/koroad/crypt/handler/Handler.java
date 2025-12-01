package com.koroad.crypt.handler;

import java.util.Map;
import java.util.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koroad.crypt.model.DriverLicensePayload;
import com.koroad.crypt.model.MainRequest;
import com.koroad.crypt.model.MainResponse;
import com.koroad.crypt.service.KoroadCryptoService;

public class Handler implements RequestHandler<Map<String, Object>, MainResponse> {

    private static final Logger logger = Logger.getLogger(Handler.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Handler() {
    }

    @Override
    public MainResponse handleRequest(Map<String, Object> rawRequest, Context context) {
        MainRequest request = null;
        try {
            request = objectMapper.convertValue(rawRequest, MainRequest.class);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to map request body: " + e.getMessage());
        }
        logger.info("handleRequest start, request: " + String.valueOf(request));

        MainResponse response = new MainResponse();
        if (request != null) {
            response.setRequestId(request.getRequestId());
            response.setMode(request.getMode());
        }

        String mode = request != null ? request.getMode() : null;
        if (mode == null || mode.isEmpty()) {
            response.setStatus("ERROR");
            response.setErrorCode("INVALID_MODE");
            response.setErrorMessage("mode must not be null or empty");
            logger.warning("handleRequest end, response: " + response.toString());
            return response;
        }

        String secret = request != null ? request.getSecret() : null;
        if (secret == null || secret.isEmpty()) {
            response.setStatus("ERROR");
            response.setErrorCode("INVALID_SECRET");
            response.setErrorMessage("secret must not be null or empty");
            logger.warning("handleRequest end, response: " + response.toString());
            return response;
        }

        try {
            KoroadCryptoService cryptoService = new KoroadCryptoService(secret);

            if ("ENCRYPT".equalsIgnoreCase(mode)) {
                String encrypted = cryptoService.encrypt(request.getPayload());
                response.setEncryptedBody(encrypted);
                response.setStatus("SUCCESS");
            } else if ("DECRYPT".equalsIgnoreCase(mode)) {
                DriverLicensePayload payload = cryptoService.decrypt(request.getEncryptedBody());
                response.setPayload(payload);
                response.setStatus("SUCCESS");
            } else {
                response.setStatus("ERROR");
                response.setErrorCode("INVALID_MODE");
                response.setErrorMessage("mode must be ENCRYPT or DECRYPT");
            }
            logger.info("handleRequest end, response: " + response.toString());
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setErrorCode("CRYPTO_ERROR");
            response.setErrorMessage(e.getMessage());
            if (context != null && context.getLogger() != null) {
                context.getLogger().log("Crypto error: " + e.getMessage());
            }
            logger.info("handleRequest end, response: " + response.toString());
        }

        return response;
    }
}
