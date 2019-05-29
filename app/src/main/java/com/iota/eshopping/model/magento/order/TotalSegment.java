
package com.iota.eshopping.model.magento.order;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.magento.product.ExtensionAttributes;

public class TotalSegment implements Serializable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttributes extensionAttributes;
    @SerializedName("area")
    @Expose
    private String area;
    private final static long serialVersionUID = -925227926301289408L;

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

    /**
     * @return
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(Double value) {
        this.value = value;
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
    public String getArea() {
        return area;
    }

    /**
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

}
