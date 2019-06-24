
package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ShippingAddress implements Serializable {

    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("region_id")
    @Expose
    private Long regionId;
    @SerializedName("region_code")
    @Expose
    private String regionCode;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("street")
    @Expose
    private List<String> street = null;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("customer_id")
    @Expose
    private Long customerId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("same_as_billing")
    @Expose
    private Long sameAsBilling;
    private final static long serialVersionUID = 5316010854816377605L;

    /**
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return
     */
    public Long getRegionId() {
        return regionId;
    }

    /**
     * @param regionId
     */
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    /**
     * @return
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * @param regionCode
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * @return
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * @return
     */
    public List<String> getStreet() {
        return street;
    }

    /**
     * @param street
     */
    public void setStreet(List<String> street) {
        this.street = street;
    }

    /**
     * @return
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return
     */
    public Long getSameAsBilling() {
        return sameAsBilling;
    }

    /**
     * @param sameAsBilling
     */
    public void setSameAsBilling(Long sameAsBilling) {
        this.sameAsBilling = sameAsBilling;
    }

}
