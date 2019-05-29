
package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author channarith.bong
 */
public class Payment extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("account_status")
    @Expose
    private Object accountStatus;
    @SerializedName("additional_information")
    @Expose
    private List<String> additionalInformation = null;
    @SerializedName("amount_ordered")
    @Expose
    private Double amountOrdered;
    @SerializedName("base_amount_ordered")
    @Expose
    private Double baseAmountOrdered;
    @SerializedName("base_shipping_amount")
    @Expose
    private Long baseShippingAmount;
    @SerializedName("cc_last4")
    @Expose
    private Object ccLast4;
    @SerializedName("entity_id")
    @Expose
    private Long entityId;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("parent_id")
    @Expose
    private Long parentId;
    @SerializedName("shipping_amount")
    @Expose
    private Long shippingAmount;
    private final static long serialVersionUID = -7821626834754930427L;

    /**
     * @return
     */
    public Object getAccountStatus() {
        return accountStatus;
    }

    /**
     * @param accountStatus
     */
    public void setAccountStatus(Object accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * @return
     */
    public List<String> getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * @param additionalInformation
     */
    public void setAdditionalInformation(List<String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    /**
     * @return
     */
    public Double getAmountOrdered() {
        return amountOrdered;
    }

    /**
     * @param amountOrdered
     */
    public void setAmountOrdered(Double amountOrdered) {
        this.amountOrdered = amountOrdered;
    }

    /**
     * @return
     */
    public Double getBaseAmountOrdered() {
        return baseAmountOrdered;
    }

    /**
     * @param baseAmountOrdered
     */
    public void setBaseAmountOrdered(Double baseAmountOrdered) {
        this.baseAmountOrdered = baseAmountOrdered;
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
    public Object getCcLast4() {
        return ccLast4;
    }

    /**
     * @param ccLast4
     */
    public void setCcLast4(Object ccLast4) {
        this.ccLast4 = ccLast4;
    }

    /**
     * @return
     */
    public Long getEntityId() {
        return entityId;
    }

    /**
     * @param entityId
     */
    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    /**
     * @return
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accountStatus", accountStatus).append("additionalInformation", additionalInformation).append("amountOrdered", amountOrdered).append("baseAmountOrdered", baseAmountOrdered).append("baseShippingAmount", baseShippingAmount).append("ccLast4", ccLast4).append("entityId", entityId).append("method", method).append("parentId", parentId).append("shippingAmount", shippingAmount).toString();
    }

}
