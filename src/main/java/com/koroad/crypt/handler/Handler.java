package com.koroad.crypt.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.koroad.crypt.model.DriverLicensePayload;
import com.koroad.crypt.model.MainRequest;
import com.koroad.crypt.model.MainResponse;
import com.koroad.crypt.service.KoroadCryptoService;

public class Handler implements RequestHandler<MainRequest, MainResponse> {

    private final KoroadCryptoService cryptoService;

    public Handler() {
        String clientSecret = System.getenv("KOROAD_CLIENT_SECRET");
        this.cryptoService = new KoroadCryptoService(clientSecret);
    }

    @Override
    public MainResponse handleRequest(MainRequest request, Context context) {
        MainResponse response = new MainResponse();
        if (request != null) {
            response.setRequestId(request.getRequestId());
            response.setMode(request.getMode());
        }

        String mode = request != null ? request.getMode() : null;
        try {
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
        } catch (Exception e) {
            response.setStatus("ERROR");
            response.setErrorCode("CRYPTO_ERROR");
            response.setErrorMessage(e.getMessage());
            if (context != null && context.getLogger() != null) {
                context.getLogger().log("Crypto error: " + e.getMessage());
            }
        }

        return response;
    }
}
