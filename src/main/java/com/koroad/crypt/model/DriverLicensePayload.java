package com.koroad.crypt.model;

import java.util.List;

public class DriverLicensePayload {

    private List<DriverLicenseInfo> licenses;

    public DriverLicensePayload() {
    }

    public List<DriverLicenseInfo> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<DriverLicenseInfo> licenses) {
        this.licenses = licenses;
    }
}

