package com.iota.eshopping.model.modelForView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NokorGroup on 6/11/18.
 */
public class Address {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("firstname")
    @Expose
    private String firstName;
    @SerializedName("lastname")
    @Expose
    private String lastName;
    @SerializedName("addressId")
    @Expose
    private Long addressId;
    @SerializedName("customer_id")
    @Expose
    private Long customerId;
    @SerializedName("postcode")
    @Expose
    private String postCode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("street")
    @Expose
    private List<String> streets;
    @SerializedName("isDefaultBilling")
    @Expose
    private Boolean isDefaultBilling;
    @SerializedName("isDefaultShipping")
    @Expose
    private Boolean isDefaultShipping;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public Long getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     *
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     *
     * @return
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     */
    public List<String> getStreets() {
        return streets;
    }

    /**
     *
     * @param streets
     */
    public void setStreets(List<String> streets) {
        this.streets = streets;
    }

    /**
     *
     * @return
     */
    public Boolean getDefaultBilling() {
        return isDefaultBilling;
    }

    /**
     *
     * @param defaultBilling
     */
    public void setDefaultBilling(Boolean defaultBilling) {
        isDefaultBilling = defaultBilling;
    }

    /**
     *
     * @return
     */
    public Boolean getDefaultShipping() {
        return isDefaultShipping;
    }

    /**
     *
     * @param defaultShipping
     */
    public void setDefaultShipping(Boolean defaultShipping) {
        isDefaultShipping = defaultShipping;
    }

    /**
     *
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Address{" +
                "customerId=" + customerId +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", telephone='" + telephone + '\'' +
                ", company='" + company + '\'' +
                ", street='" + streets + '\'' +
                ", isDefaultBilling=" + isDefaultBilling +
                ", isDefaultShipping=" + isDefaultShipping +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
