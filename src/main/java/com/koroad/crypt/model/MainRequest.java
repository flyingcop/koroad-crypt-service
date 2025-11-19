package com.koroad.crypt.model;

public class MainRequest {

    private String mode;
    private String requestId;
    private String clientId;
    private DriverLicensePayload payload;
    private String encryptedBody;

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
}

