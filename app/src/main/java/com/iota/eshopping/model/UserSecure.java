package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author channarith.bong
 */

public class UserSecure extends Entity {

    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
