
package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author channarith.bong
 */
public class CustomAttributes extends Entity {

    @SerializedName("meta_title")
    @Expose
    private List<String> metaTitle = null;
    @SerializedName("meta_keyword")
    @Expose
    private List<String> metaKeyword = null;
    @SerializedName("meta_description")
    @Expose
    private List<String> metaDescription = null;
    @SerializedName("image")
    @Expose
    private List<String> image = null;
    @SerializedName("small_image")
    @Expose
    private List<String> smallImage = null;
    @SerializedName("thumbnail")
    @Expose
    private List<String> thumbnail = null;
    @SerializedName("news_from_date")
    @Expose
    private List<String> newsFromDate = null;
    @SerializedName("news_to_date")
    @Expose
    private List<String> newsToDate = null;
    @SerializedName("options_container")
    @Expose
    private List<String> optionsContainer = null;
    @SerializedName("required_options")
    @Expose
    private List<String> requiredOptions = null;
    @SerializedName("has_options")
    @Expose
    private List<String> hasOptions = null;
    @SerializedName("url_key")
    @Expose
    private List<String> urlKey = null;
    @SerializedName("gift_message_available")
    @Expose
    private List<String> giftMessageAvailable = null;
    @SerializedName("swatch_image")
    @Expose
    private List<String> swatchImage = null;
    @SerializedName("tax_class_id")
    @Expose
    private List<String> taxClassId = null;
    private final static long serialVersionUID = -4882828886926908030L;

    /**
     * @return
     */
    public List<String> getMetaTitle() {
        return metaTitle;
    }

    /**
     * @param metaTitle
     */
    public void setMetaTitle(List<String> metaTitle) {
        this.metaTitle = metaTitle;
    }

    /**
     * @return
     */
    public List<String> getMetaKeyword() {
        return metaKeyword;
    }

    /**
     * @param metaKeyword
     */
    public void setMetaKeyword(List<String> metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    /**
     * @return
     */
    public List<String> getMetaDescription() {
        return metaDescription;
    }

    /**
     * @param metaDescription
     */
    public void setMetaDescription(List<String> metaDescription) {
        this.metaDescription = metaDescription;
    }

    /**
     * @return
     */
    public List<String> getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(List<String> image) {
        this.image = image;
    }

    /**
     * @return
     */
    public List<String> getSmallImage() {
        return smallImage;
    }

    /**
     * @param smallImage
     */
    public void setSmallImage(List<String> smallImage) {
        this.smallImage = smallImage;
    }

    /**
     * @return
     */
    public List<String> getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail
     */
    public void setThumbnail(List<String> thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return
     */
    public List<String> getNewsFromDate() {
        return newsFromDate;
    }

    /**
     * @param newsFromDate
     */
    public void setNewsFromDate(List<String> newsFromDate) {
        this.newsFromDate = newsFromDate;
    }

    /**
     * @return
     */
    public List<String> getNewsToDate() {
        return newsToDate;
    }

    /**
     * @param newsToDate
     */
    public void setNewsToDate(List<String> newsToDate) {
        this.newsToDate = newsToDate;
    }

    /**
     * @return
     */
    public List<String> getOptionsContainer() {
        return optionsContainer;
    }

    /**
     * @param optionsContainer
     */
    public void setOptionsContainer(List<String> optionsContainer) {
        this.optionsContainer = optionsContainer;
    }

    /**
     * @return
     */
    public List<String> getRequiredOptions() {
        return requiredOptions;
    }

    /**
     * @param requiredOptions
     */
    public void setRequiredOptions(List<String> requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    /**
     * @return
     */
    public List<String> getHasOptions() {
        return hasOptions;
    }

    /**
     * @param hasOptions
     */
    public void setHasOptions(List<String> hasOptions) {
        this.hasOptions = hasOptions;
    }

    /**
     * @return
     */
    public List<String> getUrlKey() {
        return urlKey;
    }

    /**
     * @param urlKey
     */
    public void setUrlKey(List<String> urlKey) {
        this.urlKey = urlKey;
    }

    /**
     * @return
     */
    public List<String> getGiftMessageAvailable() {
        return giftMessageAvailable;
    }

    /**
     * @param giftMessageAvailable
     */
    public void setGiftMessageAvailable(List<String> giftMessageAvailable) {
        this.giftMessageAvailable = giftMessageAvailable;
    }

    /**
     * @return
     */
    public List<String> getSwatchImage() {
        return swatchImage;
    }

    /**
     * @param swatchImage
     */
    public void setSwatchImage(List<String> swatchImage) {
        this.swatchImage = swatchImage;
    }

    /**
     * @return
     */
    public List<String> getTaxClassId() {
        return taxClassId;
    }

    /**
     * @param taxClassId
     */
    public void setTaxClassId(List<String> taxClassId) {
        this.taxClassId = taxClassId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("metaTitle", metaTitle).append("metaKeyword", metaKeyword).append("metaDescription", metaDescription).append("image", image).append("smallImage", smallImage).append("thumbnail", thumbnail).append("newsFromDate", newsFromDate).append("newsToDate", newsToDate).append("optionsContainer", optionsContainer).append("requiredOptions", requiredOptions).append("hasOptions", hasOptions).append("urlKey", urlKey).append("giftMessageAvailable", giftMessageAvailable).append("swatchImage", swatchImage).append("taxClassId", taxClassId).toString();
    }

}
