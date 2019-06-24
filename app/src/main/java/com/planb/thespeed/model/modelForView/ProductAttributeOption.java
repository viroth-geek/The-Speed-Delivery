package com.planb.thespeed.model.modelForView;

import com.planb.thespeed.model.Entity;
import com.planb.thespeed.model.OptionProduct;
import com.planb.thespeed.model.ProductOption;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class ProductAttributeOption extends Entity {

    private List<ProductOption> productOptions;

    private List<OptionProduct> optionProducts;

    /**
     *
     * @return ProductOption
     */
    public List<ProductOption> getProductOptions() {
        if (productOptions == null) {
            productOptions = new ArrayList<>();
        }
        return productOptions;
    }

    /**
     *
     * @param productOptions ProductOption
     */
    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }

    /**
     *
     * @return OptionProduct
     */
    public List<OptionProduct> getOptionProducts() {
        if (optionProducts == null) {
            optionProducts = new ArrayList<>();
        }
        return optionProducts;
    }

    /**
     *
     * @param optionProducts OptionProduct
     */
    public void setOptionProducts(List<OptionProduct> optionProducts) {
        this.optionProducts = optionProducts;
    }

    @Override
    public String toString() {
        return "ProductAttributeOption{" +
                "productOptions=" + productOptions +
                ", optionProducts=" + optionProducts +
                '}';
    }
}
