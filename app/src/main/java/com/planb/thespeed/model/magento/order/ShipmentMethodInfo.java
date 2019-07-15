
package com.planb.thespeed.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

/**
 * @author channarith.bong
 */
public class ShipmentMethodInfo extends Entity {

    @SerializedName("carrier_code")
    @Expose
    private String carrierCode;
    @SerializedName("method_code")
    @Expose
    private String methodCode;
    @SerializedName("carrier_title")
    @Expose
    private String carrierTitle;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("amount")
    @Expose
    private Long amount;
    @SerializedName("base_amount")
    @Expose
    private Long baseAmount;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("price_excl_tax")
    @Expose
    private Long priceExclTax;
    @SerializedName("price_incl_tax")
    @Expose
    private Long priceInclTax;
    private final static long serialVersionUID = -8794965505697729004L;

    /**
     * @return
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * @param carrierCode
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * @return
     */
    public String getMethodCode() {
        return methodCode;
    }

    /**
     * @param methodCode
     */
    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    /**
     * @return
     */
    public String getCarrierTitle() {
        return carrierTitle;
    }

    /**
     * @param carrierTitle
     */
    public void setCarrierTitle(String carrierTitle) {
        this.carrierTitle = carrierTitle;
    }

    /**
     * @return
     */
    public String getMethodTitle() {
        return methodTitle;
    }

    /**
     * @param methodTitle
     */
    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    /**
     * @return
     */
    public Long getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    /**
     * @return
     */
    public Long getBaseAmount() {
        return baseAmount;
    }

    /**
     * @param baseAmount
     */
    public void setBaseAmount(Long baseAmount) {
        this.baseAmount = baseAmount;
    }

    /**
     * @return
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return
     */
    public Long getPriceExclTax() {
        return priceExclTax;
    }

    /**
     * @param priceExclTax
     */
    public void setPriceExclTax(Long priceExclTax) {
        this.priceExclTax = priceExclTax;
    }

    /**
     * @return
     */
    public Long getPriceInclTax() {
        return priceInclTax;
    }

    /**
     * @param priceInclTax
     */
    public void setPriceInclTax(Long priceInclTax) {
        this.priceInclTax = priceInclTax;
    }

}
