
package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author channarith.bong
 */
public class Customer extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("entity_id")
    @Expose
    private Long entityId;
    @SerializedName("website_id")
    @Expose
    private Long websiteId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("group_id")
    @Expose
    private Long groupId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("disable_auto_group_change")
    @Expose
    private String disableAutoGroupChange;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("phone_number")
    @Expose
    private String phonenumber;

    @SerializedName("updated_at")
    @Expose
    private String updateAt;
    @SerializedName("created_in")
    @Expose
    private String createdIn;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("taxvat")
    @Expose
    private String taxvat;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("reward_update_notification")
    @Expose
    private String rewardUpdateNotification;
    @SerializedName("reward_warning_notification")
    @Expose
    private String rewardWarningNotification;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("rp_token")
    @Expose
    private String rpToken;
    @SerializedName("rp_token_created_at")
    @Expose
    private String rpTokenCreatedAt;
    @SerializedName("store_id")
    @Expose
    private Long storeId;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses;
    @SerializedName("default_billing")
    @Expose
    private String defaultBilling;
    @SerializedName("default_shipping")
    @Expose
    private String defaultShipping;

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getUpdateAt() {
        return updateAt;
    }

    /**
     * @param updateAt
     */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * @return
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * @return
     */
    public Long getWebsiteId() {
        return websiteId;
    }

    /**
     * @param websiteId
     */
    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
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
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return
     */
    public String getDisableAutoGroupChange() {
        return disableAutoGroupChange;
    }

    /**
     * @param disableAutoGroupChange
     */
    public void setDisableAutoGroupChange(String disableAutoGroupChange) {
        this.disableAutoGroupChange = disableAutoGroupChange;
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


    /*
     *@param phonenumber
     * */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * @return
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @return
     */
    public String getCreatedIn() {
        return createdIn;
    }

    /**
     * @param createdIn
     */
    public void setCreatedIn(String createdIn) {
        this.createdIn = createdIn;
    }

    /**
     * @return
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return
     */
    public String getTaxvat() {
        return taxvat;
    }

    /**
     * @param taxvat
     */
    public void setTaxvat(String taxvat) {
        this.taxvat = taxvat;
    }

    /**
     * @return
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return
     */
    public String getRewardUpdateNotification() {
        return rewardUpdateNotification;
    }

    /**
     * @param rewardUpdateNotification
     */
    public void setRewardUpdateNotification(String rewardUpdateNotification) {
        this.rewardUpdateNotification = rewardUpdateNotification;
    }

    /**
     * @return
     */
    public String getRewardWarningNotification() {
        return rewardWarningNotification;
    }

    /**
     * @param rewardWarningNotification
     */
    public void setRewardWarningNotification(String rewardWarningNotification) {
        this.rewardWarningNotification = rewardWarningNotification;
    }

    /**
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return
     */
    public String getRpToken() {
        return rpToken;
    }

    /**
     * @param rpToken
     */
    public void setRpToken(String rpToken) {
        this.rpToken = rpToken;
    }

    /**
     * @return
     */
    public String getRpTokenCreatedAt() {
        return rpTokenCreatedAt;
    }

    /**
     * @param rpTokenCreatedAt
     */
    public void setRpTokenCreatedAt(String rpTokenCreatedAt) {
        this.rpTokenCreatedAt = rpTokenCreatedAt;
    }

    /**
     * @return
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * @param storeId
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * @return
     */
    public String getDefaultBilling() {
        return defaultBilling;
    }

    /**
     * @param defaultBilling
     */
    public void setDefaultBilling(String defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    /**
     * @return
     */
    public String getDefaultShipping() {
        return defaultShipping;
    }

    /**
     * @param defaultShipping
     */
    public void setDefaultShipping(String defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    /**
     * @return
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", entityId=" + entityId +
                ", websiteId=" + websiteId +
                ", email='" + email + '\'' +
                ", groupId=" + groupId +
                ", createdAt='" + createdAt + '\'' +
                ", disableAutoGroupChange='" + disableAutoGroupChange + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ",phone number" + phonenumber + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", createdIn='" + createdIn + '\'' +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", taxvat='" + taxvat + '\'' +
                ", dob='" + dob + '\'' +
                ", rewardUpdateNotification='" + rewardUpdateNotification + '\'' +
                ", rewardWarningNotification='" + rewardWarningNotification + '\'' +
                ", gender='" + gender + '\'' +
                ", rpToken='" + rpToken + '\'' +
                ", rpTokenCreatedAt='" + rpTokenCreatedAt + '\'' +
                ", storeId=" + storeId +
                ", addresses=" + addresses +
                ", defaultBilling='" + defaultBilling + '\'' +
                ", defaultShipping='" + defaultShipping + '\'' +
                '}';
    }
}