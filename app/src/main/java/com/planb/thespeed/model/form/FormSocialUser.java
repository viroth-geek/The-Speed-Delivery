package com.planb.thespeed.model.form;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 8/10/18.
 */

public class FormSocialUser {

    @SerializedName("firstname")
    @Expose
    private String firstName;

    @SerializedName("lastname")
    @Expose
    private String lastName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("social_id")
    @Expose
    private String socialID;

    @SerializedName("social_type")
    @Expose
    private String socialType;

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
     * Get email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get socialID
     *
     * @return socialID
     */
    public String getSocialID() {
        return socialID;
    }

    /**
     * Setter socialID
     */
    public void setSocialID(String socialID) {
        this.socialID = socialID;
    }

    /**
     * Get socialType
     *
     * @return socialType
     */
    public String getSocialType() {
        return socialType;
    }

    /**
     * Setter socialType
     */
    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }
}
