package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay
 */
public class ConfigurationProductOption {

    @SerializedName("extension_attributes")
    @Expose
    private ExtensionAttribute extensionAttribute;

    @SerializedName("options")
    @Expose
    private List<OptionProduct> optionProducts;

    /**
     *
     * @return ExtensionAttribute
     */
    public ExtensionAttribute getExtensionAttribute() {
        return extensionAttribute;
    }

    /**
     *
     * @param extensionAttribute ExtensionAttribute
     */
    public void setExtensionAttribute(ExtensionAttribute extensionAttribute) {
        this.extensionAttribute = extensionAttribute;
    }

    /**
     *
     * @return list of OptionProduct
     */
    public List<OptionProduct> getOptionProducts() {
        return optionProducts;
    }

    /**
     *
     * @param optionProducts list of OptionProduct
     */
    public void setOptionProducts(List<OptionProduct> optionProducts) {
        this.optionProducts = optionProducts;
    }

    @Override
    public String toString() {
        return "ConfigurationProductOption{" +
                "extensionAttribute=" + extensionAttribute +
                ", optionProducts=" + optionProducts +
                '}';
    }
}
