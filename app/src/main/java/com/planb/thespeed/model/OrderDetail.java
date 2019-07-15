
package com.planb.thespeed.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.magento.order.ExtensionAttributes;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author channarith.bong
 */
public class OrderDetail extends Entity implements Comparable<OrderDetail> {

    @SerializedName("entity_id")
    @Expose
    private Long id;
    @SerializedName("billing_address_id")
    @Expose
    private Long billingAddressId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_firstname")
    @Expose
    private String customerFirstname;
    @SerializedName("customer_group_id")
    @Expose
    private Long customerGroupId;
    @SerializedName("customer_id")
    @Expose
    private Long customerId;
    @SerializedName("customer_is_guest")
    @Expose
    private Long customerIsGuest;
    @SerializedName("customer_lastname")
    @Expose
    private String customerLastname;
    @SerializedName("customer_note_notify")
    @Expose
    private Long customerNoteNotify;
    @SerializedName("discount_amount")
    @Expose
    private Long discountAmount;
    @SerializedName("email_sent")
    @Expose
    private Long emailSent;
    @SerializedName("quote_id")
    @Expose
    private Long quoteId;
    @SerializedName("remote_ip")
    @Expose
    private String remoteIp;
    @SerializedName("shipping_amount")
    @Expose
    private Double shippingAmount;
    @SerializedName("shipping_description")
    @Expose
    private String shippingDescription;
    @SerializedName("shipping_discount_amount")
    @Expose
    private Double shippingDiscountAmount;
    @SerializedName("shipping_discount_tax_compensation_amount")
    @Expose
    private Double shippingDiscountTaxCompensationAmount;
    @SerializedName("shipping_incl_tax")
    @Expose
    private Double shippingInclTax;
    @SerializedName("shipping_tax_amount")
    @Expose
    private Double shippingTaxAmount;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("store_currency_code")
    @Expose
    private String storeCurrencyCode;
    @SerializedName("store_id")
    @Expose
    private Long storeId;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_to_base_rate")
    @Expose
    private Long storeToBaseRate;
    @SerializedName("store_to_order_rate")
    @Expose
    private Long storeToOrderRate;
    @SerializedName("subtotal")
    @Expose
    private Double subtotal;
    @SerializedName("subtotal_incl_tax")
    @Expose
    private Double subtotalInclTax;
    @SerializedName("tax_amount")
    @Expose
    private Double taxAmount;
    @SerializedName("grand_total")
    @Expose
    private Double totalDue;
    @SerializedName("total_item_count")
    @Expose
    private Long totalItemCount;
    @SerializedName("total_qty_ordered")
    @Expose
    private Long totalQtyOrdered;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("weight")
    @Expose
    private Long weight;
    @SerializedName("is_virtual")
    @Expose
    private int isVirtual;
    @SerializedName("items")
    @Expose
    private List<com.planb.thespeed.model.modelForView.ProductItem> productItems = null;
    @SerializedName("billing_address")
    @Expose
    private Address billingAddress;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("status_histories")
    @Expose
    private List<StatusHistory> statusHistories = null;
    @SerializedName("increment_id")
    @Expose
    private String incrementId;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;

    /**
     * @return
     */
    public Long getBillingAddressId() {
        return billingAddressId;
    }

