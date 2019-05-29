package com.iota.eshopping.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 7/23/18.
 */

public class ProductOption {

    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttribute extensionAttribute;

    /**
     * @see ExtensionAttribute
     * @return ExtensionAttribute
     */
    public ExtensionAttribute getExtensionAttribute() {
        return extensionAttribute;
    }

    /**
     * @see ExtensionAttribute
     * @param extensionAttribute ExtensionAttribute
     */
    public void setExtensionAttribute(ExtensionAttribute extensionAttribute) {
        this.extensionAttribute = extensionAttribute;
    }

    @Override
    public String toString() {
        return "ProductOption{" +
                "extensionAttribute=" + extensionAttribute +
                '}';
    }
}
