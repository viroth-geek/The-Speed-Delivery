
package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class Total implements Serializable {

    @SerializedName("base_shipping_amount")
    @Expose
    private Long baseShippingAmount;
    @SerializedName("base_shipping_discount_amount")
    @Expose
    private Long baseShippingDiscountAmount;
    @SerializedName("base_shipping_incl_tax")
    @Expose
    private Long baseShippingInclTax;
    @SerializedName("base_shipping_tax_amount")
    @Expose
    private Long baseShippingTaxAmount;
    @SerializedName("shipping_amount")
    @Expose
    private Long shippingAmount;
    @SerializedName("shipping_discount_amount")
    @Expose
    private Long shippingDiscountAmount;
    @SerializedName("shipping_discount_tax_compensation_amount")
    @Expose
    private Double shippingDiscountTaxCompensationAmount;
    @SerializedName("shipping_incl_tax")
    @Expose
    private Double shippingInclTax;
    @SerializedName("shipping_tax_amount")
    @Expose
    private Double shippingTaxAmount;
    private final static long serialVersionUID = 2486063308364041606L;

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
     * Get shippingDiscountTaxCompensationAmount
     *
     * @return shippingDiscountTaxCompensationAmount
     */
    public Double getShippingDiscountTaxCompensationAmount() {
        return shippingDiscountTaxCompensationAmount;
    }

    /**
     * Setter shippingDiscountTaxCompensationAmount
     */
    public void setShippingDiscountTaxCompensationAmount(Double shippingDiscountTaxCompensationAmount) {
        this.shippingDiscountTaxCompensationAmount = shippingDiscountTaxCompensationAmount;
    }

    /**
     * Get shippingInclTax
     *
     * @return shippingInclTax
     */
    public Double getShippingInclTax() {
        return shippingInclTax;
    }

    /**
     * Setter shippingInclTax
     */
    public void setShippingInclTax(Double shippingInclTax) {
        this.shippingInclTax = shippingInclTax;
    }

    /**
     * Get shippingTaxAmount
     *
     * @return shippingTaxAmount
     */
    public Double getShippingTaxAmount() {
        return shippingTaxAmount;
    }

    /**
     * Setter shippingTaxAmount
     */
    public void setShippingTaxAmount(Double shippingTaxAmount) {
        this.shippingTaxAmount = shippingTaxAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("baseShippingAmount", baseShippingAmount).append("baseShippingDiscountAmount", baseShippingDiscountAmount).append("baseShippingInclTax", baseShippingInclTax).append("baseShippingTaxAmount", baseShippingTaxAmount).append("shippingAmount", shippingAmount).append("shippingDiscountAmount", shippingDiscountAmount).append("shippingDiscountTaxCompensationAmount", shippingDiscountTaxCompensationAmount).append("shippingInclTax", shippingInclTax).append("shippingTaxAmount", shippingTaxAmount).toString();
    }

}
