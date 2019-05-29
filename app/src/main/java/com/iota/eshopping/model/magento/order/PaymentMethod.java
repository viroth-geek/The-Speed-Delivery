
package com.iota.eshopping.model.magento.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentMethod implements Serializable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("title")
    @Expose
    private String title;
    private final static long serialVersionUID = -3304582527399279224L;

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
