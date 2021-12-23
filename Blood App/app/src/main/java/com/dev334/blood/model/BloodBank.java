package com.dev334.blood.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodBank {

    @SerializedName("_blood_bank_name")
    private String bankName;

    @SerializedName("__longitude")
    private Double longitude;

    @SerializedName("__latitude")
    private Double latitude;

    @SerializedName("__contact_no")
    private String contact;

    public BloodBank() {

    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
