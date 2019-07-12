package com.planb.thespeed.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author yeakleang.ay on 7/23/18.
 */

public class ExtensionAttribute {

    @SerializedName("custom_options")
    @Expose
    private List<CustomOption> customOptions;

    @SerializedName("configurable_item_options")
    @Expose
    private List<ItemOption> itemOptions;

    /**
     * @see CustomOption
     * @return CustomOption
     */
    public List<CustomOption> getCustomOptions() {
        return customOptions;
    }

    /**
     * @see CustomOption
     * @param customOptions List of CustomOption
     */
    public void setCustomOptions(List<CustomOption> customOptions) {
        this.customOptions = customOptions;
    }

    /**
     * @see ItemOption
     * @return ItemOption
     */
    public List<ItemOption> getItemOptions() {
        return itemOptions;
    }

    /**
     * @see ItemOption
     * @param itemOptions ItemOption
     */
    public void setItemOptions(List<ItemOption> itemOptions) {
        this.itemOptions = itemOptions;
    }
}
