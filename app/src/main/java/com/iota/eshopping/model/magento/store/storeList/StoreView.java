
package com.iota.eshopping.model.magento.store.storeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;
import com.iota.eshopping.model.modelForView.Tag;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class StoreView extends Entity implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameKh")
    @Expose
    private String nameKh;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("rate")
    @Expose
    private Long rate;
    @SerializedName("percentage")
    @Expose
    private Long percentage;
    @SerializedName("tag")
    @Expose
    private List<Tag> Tags = null;
    @SerializedName("timeDelivery")
    @Expose
    private TimeDelivery timeDelivery;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("open_hour_today")
    @Expose
    private String openHour;
    @SerializedName("close_hour_today")
    @Expose
    private String closeHour;
    @SerializedName("status_today")
    @Expose
    private String status;
    @SerializedName("is_sponsor")
    @Expose
    private Boolean isSponsor;
    @SerializedName("is_recommend")
    @Expose
    private Boolean isRecommend;
    @SerializedName("is_new_arrival")
    @Expose
    private Boolean isNewArrival;
    @SerializedName("estoreTypes")
    @Expose
    private List<String> estoreTypes = null;
    @SerializedName("distant")
    @Expose
    private float distant;
    @SerializedName("fee_base_on_distant")
    @Expose
    private float feeBaseOnDistant;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;

    @SerializedName("is_open")
    @Expose
    private boolean isOpen;
    @SerializedName("store_status_today")
    @Expose
    private boolean statusOpenToday;
    @SerializedName("store_status_tomorrow")
    @Expose
    private boolean statusOpenTomorrow;
    @SerializedName("count")
    @Expose
    private int count;

    private final static long serialVersionUID = 868437171646106190L;

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get nameKh
     *
     * @return nameKh
     */
    public String getNameKh() {
        return nameKh;
    }

    /**
     * Setter nameKh
     */
    public void setNameKh(String nameKh) {
        this.nameKh = nameKh;
    }

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
     * Get imageUrl
     *
     * @return imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Get logoUrl
     *
     * @return logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * Setter logoUrl
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     * Get rate
     *
     * @return rate
     */
    public Long getRate() {
        return rate;
    }

    /**
     * Setter rate
     */
    public void setRate(Long rate) {
        this.rate = rate;
    }

    /**
     * Get percentage
     *
     * @return percentage
     */
    public Long getPercentage() {
        return percentage;
    }

    /**
     * Setter percentage
     */
    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    /**
     * Get Tags
     *
     * @return Tags
     */
    public List<Tag> getTags() {
        return Tags;
    }

    /**
     * Setter Tags
     */
    public void setTags(List<Tag> tags) {
        Tags = tags;
    }

    /**
     * Get timeDelivery
     *
     * @return timeDelivery
     */
    public TimeDelivery getTimeDelivery() {
        return timeDelivery;
    }

    /**
     * Setter timeDelivery
     */
    public void setTimeDelivery(TimeDelivery timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get categories
     *
     * @return categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Setter categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Get latitude
     *
     * @return latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Setter latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get longitude
     *
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Setter longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get openHour
     *
     * @return openHour
     */
    public String getOpenHour() {
        return openHour;
    }

    /**
     * Setter openHour
     */
    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    /**
     * Get closeHour
     *
     * @return closeHour
     */
    public String getCloseHour() {
        return closeHour;
    }

    /**
     * Setter closeHour
     */
    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    /**
     * Get status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get isSponsor
     *
     * @return isSponsor
     */
    public Boolean getSponsor() {
        return isSponsor;
    }

    /**
     * Setter isSponsor
     */
    public void setSponsor(Boolean sponsor) {
        isSponsor = sponsor;
    }

    /**
     * Get isRecommend
     *
     * @return isRecommend
     */
    public Boolean getRecommend() {
        return isRecommend;
    }

    /**
     * Setter isRecommend
     */
    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    /**
     * Get isNewArrival
     *
     * @return isNewArrival
     */
    public Boolean getNewArrival() {
        return isNewArrival;
    }

    /**
     * Setter isNewArrival
     */
    public void setNewArrival(Boolean newArrival) {
        isNewArrival = newArrival;
    }

    /**
     * Get estoreTypes
     *
     * @return estoreTypes
     */
    public List<String> getEstoreTypes() {
        return estoreTypes;
    }

    /**
     * Setter estoreTypes
     */
    public void setEstoreTypes(List<String> estoreTypes) {
        this.estoreTypes = estoreTypes;
    }

    /**
     * Get distant
     *
     * @return distant
     */
    public float getDistant() {
        return distant;
    }

    /**
     * Setter distant
     */
    public void setDistant(float distant) {
        this.distant = distant;
    }

    /**
     * Get feeBaseOnDistant
     *
     * @return feeBaseOnDistant
     */
    public float getFeeBaseOnDistant() {
        return feeBaseOnDistant;
    }

    /**
     * Setter feeBaseOnDistant
     */
    public void setFeeBaseOnDistant(float feeBaseOnDistant) {
        this.feeBaseOnDistant = feeBaseOnDistant;
    }

    /**
     * Get shippingMethod
     *
     * @return shippingMethod
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Setter shippingMethod
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * Get isOpen
     *
     * @return isOpen
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Setter isOpen
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * Get statusOpenToday
     *
     * @return statusOpenToday
     */
    public boolean getStatusOpenToday() {
        return statusOpenToday;
    }

    /**
     * Setter statusOpenToday
     */
    public void setStatusOpenToday(boolean statusOpenToday) {
        this.statusOpenToday = statusOpenToday;
    }

    /**
     * Get statusOpenTomorrow
     *
     * @return statusOpenTomorrow
     */
    public boolean getStatusOpenTomorrow() {
        return statusOpenTomorrow;
    }

    /**
     * Setter statusOpenTomorrow
     */
    public void setStatusOpenTomorrow(boolean statusOpenTomorrow) {
        this.statusOpenTomorrow = statusOpenTomorrow;
    }

    /**
     * Get count
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Setter count
     */
    public void setCount(int count) {
        this.count = count;
    }

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this).append("id", id).append("name", name).append("code", code).append("imageUrl", imageUrl).append("rate", rate).append("percentage", percentage).append("Tag", Tags).append("timeDelivery", timeDelivery).append("categories", categories).append("isOpen", isOpen).append("estoreTypes", estoreTypes).toString();
//    }


    @Override
    public String toString() {
        return "StoreView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameKh='" + nameKh + '\'' +
                ", code='" + code + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", rate=" + rate +
                ", percentage=" + percentage +
                ", Tags=" + Tags +
                ", timeDelivery=" + timeDelivery +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", openHour='" + openHour + '\'' +
                ", closeHour='" + closeHour + '\'' +
                ", status='" + status + '\'' +
                ", isSponsor=" + isSponsor +
                ", isRecommend=" + isRecommend +
                ", isNewArrival=" + isNewArrival +
                ", estoreTypes=" + estoreTypes +
                ", distant=" + distant +
                ", feeBaseOnDistant=" + feeBaseOnDistant +
                ", shippingMethod='" + shippingMethod + '\'' +
                ", isOpen=" + isOpen +
                ", statusOpenToday=" + statusOpenToday +
                ", statusOpenTomorrow=" + statusOpenTomorrow +
                ", count=" + count +
                '}';
    }
}
