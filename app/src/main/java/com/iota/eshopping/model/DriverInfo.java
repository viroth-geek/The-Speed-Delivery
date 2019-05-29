package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 12/11/18.
 */
public class DriverInfo {

    @SerializedName("driver_code")
    @Expose
    private String code;

    @SerializedName("driver_code")
    @Expose
    private String firstName;

    @SerializedName("driver_code")
    @Expose
    private String lastName;

    @SerializedName("driver_code")
    @Expose
    private String phoneNumber;

    /**
     * Get code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get firstName
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get lastName
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get phoneNumber
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
