package com.iota.eshopping.service.datahelper.datasource.offine.optionproduct;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.constant.meta.OptionProductM;
import com.iota.eshopping.model.OptionProduct;
import com.iota.eshopping.model.OptionValue;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

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
