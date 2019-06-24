
package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("item_id")
    @Expose
    private Long itemId;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("base_price")
    @Expose
    private Double basePrice;
    @SerializedName("qty")
    @Expose
    private Long qty;
    @SerializedName("row_total")
    @Expose
    private Double rowTotal;
    @SerializedName("base_row_total")
    @Expose
    private Double baseRowTotal;
    @SerializedName("row_total_with_discount")
    @Expose
    private Long rowTotalWithDiscount;
    @SerializedName("tax_amount")
    @Expose
    private Long taxAmount;
    @SerializedName("base_tax_amount")
    @Expose
    private Long baseTaxAmount;
    @SerializedName("tax_percent")
    @Expose
    private Long taxPercent;
    @SerializedName("discount_amount")
    @Expose
    private Long discountAmount;
    @SerializedName("base_discount_amount")
    @Expose
    private Long baseDiscountAmount;
    @SerializedName("discount_percent")
    @Expose
    private Long discountPercent;
    @SerializedName("price_incl_tax")
    @Expose
    private Double priceInclTax;
    @SerializedName("base_price_incl_tax")
    @Expose
    private Double basePriceInclTax;
    @SerializedName("row_total_incl_tax")
    @Expose
    private Double rowTotalInclTax;
    @SerializedName("base_row_total_incl_tax")
    @Expose
    private Double baseRowTotalInclTax;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("weee_tax_applied_amount")
    @Expose
    private Object weeeTaxAppliedAmount;
    @SerializedName("weee_tax_applied")
    @Expose
    private Object weeeTaxApplied;
    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = -6072238227197265366L;

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
    public Double getBasePrice() {
        return basePrice;
    }

    /**
     * @param basePrice
     */
    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * @return
     */
    public Long getQty() {
        return qty;
    }

    /**
     * @param qty
     */
    public void setQty(Long qty) {
        this.qty = qty;
    }

    /**
     * @return
     */
    public Double getRowTotal() {
        return rowTotal;
    }

    /**
     * @param rowTotal
     */
    public void setRowTotal(Double rowTotal) {
        this.rowTotal = rowTotal;
    }

    /**
     * @return
     */
    public Double getBaseRowTotal() {
        return baseRowTotal;
    }

    /**
     * @param baseRowTotal
     */
    public void setBaseRowTotal(Double baseRowTotal) {
        this.baseRowTotal = baseRowTotal;
    }

    /**
     * @return
     */
    public Long getRowTotalWithDiscount() {
        return rowTotalWithDiscount;
    }

    /**
     * @param rowTotalWithDiscount
     */
    public void setRowTotalWithDiscount(Long rowTotalWithDiscount) {
        this.rowTotalWithDiscount = rowTotalWithDiscount;
    }

    /**
     * @return
     */
    public Long getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount
     */
    public void setTaxAmount(Long taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return
     */
    public Long getBaseTaxAmount() {
        return baseTaxAmount;
    }

    /**
     * @param baseTaxAmount
     */
    public void setBaseTaxAmount(Long baseTaxAmount) {
        this.baseTaxAmount = baseTaxAmount;
    }

    /**
     * @return
     */
    public Long getTaxPercent() {
        return taxPercent;
    }

    /**
     * @param taxPercent
     */
    public void setTaxPercent(Long taxPercent) {
        this.taxPercent = taxPercent;
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
    public Long getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    /**
     * @param baseDiscountAmount
     */
    public void setBaseDiscountAmount(Long baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
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
    public Double getBasePriceInclTax() {
        return basePriceInclTax;
    }

    /**
     * @param basePriceInclTax
     */
    public void setBasePriceInclTax(Double basePriceInclTax) {
        this.basePriceInclTax = basePriceInclTax;
    }

    /**
     * @return
     */
    public Double getRowTotalInclTax() {
        return rowTotalInclTax;
    }

    /**
     * @param rowTotalInclTax
     */
    public void setRowTotalInclTax(Double rowTotalInclTax) {
        this.rowTotalInclTax = rowTotalInclTax;
    }

    /**
     * @return
     */
    public Double getBaseRowTotalInclTax() {
        return baseRowTotalInclTax;
    }

    /**
     * @param baseRowTotalInclTax
     */
    public void setBaseRowTotalInclTax(Double baseRowTotalInclTax) {
        this.baseRowTotalInclTax = baseRowTotalInclTax;
    }

    /**
     * @return
     */
    public String getOptions() {
        return options;
    }

    /**
     * @param options
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * @return
     */
    public Object getWeeeTaxAppliedAmount() {
        return weeeTaxAppliedAmount;
    }

    /**
     * @param weeeTaxAppliedAmount
     */
    public void setWeeeTaxAppliedAmount(Object weeeTaxAppliedAmount) {
        this.weeeTaxAppliedAmount = weeeTaxAppliedAmount;
    }

    /**
     * @return
     */
    public Object getWeeeTaxApplied() {
        return weeeTaxApplied;
    }

    /**
     * @param weeeTaxApplied
     */
    public void setWeeeTaxApplied(Object weeeTaxApplied) {
        this.weeeTaxApplied = weeeTaxApplied;
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

}
