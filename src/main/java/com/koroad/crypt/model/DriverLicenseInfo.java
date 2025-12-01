package com.koroad.crypt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverLicenseInfo {

    @JsonProperty("f_license_no")
    private String licenseNo;

    @JsonProperty("f_resident_name")
    private String residentName;

    @JsonProperty("f_resident_date")
    private String residentDate;

    @JsonProperty("f_seq_no")
    private String seqNo;

    @JsonProperty("f_licn_con_code")
    private String licenseConCode;

    @JsonProperty("f_from_date")
    private String fromDate;

    @JsonProperty("f_to_date")
    private String toDate;

    public DriverLicenseInfo() {
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public String getResidentDate() {
        return residentDate;
    }

    public void setResidentDate(String residentDate) {
        this.residentDate = residentDate;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getLicenseConCode() {
        return licenseConCode;
    }

    public void setLicenseConCode(String licenseConCode) {
        this.licenseConCode = licenseConCode;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
