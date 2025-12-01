package com.koroad.crypt.model;

public class MainResponse {

    private String mode;
    private String requestId;
    private String status;
    private String errorCode;
    private String errorMessage;
    private DriverLicensePayload payload;
    private String encryptedBody;

    public MainResponse() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
        String str = "MainResponse: {";
        str += "mode=" + mode + ", ";
        str += "requestId=" + requestId + ", ";
        str += "status=" + status + ", ";
        str += "errorCode=" + errorCode + ", ";
        str += "errorMessage=" + errorMessage + ", ";
        str += "payload=" + payload + ", ";
        str += "encryptedBody=" + encryptedBody + "}";
        if (payload != null) {
            for (DriverLicenseInfo license : payload.getLicenses()) {
                str += "License: {licenseNo=" + license.getLicenseNo() + ", residentName=" + license.getResidentName()
                        + ", residentDate=" + license.getResidentDate() + ", seqNo=" + license.getSeqNo()
                        + ", licenseConCode=" + license.getLicenseConCode() + ", fromDate=" + license.getFromDate()
                        + ", toDate=" + license.getToDate() + "}, ";
            }
        }
        return str;
    }
}

