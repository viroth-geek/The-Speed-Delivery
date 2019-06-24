package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Totals implements Serializable {

    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("base_grand_total")
    @Expose
    private Double baseGrandTotal;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("base_subtotal")
    @Expose
    private Double baseSubtotal;
    @SerializedName("discount_amount")
    @Expose
    private Long discountAmount;
    @SerializedName("base_discount_amount")
    @Expose
    private Long baseDiscountAmount;
    @SerializedName("subtotal_with_discount")
    @Expose
    private Double subtotalWithDiscount;
    @SerializedName("base_subtotal_with_discount")
    @Expose
    private Double baseSubtotalWithDiscount;
    @SerializedName("shipping_amount")
    @Expose
    private Long shippingAmount;
    @SerializedName("base_shipping_amount")
    @Expose
    private Long baseShippingAmount;
    @SerializedName("shipping_discount_amount")
    @Expose
    private Long shippingDiscountAmount;
    @SerializedName("base_shipping_discount_amount")
    @Expose
    private Long baseShippingDiscountAmount;
    @SerializedName("tax_amount")
    @Expose
    private Long taxAmount;
    @SerializedName("base_tax_amount")
    @Expose
    private Long baseTaxAmount;
    @SerializedName("weee_tax_applied_amount")
    @Expose
    private Object weeeTaxAppliedAmount;
    @SerializedName("shipping_tax_amount")
    @Expose
    private Long shippingTaxAmount;
    @SerializedName("base_shipping_tax_amount")
    @Expose
    private Long baseShippingTaxAmount;
    @SerializedName("subtotal_incl_tax")
    @Expose
    private Double subtotalInclTax;
    @SerializedName("shipping_incl_tax")
    @Expose
    private Long shippingInclTax;
    @SerializedName("base_shipping_incl_tax")
    @Expose
    private Long baseShippingInclTax;
    @SerializedName("base_currency_code")
    @Expose
    private String baseCurrencyCode;
    @SerializedName("quote_currency_code")
    @Expose
    private String quoteCurrencyCode;
    @SerializedName("items_qty")
    @Expose
    private Long itemsQty;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("total_segments")
    @Expose
    private List<TotalSegment> totalSegments = null;
    private final static long serialVersionUID = -4314119023303207686L;

    /**
     * @return
     */
    public Double getGrandTotal() {
        return grandTotal;
    }

    /**
     * @param grandTotal
     */
    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * @return
     */
    public Double getBaseGrandTotal() {
        return baseGrandTotal;
    }

    /**
     * @param baseGrandTotal
     */
    public void setBaseGrandTotal(Double baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    /**
     * @return
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return
     */
    public Double getBaseSubtotal() {
        return baseSubtotal;
    }

    /**
     * @param baseSubtotal
     */
    public void setBaseSubtotal(Double baseSubtotal) {
        this.baseSubtotal = baseSubtotal;
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
    public Double getSubtotalWithDiscount() {
        return subtotalWithDiscount;
    }

    /**
     * @param subtotalWithDiscount
     */
    public void setSubtotalWithDiscount(Double subtotalWithDiscount) {
        this.subtotalWithDiscount = subtotalWithDiscount;
    }

    /**
     * @return
     */
    public Double getBaseSubtotalWithDiscount() {
        return baseSubtotalWithDiscount;
    }

    /**
     * @param baseSubtotalWithDiscount
     */
    public void setBaseSubtotalWithDiscount(Double baseSubtotalWithDiscount) {
        this.baseSubtotalWithDiscount = baseSubtotalWithDiscount;
    }

    /**
     * @return
     */
    public Long getShippingAmount() {
        return shippingAmount;
    }

    /**
     * @param shippingAmount
     */
    public void setShippingAmount(Long shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    /**
     * @return
     */
    public Long getBaseShippingAmount() {
        return baseShippingAmount;
    }

    /**
     * @param baseShippingAmount
     */
    public void setBaseShippingAmount(Long baseShippingAmount) {
        this.baseShippingAmount = baseShippingAmount;
    }

    /**
     * @return
     */
    public Long getShippingDiscountAmount() {
        return shippingDiscountAmount;
    }

    /**
     * @param shippingDiscountAmount
     */
    public void setShippingDiscountAmount(Long shippingDiscountAmount) {
        this.shippingDiscountAmount = shippingDiscountAmount;
    }

    /**
     * @return
     */
    public Long getBaseShippingDiscountAmount() {
        return baseShippingDiscountAmount;
    }

    /**
     * @param baseShippingDiscountAmount
     */
    public void setBaseShippingDiscountAmount(Long baseShippingDiscountAmount) {
        this.baseShippingDiscountAmount = baseShippingDiscountAmount;
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
    public Long getShippingTaxAmount() {
        return shippingTaxAmount;
    }

    /**
     * @param shippingTaxAmount
     */
    public void setShippingTaxAmount(Long shippingTaxAmount) {
        this.shippingTaxAmount = shippingTaxAmount;
    }

    /**
     * @return
     */
    public Long getBaseShippingTaxAmount() {
        return baseShippingTaxAmount;
    }

    /**
     * @param baseShippingTaxAmount
     */
    public void setBaseShippingTaxAmount(Long baseShippingTaxAmount) {
        this.baseShippingTaxAmount = baseShippingTaxAmount;
    }

    /**
     * @return
     */
    public Double getSubtotalInclTax() {
        return subtotalInclTax;
    }

    /**
     * @param subtotalInclTax
     */
    public void setSubtotalInclTax(Double subtotalInclTax) {
        this.subtotalInclTax = subtotalInclTax;
    }

    /**
     * @return
     */
    public Long getShippingInclTax() {
        return shippingInclTax;
    }

    /**
     * @param shippingInclTax
     */
    public void setShippingInclTax(Long shippingInclTax) {
        this.shippingInclTax = shippingInclTax;
    }

    /**
     * @return
     */
    public Long getBaseShippingInclTax() {
        return baseShippingInclTax;
    }

    /**
     * @param baseShippingInclTax
     */
    public void setBaseShippingInclTax(Long baseShippingInclTax) {
        this.baseShippingInclTax = baseShippingInclTax;
    }

    /**
     * @return
     */
    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    /**
     * @param baseCurrencyCode
     */
    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    /**
     * @return
     */
    public String getQuoteCurrencyCode() {
        return quoteCurrencyCode;
    }

    /**
     * @param quoteCurrencyCode
     */
    public void setQuoteCurrencyCode(String quoteCurrencyCode) {
        this.quoteCurrencyCode = quoteCurrencyCode;
    }

    /**
     * @return
     */
    public Long getItemsQty() {
        return itemsQty;
    }

    /**
     * @param itemsQty
     */
    public void setItemsQty(Long itemsQty) {
        this.itemsQty = itemsQty;
    }

    /**
     * @return
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return
     */
    public List<TotalSegment> getTotalSegments() {
        return totalSegments;
    }


}
