package com.planb.thespeed.service.datahelper.datasource.offine.optionproduct;

import android.content.Context;

import com.planb.thespeed.model.OptionProduct;
import com.planb.thespeed.model.OptionValue;
import com.planb.thespeed.server.DatabaseHelper;

import java.util.List;

/**
 * @author yeakleang.ay on 7/19/18.
 */

public class OptionProductDAO {

    private OptionProductLocalService optionProductLocalService;

    private OptionValueLocalService optionValueLocalService;

    /**
     * constructor
     * @param context context
     */
    public OptionProductDAO(Context context) {
        this.optionProductLocalService = new OptionProductLocalService(DatabaseHelper.getInstance(context).getDatabase());
        this.optionValueLocalService = new OptionValueLocalService(DatabaseHelper.getInstance(context).getDatabase());
    }

    /**
     * save option
     * @param optionProduct OptionProduct
     */
    public void save(OptionProduct optionProduct) {
        Long optionId = optionProductLocalService.save(optionProduct);
        for (OptionValue optionValue : optionProduct.getOptionValues()) {
            optionValue.setProductUid(optionProduct.getProductUid());
            optionValueLocalService.save(optionValue, optionId);
        }
    }

    /**
     * get option product
     * @return list option product
     */
    public List<OptionProduct> getOptionProducts() {

        List<OptionProduct> optionProducts = optionProductLocalService.getOptionProducts();
        if (optionProducts != null && !optionProducts.isEmpty()) {
            for (OptionProduct optionProduct : optionProducts) {
                List<OptionValue> optionValues = optionValueLocalService.getOptionValues(optionProduct.getId());
                optionProduct.setOptionValues(optionValues);
            }
        }
        return optionProducts;
    }

    /**
     * delete all product in cart
     * @return boolean
     */
    public boolean deleteAll() {
        return optionValueLocalService.deleteAll() && optionProductLocalService.deleteAll();
    }

    /**
     * delete where product uid
     * @param productUid String
     * @return boolean
     */
    public boolean deleteWhereProductUid(String productUid) {
        return optionValueLocalService.deleteWhereProductUUID(productUid) && optionProductLocalService.deleteWhereProductUUID(productUid);
    }
}
