package com.koroad.crypt.model;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainRequest {

    private String mode;
    private String requestId;
    private String clientId;
    private DriverLicensePayload payload;
    private String encryptedBody;
    private String secret;

    public MainRequest() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public DriverLicensePayload getPayload() {
        return payload;
    }

    public void setPayload(DriverLicensePayload payload) {
        this.payload = payload;
    }

    public String getEncryptedBody() {
        return encryptedBody;
    }

    public void setEncryptedBody(String encryptedBody) {
        this.encryptedBody = encryptedBody;
    }

    @Override
    public String toString() {
        String str = "MainRequest: {";
        str += "mode=" + mode + ", ";
        str += "requestId=" + requestId + ", ";
        str += "clientId=" + clientId + ", ";
        str += "secret=" + secret + ", ";
        str += "encryptedBody=" + encryptedBody + ", ";
        if (payload != null) {
            str += "payload={ licenses=[";
            for (DriverLicenseInfo license : payload.getLicenses()) {
                if (license != null) {
                    try {
                        String json = new ObjectMapper().writeValueAsString(license);
                        str += json + ", ";
                    } catch (Exception e) {
                        str += "{error_serializing_license:\"" + e.getMessage() + "\"}, ";
                    }
                }
            }
            str += "]}";
        }
        else {
            str += "payload=null";
        }
        return str;
    }
}

