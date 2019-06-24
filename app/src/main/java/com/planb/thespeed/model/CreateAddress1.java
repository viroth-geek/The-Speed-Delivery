package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.modelForView.Address1;

public class CreateAddress1 {
    @SerializedName("address")
    @Expose
    private Address1 address;

    public Address1 getAddress() {
        return address;
    }

    public void setAddress(Address1 address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CreateAddress{" +
                "address=" + address +
                '}';
    }
}
