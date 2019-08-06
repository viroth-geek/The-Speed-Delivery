
package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author channarith.bong
 */
public class ProductItem extends Entity {

    @SerializedName("item_id")
    @Expose
    private Long itemId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("discount_amount")
    @Expose
    private Long discountAmount;
    @SerializedName("discount_invoiced")
    @Expose
    private Long discountInvoiced;
    @SerializedName("discount_percent")
    @Expose
    private Long discountPercent;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("no_discount")
    @Expose
    private Long noDiscount;
    @SerializedName("order_id")
    @Expose
    private Long orderId;
    @SerializedName("original_price")
    @Expose
    private Double originalPrice;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("price_incl_tax")
    @Expose
    private Double priceInclTax;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("qty_canceled")
    @Expose
    private Long qtyCanceled;
    @SerializedName("qty_invoiced")
    @Expose
    private Long qtyInvoiced;
    @SerializedName("qty_ordered")
    @Expose
    private Integer qtyOrdered;
    @SerializedName("qty_refunded")
    @Expose
    private Long qtyRefunded;
    @SerializedName("qty_shipped")
    @Expose
    private Long qtyShipped;
    @SerializedName("quote_item_id")
    @Expose
    private Long quoteItemId;
    /*@SerializedName("row_invoiced")
    @Expose
    private Long rowInvoiced;*/
    @SerializedName("row_total")
    @Expose
    private Double rowTotal;
    @SerializedName("row_total_incl_tax")
    @Expose
    private Double rowTotalInclTax;
    @SerializedName("row_weight")
    @Expose
    private Long rowWeight;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("store_id")
    @Expose
    private Long storeId;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("tax_invoiced")
    @Expose
    private Long taxInvoiced;
    @SerializedName("tax_percent")
    @Expose
    private Long taxPercent;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    private Long parentId;

    /**
     * @return
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
    public Long getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount
     */
    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return
     */
    public Long getDiscountInvoiced() {
        return discountInvoiced;
    }

    /**
     * @param discountInvoiced
     */
    public void setDiscountInvoiced(Long discountInvoiced) {
        this.discountInvoiced = discountInvoiced;
    }

    /**
     * @return
     */
    public Long getDiscountPercent() {
        return discountPercent;
    }

    /**
     * @param discountPercent
     */
    public void setDiscountPercent(Long discountPercent) {
        this.discountPercent = discountPercent;
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
    public Long getNoDiscount() {
        return noDiscount;
    }

    /**
     * @param noDiscount
     */
    public void setNoDiscount(Long noDiscount) {
        this.noDiscount = noDiscount;
    }

    /**
     * @return
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return
     */
    public Double getOriginalPrice() {
        return originalPrice;
    }

    /**
     * @param originalPrice
     */
    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * @return
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return
     */
    public Double getPriceInclTax() {
        return priceInclTax;
    }

    /**
     * @param priceInclTax
     */
    public void setPriceInclTax(Double priceInclTax) {
        this.priceInclTax = priceInclTax;
    }

    /**
     * @return
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
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
    public Long getQtyCanceled() {
        return qtyCanceled;
    }

    /**
     * @param qtyCanceled
     */
    public void setQtyCanceled(Long qtyCanceled) {
        this.qtyCanceled = qtyCanceled;
    }

    /**
     * @return
     */
    public Long getQtyInvoiced() {
        return qtyInvoiced;
    }

    /**
     * @param qtyInvoiced
     */
    public void setQtyInvoiced(Long qtyInvoiced) {
        this.qtyInvoiced = qtyInvoiced;
    }

    /**
     * @return
     */
    public Integer getQtyOrdered() {
        return qtyOrdered;
    }

    /**
     * @param qtyOrdered
     */
    public void setQtyOrdered(Integer qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    /**
     * @return
     */
    public Long getQtyRefunded() {
        return qtyRefunded;
    }

    /**
     * @param qtyRefunded
     */
    public void setQtyRefunded(Long qtyRefunded) {
        this.qtyRefunded = qtyRefunded;
    }

    /**
     * @return
     */
    public Long getQtyShipped() {
        return qtyShipped;
    }

    /**
     * @param qtyShipped
     */
    public void setQtyShipped(Long qtyShipped) {
        this.qtyShipped = qtyShipped;
    }

    /**
     * @return
     */
    public Long getQuoteItemId() {
        return quoteItemId;
    }

    /**
     * @param quoteItemId
     */
    public void setQuoteItemId(Long quoteItemId) {
        this.quoteItemId = quoteItemId;
    }

    /*public Long getRowInvoiced() {
        return rowInvoiced;
    }

    public void setRowInvoiced(Long rowInvoiced) {
        this.rowInvoiced = rowInvoiced;
    }
*/

    /**
     * @return
     */
    public Double getRowTotal() {
        return rowTotal;
    }

    /**
     * @param rowTotal Double
     */
    public void setRowTotal(Double rowTotal) {
        this.rowTotal = rowTotal;
    }

    /**
     * @return Double
     */
    public Double getRowTotalInclTax() {
        return rowTotalInclTax;
    }

    /**
     * @param rowTotalInclTax Double
     */
    public void setRowTotalInclTax(Double rowTotalInclTax) {
        this.rowTotalInclTax = rowTotalInclTax;
    }

    /**
     * @return Long
     */
    public Long getRowWeight() {
        return rowWeight;
    }

    /**
     * @param rowWeight Long
     */
    public void setRowWeight(Long rowWeight) {
        this.rowWeight = rowWeight;
    }

    /**
     * @return String
     */
    public String getSku() {
        return sku;
    }

    /**
     * @param sku String
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * @return Long
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * @param storeId Long
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * @return Long
     */
    public Double getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount Long
     */
    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return Long
     */
    public Long getTaxInvoiced() {
        return taxInvoiced;
    }

    /**
     * @param taxInvoiced Long
     */
    public void setTaxInvoiced(Long taxInvoiced) {
        this.taxInvoiced = taxInvoiced;
    }

    /**
     * @return Long
     */
    public Long getTaxPercent() {
        return taxPercent;
    }

    /**
     * @param taxPercent Long
     */
    public void setTaxPercent(Long taxPercent) {
        this.taxPercent = taxPercent;
    }

    /**
     * @return String
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt String
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return Long
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     *
     * @param parentId Long
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "itemId=" + itemId +
                ", createdAt='" + createdAt + '\'' +
                ", discountAmount=" + discountAmount +
                ", discountInvoiced=" + discountInvoiced +
                ", discountPercent=" + discountPercent +
                ", name='" + name + '\'' +
                ", noDiscount=" + noDiscount +
                ", orderId=" + orderId +
                ", originalPrice=" + originalPrice +
                ", price=" + price +
                ", priceInclTax=" + priceInclTax +
                ", productId=" + productId +
                ", productType='" + productType + '\'' +
                ", qtyCanceled=" + qtyCanceled +
                ", qtyInvoiced=" + qtyInvoiced +
                ", qtyOrdered=" + qtyOrdered +
                ", qtyRefunded=" + qtyRefunded +
                ", qtyShipped=" + qtyShipped +
                ", quoteItemId=" + quoteItemId +
                ", rowTotal=" + rowTotal +
                ", rowTotalInclTax=" + rowTotalInclTax +
                ", rowWeight=" + rowWeight +
                ", sku='" + sku + '\'' +
                ", storeId=" + storeId +
                ", taxAmount=" + taxAmount +
                ", taxInvoiced=" + taxInvoiced +
                ", taxPercent=" + taxPercent +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
