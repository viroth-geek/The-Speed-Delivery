package com.planb.thespeed.model.modelForView;

import com.planb.thespeed.model.Entity;

import java.util.List;

/**
 * @author channarith.bong
 */
public class Product extends Entity {

    private Long id;
    private String productUid;
    private String name;
    private String sku;
    private String detail;
    private Float price;
    private String imageUrl;
    private String productType;
    private List<Integer> categoryIds;
    private List<ProductType> productTypes;
    private Integer count;
    private Long parentId;
    private Long storeId;

    /**
     *
     */
    public Product() {
    }

    /**
     *
     * @param id
     * @param name
     * @param sku
     * @param detail
     * @param price
     * @param productType
     * @param count
     */
    public Product(Long id, String name, String sku, String detail, Float price, String productType, int count, Long parentId) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.detail = detail;
        this.price = price;
        this.productType = productType;
        this.count = count;
        this.parentId = parentId;
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
     * @return
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return
     */
    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    /**
     * @param productTypes
     */
    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    /**
     * @return
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return
     */
    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    /**
     * @param categoryIds
     */
    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
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
    public Integer getCount() {
        if (count == null) {
            count = 0;
        }
        return count;
    }

    /**
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     */
    public String getProductType() {
        return productType;
    }

    /**
     *
     * @param productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
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
     *
     * @return
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     *
     * @param storeId
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productUid='" + productUid + '\'' +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", productType='" + productType + '\'' +
                ", categoryIds=" + categoryIds +
                ", productTypes=" + productTypes +
                ", count=" + count +
                ", parentId=" + parentId +
                ", storeId=" + storeId +
                '}';
    }
}
