package com.iota.eshopping.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 8/20/18.
 */

public class OptionDetail {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("option_type_id")
    @Expose
    private Long optionTypeId;

    @SerializedName("price")
    @Expose
    private Float price;

    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get optionTypeId
     *
     * @return optionTypeId
     */
    public Long getOptionTypeId() {
        return optionTypeId;
    }

    /**
     * Setter optionTypeId
     */
    public void setOptionTypeId(Long optionTypeId) {
        this.optionTypeId = optionTypeId;
    }

    /**
     * Get price
     *
     * @return price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Setter price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OptionDetail{" +
                "title='" + title + '\'' +
                ", optionTypeId=" + optionTypeId +
                ", price=" + price +
                '}';
    }
}
