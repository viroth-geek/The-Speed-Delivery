package com.planb.thespeed.model.phone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhoneResponse {

    @SerializedName("data")
    @Expose
    private Phone phone;

    @SerializedName("status")
    @Expose
    private String status;

    public PhoneResponse(Phone phone, String status) {
        this.phone = phone;
        this.status = status;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
