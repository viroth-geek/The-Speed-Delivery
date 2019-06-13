package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerPhoneNumber {

    @SerializedName("data")
    @Expose
    private Customer customer;

    @SerializedName("status")
    @Expose
    private String status;

    public CustomerPhoneNumber(Customer customer, String status) {
        this.customer = customer;
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
