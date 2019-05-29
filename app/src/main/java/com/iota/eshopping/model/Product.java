
package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.magento.product.ExtensionAttributes;
import com.iota.eshopping.model.magento.product.MediaGalleryEntry;
import com.iota.eshopping.model.magento.product.Option;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * @author channarith.bong
 */
public class Product extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String detail;
    @SerializedName("attribute_set_id")
    @Expose
    private Long attributeSetId;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("status")
    @Expose
    private Long status;
    @SerializedName("visibility")
    @Expose
    private Long visibility;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;
    @SerializedName("product_links")
    @Expose
    private List<Object> productLinks = null;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("media_gallery_entries")
    @Expose
    private List<MediaGalleryEntry> mediaGalleryEntries = null;
    @SerializedName("tier_prices")
    @Expose
    private List<Object> tierPrices = null;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    private Long parentId;

    /*@SerializedName("custom_attributes")
    @Expose
    private List<CustomAttribute> customAttributes = null;*/

    @SerializedName("custom_attributes")
    @Expose
    private CustomAttributes customAttribute = null;

    private final static long serialVersionUID = -5210719974528125842L;

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
     *
     * @return
     */
    public Long getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
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
     * Get detail
     *
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Setter detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return
     */
    public Long getAttributeSetId() {
        return attributeSetId;
    }

    /**
     * @param attributeSetId
     */
    public void setAttributeSetId(Long attributeSetId) {
        this.attributeSetId = attributeSetId;
    }

    /**
     * @return
     */
    public Double getPrice() {
        return price == null ? 0d : price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     *
     * @return
     */
    public Integer getQty() {
        return qty;
    }

    /**
     *
     * @param qty
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * @return
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * @return
     */
    public Long getVisibility() {
        return visibility;
    }

    /**
     * @param visibility
     */
    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    /**
     * @return
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
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
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return
     */
    public ExtensionAttributes getExtensionAttributes() {
        return extensionAttributes;
    }

    /**
     * @param extensionAttributes
     */
    public void setExtensionAttributes(ExtensionAttributes extensionAttributes) {
        this.extensionAttributes = extensionAttributes;
    }

    /**
     * @return
     */
    public List<Object> getProductLinks() {
        return productLinks;
    }

    /**
     * @param productLinks
     */
    public void setProductLinks(List<Object> productLinks) {
        this.productLinks = productLinks;
    }

    /**
     * @return
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * @param options
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    /**
     * @return
     */
    public List<MediaGalleryEntry> getMediaGalleryEntries() {
        return mediaGalleryEntries;
    }

    /**
     * @param mediaGalleryEntries
     */
    public void setMediaGalleryEntries(List<MediaGalleryEntry> mediaGalleryEntries) {
        this.mediaGalleryEntries = mediaGalleryEntries;
    }

    /**
     * @return
     */
    public List<Object> getTierPrices() {
        return tierPrices;
    }

    /**
     * @param tierPrices
     */
    public void setTierPrices(List<Object> tierPrices) {
        this.tierPrices = tierPrices;
    }

    //TODO Changing CustomAttribute
   /* public List<CustomAttribute> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(List<CustomAttribute> customAttributes) {
        this.customAttributes = customAttributes;
    }*/

    /**
     * @return
     */
    public CustomAttributes getCustomAttribute() {
        return customAttribute;
    }

    /**
     * @param customAttribute
     */
    public void setCustomAttribute(CustomAttributes customAttribute) {
        this.customAttribute = customAttribute;
    }

    /**
     *
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     *
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
     * Get serialVersionUID
     *
     * @return serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", attributeSetId=" + attributeSetId +
                ", price=" + price +
                ", qty=" + qty +
                ", status=" + status +
                ", visibility=" + visibility +
                ", productType='" + productType + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", extensionAttributes=" + extensionAttributes +
                ", productLinks=" + productLinks +
                ", options=" + options +
                ", mediaGalleryEntries=" + mediaGalleryEntries +
                ", tierPrices=" + tierPrices +
                ", parentId=" + parentId +
                ", customAttribute=" + customAttribute +
                '}';
    }
}