    /**
     * @param billingAddressId
     */
    public void setBillingAddressId(Long billingAddressId) {
        this.billingAddressId = billingAddressId;
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
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * @param customerEmail
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * @return
     */
    public String getCustomerFirstname() {
        return customerFirstname;
    }

    /**
     * @param customerFirstname
     */
    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    /**
     * @return
     */
    public Long getCustomerGroupId() {
        return customerGroupId;
    }

    /**
     * @param customerGroupId
     */
    public void setCustomerGroupId(Long customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    /**
     * @return
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * @return
     */
    public Long getCustomerIsGuest() {
        return customerIsGuest;
    }

    /**
     * @param customerIsGuest
     */
    public void setCustomerIsGuest(Long customerIsGuest) {
        this.customerIsGuest = customerIsGuest;
    }

    /**
     * @return
     */
    public String getCustomerLastname() {
        return customerLastname;
    }

    /**
     * @param customerLastname
     */
    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    /**
     * @return
     */
    public Long getCustomerNoteNotify() {
        return customerNoteNotify;
    }

    /**
     * @param customerNoteNotify
     */
    public void setCustomerNoteNotify(Long customerNoteNotify) {
        this.customerNoteNotify = customerNoteNotify;
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
    public Long getEmailSent() {
        return emailSent;
    }

    /**
     * @param emailSent
     */
    public void setEmailSent(Long emailSent) {
        this.emailSent = emailSent;
    }

    /**
     * @return
     */
    public Long getQuoteId() {
        return quoteId;
    }

    /**
     * @param quoteId
     */
    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    /**
     * @return
     */
    public String getRemoteIp() {
        return remoteIp;
    }

    /**
     * @param remoteIp
     */
    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    /**
     * @return
     */
    public String getShippingDescription() {
        return shippingDescription;
    }

    /**
     * @param shippingDescription
     */
    public void setShippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    /**
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return
     */
    public String getStoreCurrencyCode() {
        return storeCurrencyCode;
    }

    /**
     * @param storeCurrencyCode
     */
    public void setStoreCurrencyCode(String storeCurrencyCode) {
        this.storeCurrencyCode = storeCurrencyCode;
    }

    /**
     * @return
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * @param storeId
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     * @return
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return
     */
    public Long getStoreToBaseRate() {
        return storeToBaseRate;
    }

    /**
     * @param storeToBaseRate
     */
    public void setStoreToBaseRate(Long storeToBaseRate) {
        this.storeToBaseRate = storeToBaseRate;
    }

    /**
     * @return
     */
    public Long getStoreToOrderRate() {
        return storeToOrderRate;
    }

    /**
     * @param storeToOrderRate
     */
    public void setStoreToOrderRate(Long storeToOrderRate) {
        this.storeToOrderRate = storeToOrderRate;
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
    public Double getTaxAmount() {
        return taxAmount;
    }

    /**
     * @param taxAmount
     */
    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * @return
     */
    public Double getTotalDue() {
        return totalDue;
    }

    /**
     * @param totalDue
     */
    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    /**
     * @return
     */
    public Long getTotalItemCount() {
        return totalItemCount;
    }

    /**
     * @param totalItemCount
     */
    public void setTotalItemCount(Long totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    /**
     * @return
     */
    public Long getTotalQtyOrdered() {
        return totalQtyOrdered;
    }

    /**
     * @param totalQtyOrdered
     */
    public void setTotalQtyOrdered(Long totalQtyOrdered) {
        this.totalQtyOrdered = totalQtyOrdered;
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
    public Long getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(Long weight) {
        this.weight = weight;
    }

    /**
     * @return
     */
    public List<com.planb.thespeed.model.modelForView.ProductItem> getProductItems() {
        return productItems;
    }

    /**
     * @param productItems
     */
    public void setProductItems(List<com.planb.thespeed.model.modelForView.ProductItem> productItems) {
        this.productItems = productItems;
    }

    /**
     * @return
     */
    public Address getBillingAddress() {
        return billingAddress;
    }

    /**
     * @param billingAddress
     */
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * @return
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * @param payment
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * @return
     */
    public List<StatusHistory> getStatusHistories() {
        return statusHistories;
    }

    /**
     * @param statusHistories
     */
    public void setStatusHistories(List<StatusHistory> statusHistories) {
        this.statusHistories = statusHistories;
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
     * @return
     */
    public String getIncrementId() {
        return incrementId;
    }

    /**
     * @param incrementId
     */
    public void setIncrementId(String incrementId) {
        this.incrementId = incrementId;
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
    public int getIsVirtual() {
        return isVirtual;
    }

    /**
     * @param virtual
     */
    public void setVirtual(int virtual) {
        isVirtual = virtual;
    }

    @Override
    public int compareTo(@NonNull OrderDetail orderDetail) {
        return Timestamp.valueOf(orderDetail.createdAt).compareTo(Timestamp.valueOf(this.createdAt));
    }

    /**
     * Get shippingAmount
     *
     * @return shippingAmount
     */
    public Double getShippingAmount() {
        return shippingAmount;
    }

    /**
     * Setter shippingAmount
     */
    public void setShippingAmount(Double shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    /**
     * Get shippingDiscountAmount
     *
     * @return shippingDiscountAmount
     */
    public Double getShippingDiscountAmount() {
        return shippingDiscountAmount;
    }

    /**
     * Setter shippingDiscountAmount
     */
    public void setShippingDiscountAmount(Double shippingDiscountAmount) {
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
        return "OrderDetail{" +
                "id=" + id +
                ", billingAddressId=" + billingAddressId +
                ", createdAt='" + createdAt + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerFirstname='" + customerFirstname + '\'' +
                ", customerGroupId=" + customerGroupId +
                ", customerId=" + customerId +
                ", customerIsGuest=" + customerIsGuest +
                ", customerLastname='" + customerLastname + '\'' +
                ", customerNoteNotify=" + customerNoteNotify +
                ", discountAmount=" + discountAmount +
                ", emailSent=" + emailSent +
                ", quoteId=" + quoteId +
                ", remoteIp='" + remoteIp + '\'' +
                ", shippingAmount=" + shippingAmount +
                ", shippingDescription='" + shippingDescription + '\'' +
                ", shippingDiscountAmount=" + shippingDiscountAmount +
                ", shippingDiscountTaxCompensationAmount=" + shippingDiscountTaxCompensationAmount +
                ", shippingInclTax=" + shippingInclTax +
                ", shippingTaxAmount=" + shippingTaxAmount +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", storeCurrencyCode='" + storeCurrencyCode + '\'' +
                ", storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", storeToBaseRate=" + storeToBaseRate +
                ", storeToOrderRate=" + storeToOrderRate +
                ", subtotal=" + subtotal +
                ", subtotalInclTax=" + subtotalInclTax +
                ", taxAmount=" + taxAmount +
                ", totalDue=" + totalDue +
                ", totalItemCount=" + totalItemCount +
                ", totalQtyOrdered=" + totalQtyOrdered +
                ", updatedAt='" + updatedAt + '\'' +
                ", weight=" + weight +
                ", isVirtual=" + isVirtual +
                ", productItems=" + productItems +
                ", billingAddress=" + billingAddress +
                ", payment=" + payment +
                ", statusHistories=" + statusHistories +
                ", incrementId='" + incrementId + '\'' +
                ", extensionAttributes=" + extensionAttributes +
                '}';
    }
}
