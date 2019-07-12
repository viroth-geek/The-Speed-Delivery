
package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.magento.order.ExtensionAttribute;

import java.util.List;

/**
 * @author channarith.bong
 */

public class Address extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("customer_id")
    @Expose
    private Long customerId;
    @SerializedName("region_id")
    @Expose
    private Long regionId;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("street")
    @Expose
    private List<String> street = null;
    @SerializedName("telephone")
    @Expose
    private String telephone;
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
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("default_shipping")
    @Expose
    private Boolean defaultShipping;
    @SerializedName("default_billing")
    @Expose
    private Boolean defaultBilling;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("address_lines")
    @Expose
    private String addressLine;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttribute extensionAttribute;
    @SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes;

    private final static long serialVersionUID = 4349227692488541495L;

    public Address() {
    }

    public Address(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Long
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId Long
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Long
     */
    public Long getRegionId() {
        return regionId;
    }

    /**
     * @param regionId Long
     */
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    /**
     * @return String
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId String
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * @return List<String>
     */
    public List<String> getStreet() {
        return street;
    }

    /**
     * @param street List string
     */
    public void setStreet(List<String> street) {
        this.street = street;
    }

    /**
     * @return String
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone String
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return String
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode String
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return String
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname String
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return String
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname String
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return String
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @return String
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @return String
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode String
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName String
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return String
     */
    public String getAddressLine() {
        return addressLine;
    }

    /**
     * @param addressLine String
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    /**
     * @param latitude String
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @param longitude String
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return Boolean
     */
    public Boolean getDefaultShipping() {
        return defaultShipping;
    }

    /**
     *
     * @param defaultShipping Boolean
     */
    public void setDefaultShipping(Boolean defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    /**
     *
     * @return Boolean
     */
    public Boolean getDefaultBilling() {
        return defaultBilling;
    }

    /**
     *
     * @param defaultBilling Boolean
     */
    public void setDefaultBilling(Boolean defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    /**
     * @see ExtensionAttribute
     * @return ExtensionAttribute
     */
    public ExtensionAttribute getExtensionAttribute() {
        return extensionAttribute;
    }

    /**
     *
     * @param extensionAttribute ExtensionAttribute
     */
    public void setExtensionAttribute(ExtensionAttribute extensionAttribute) {
        this.extensionAttribute = extensionAttribute;
    }

    /**
     *
     * @return list of CustomAttribute
     */
    public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    /**
     *
     * @param customAttributes CustomAttribute
     */
    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }

    /**
     *
     * @return long
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", regionId=" + regionId +
                ", countryId='" + countryId + '\'' +
                ", street=" + street +
                ", telephone='" + telephone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", defaultShipping=" + defaultShipping +
                ", defaultBilling=" + defaultBilling +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", extensionAttribute=" + extensionAttribute +
                ", customAttributes=" + customAttributes +
                '}';
    }
}
