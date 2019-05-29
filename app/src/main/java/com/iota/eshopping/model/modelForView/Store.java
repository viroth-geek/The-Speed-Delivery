package com.iota.eshopping.model.modelForView;

import com.iota.eshopping.model.Entity;

import java.util.List;

/**
 * @author channarith.bong
 */
public class Store extends Entity {

    private static final Long ID_0 = 0L;
    private static final Long ID_1 = 1L;

    private Long id;
    private String name;
    private String nameKh;
    private List<ProductCategory> categories;
    private List<StoreType> estoreTypes;
    private Float rate;
    private Float percentage;
    private String tag;
    private String timeDelivery;
    private String code;
    private String logoUrl;
    private String imageUrl;
    private String description;
    private Double latitude;
    private Double longitude;
    private String openHour;
    private String closeHour;
    private String status;
    private Boolean isSponsor;
    private Boolean isRecommend;
    private Boolean isNewArrival;
    private Long rootCategoryId;
    private Long storeGroupId;
    private float distant;
    private float fee;
    private String shippingMethod;
    private boolean statusOpenToday;
    private boolean statusOpenTomorrow;
    private boolean isOpenToday;

    public Store() {
    }

    public Store(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getNameKh() {
        return nameKh;
    }

    /**
     *
     * @param nameKh
     */
    public void setNameKh(String nameKh) {
        this.nameKh = nameKh;
    }

    /**
     * @return
     */
    public List<ProductCategory> getCategories() {
        return categories;
    }

    /**
     * @param categories
     */
    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    /**
     * @return
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return
     */
    public Float getRate() {
        return rate;
    }

    /**
     * @param rate
     */
    public void setRate(Float rate) {
        this.rate = rate;
    }

    /**
     * @return
     */
    public Float getPercentage() {
        return percentage;
    }

    /**
     * @param percentage
     */
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    /**
     * @return
     */
    public List<StoreType> getStoreTypes() {
        return estoreTypes;
    }

    /**
     * @param estoreTypes
     */
    public void setStoreTypes(List<StoreType> estoreTypes) {
        this.estoreTypes = estoreTypes;
    }

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public String getTimeDelivery() {
        return timeDelivery;
    }

    /**
     * @param timeDelivery
     */
    public void setTimeDelivery(String timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    /**
     * @return
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return
     */
    public Long getRootCategoryId() {
        return rootCategoryId;
    }

    /**
     * @param rootCategoryId
     */
    public void setRootCategoryId(Long rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    /**
     * @return
     */
    public Long getStoreGroupId() {
        return storeGroupId;
    }

    /**
     * @param storeGroupId
     */
    public void setStoreGroupId(Long storeGroupId) {
        this.storeGroupId = storeGroupId;
    }

    /**
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public List<StoreType> getEstoreTypes() {
        return estoreTypes;
    }

    /**
     * @param estoreTypes
     */
    public void setEstoreTypes(List<StoreType> estoreTypes) {
        this.estoreTypes = estoreTypes;
    }

    /**
     * @return
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return
     */
    public String getOpenHour() {
        return openHour;
    }

    /**
     * @param openHour
     */
    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    /**
     * @return
     */
    public String getCloseHour() {
        return closeHour;
    }

    /**
     * @param closeHour
     */
    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return
     */
    public Boolean getSponsor() {
        return isSponsor;
    }

    /**
     * @param sponsor
     */
    public void setSponsor(Boolean sponsor) {
        isSponsor = sponsor;
    }

    /**
     * @return
     */
    public Boolean getRecommend() {
        return isRecommend;
    }

    /**
     * @param recommend
     */
    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    /**
     * @return
     */
    public Boolean getNewArrival() {
        return isNewArrival;
    }

    /**
     * @param newArrival
     */
    public void setNewArrival(Boolean newArrival) {
        isNewArrival = newArrival;
    }

    /**
     * @return
     */
    public float getDistant() {
        return distant;
    }

    /**
     * @param distant
     */
    public void setDistant(float distant) {
        this.distant = distant;
    }

    /**
     * @return
     */
    public float getFee() {
        return fee;
    }

    /**
     * @param fee
     */
    public void setFee(float fee) {
        this.fee = fee;
    }

    /**
     * @return
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * @param shippingMethod
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * @return
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    /**
     *
     * @return
     */
    public boolean getStatusOpenToday() {
        return statusOpenToday;
    }

    /**
     *
     * @param statusOpenToday
     */
    public void setStatusOpenToday(boolean statusOpenToday) {
        this.statusOpenToday = statusOpenToday;
    }

    /**
     * Get statusOpenTomorrow
     *
     * @return statusOpenTomorrow
     */
    public boolean isStatusOpenTomorrow() {
        return statusOpenTomorrow;
    }

    /**
     * Setter statusOpenTomorrow
     */
    public void setStatusOpenTomorrow(boolean statusOpenTomorrow) {
        this.statusOpenTomorrow = statusOpenTomorrow;
    }

    /**
     * Get isOpen
     *
     * @return isOpen
     */
    public boolean isOpenToday() {
        return isOpenToday;
    }

    /**
     * Setter isOpen
     */
    public void setOpenToday(boolean openToday) {
        isOpenToday = openToday;
    }

    /**
     * @param storeId
     * @return
     */
    public static boolean isSysStore(Long storeId) {
        return ID_0.equals(storeId) || ID_1.equals(storeId);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", estoreTypes=" + estoreTypes +
                ", rate=" + rate +
                ", percentage=" + percentage +
                ", tag='" + tag + '\'' +
                ", timeDelivery='" + timeDelivery + '\'' +
                ", code='" + code + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", openHour='" + openHour + '\'' +
                ", closeHour='" + closeHour + '\'' +
                ", status='" + status + '\'' +
                ", isSponsor=" + isSponsor +
                ", isRecommend=" + isRecommend +
                ", isNewArrival=" + isNewArrival +
                ", rootCategoryId=" + rootCategoryId +
                ", storeGroupId=" + storeGroupId +
                ", distant=" + distant +
                ", fee=" + fee +
                ", shippingMethod='" + shippingMethod + '\'' +
                '}';
    }
}
