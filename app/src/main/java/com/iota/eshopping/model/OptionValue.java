package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class OptionValue extends Entity {

    private Long id;

    @SerializedName("option_type_id")
    @Expose
    private Long optionTypeId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private Float price;

    @SerializedName("sort_order")
    @Expose
    private Integer sortOrder;

    private boolean isCheck;

    private String productUid;

    /**
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return Long
     */
    public Long getOptionTypeId() {
        return optionTypeId;
    }

    /**
     *
     * @param optionTypeId Long
     */
    public void setOptionTypeId(Long optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    /**
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return Float
     */
    public Float getPrice() {
        return price == null ? 0F : price;
    }

    /**
     *
     * @param price Float
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     *
     * @return Integer
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     *
     * @param sortOrder Integer
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     *
     * @return boolean
     */
    public boolean isCheck() {
        return isCheck;
    }

    /**
     *
     * @param check boolean
     */
    public void setCheck(boolean check) {
        isCheck = check;
    }

    /**
     *
     * @return String
     */
    public String getProductUid() {
        return productUid;
    }

    /**
     *
     * @param productUid String
     */
    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    @Override
    public String toString() {
        return "OptionValue{" +
                "id=" + id +
                ", optionTypeId=" + optionTypeId +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", sortOrder=" + sortOrder +
                ", isCheck=" + isCheck +
                ", productUid='" + productUid + '\'' +
                '}';
    }
}
