
package com.planb.thespeed.model.magento.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author channarith.bong
 */
public class Option extends Entity {

    @SerializedName("product_sku")
    @Expose
    private String productSku;
    @SerializedName("option_id")
    @Expose
    private Long optionId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sort_order")
    @Expose
    private Long sortOrder;
    @SerializedName("is_require")
    @Expose
    private Boolean isRequire;
    @SerializedName("max_characters")
    @Expose
    private Long maxCharacters;
    @SerializedName("image_size_x")
    @Expose
    private Long imageSizeX;
    @SerializedName("image_size_y")
    @Expose
    private Long imageSizeY;
    @SerializedName("values")
    @Expose
    private List<Value> values = null;
    private final static long serialVersionUID = -9015678424532979421L;

    /**
     * @return
     */
    public String getProductSku() {
        return productSku;
    }

    /**
     * @param productSku
     */
    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    /**
     * @return
     */
    public Long getOptionId() {
        return optionId;
    }

    /**
     * @param optionId
     */
    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return
     */
    public Long getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder
     */
    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return
     */
    public Boolean getIsRequire() {
        return isRequire;
    }

    /**
     * @param isRequire
     */
    public void setIsRequire(Boolean isRequire) {
        this.isRequire = isRequire;
    }

    /**
     * @return
     */
    public Long getMaxCharacters() {
        return maxCharacters;
    }

    /**
     * @param maxCharacters
     */
    public void setMaxCharacters(Long maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    /**
     * @return
     */
    public Long getImageSizeX() {
        return imageSizeX;
    }

    /**
     * @param imageSizeX
     */
    public void setImageSizeX(Long imageSizeX) {
        this.imageSizeX = imageSizeX;
    }

    /**
     * @return
     */
    public Long getImageSizeY() {
        return imageSizeY;
    }

    /**
     * @param imageSizeY
     */
    public void setImageSizeY(Long imageSizeY) {
        this.imageSizeY = imageSizeY;
    }

    /**
     * @return
     */
    public List<Value> getValues() {
        return values;
    }

    /**
     * @param values
     */
    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productSku", productSku).append("optionId", optionId).append("title", title).append("type", type).append("sortOrder", sortOrder).append("isRequire", isRequire).append("maxCharacters", maxCharacters).append("imageSizeX", imageSizeX).append("imageSizeY", imageSizeY).append("values", values).toString();
    }

}
