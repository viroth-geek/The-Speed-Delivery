package com.iota.eshopping.service.datahelper.datasource.offine.optionproduct;

import android.database.Cursor;

import com.iota.eshopping.constant.meta.OptionProductM;
import com.iota.eshopping.model.OptionProduct;
import com.iota.eshopping.service.datahelper.datasource.offine.RowMapper;

/**
 * @author yeakleang.ay on 7/19/18.
 */

public class OptionProductRowMapper implements RowMapper<OptionProduct> {

    @Override
    public OptionProduct mappedRow(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        OptionProduct optionProduct = new OptionProduct();
        if (!cursor.isNull(cursor.getColumnIndex(OptionProductM.ID.trim()))) {
            optionProduct.setId(cursor.getLong(cursor.getColumnIndex(OptionProductM.ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionProductM.OPTION_ID.trim()))) {
            optionProduct.setOptionId(cursor.getLong(cursor.getColumnIndex(OptionProductM.OPTION_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionProductM.TITLE.trim()))) {
            optionProduct.setTitle(cursor.getString(cursor.getColumnIndex(OptionProductM.TITLE.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionProductM.PRODUCT_ID.trim()))) {
            optionProduct.setProductId(cursor.getLong(cursor.getColumnIndex(OptionProductM.PRODUCT_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionProductM.ORIGINAL_PRODUCT_ID.trim()))) {
            optionProduct.setOriginalProductId(cursor.getLong(cursor.getColumnIndex(OptionProductM.ORIGINAL_PRODUCT_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionProductM.PRODUCT_UID.trim()))) {
            optionProduct.setProductUid(cursor.getString(cursor.getColumnIndex(OptionProductM.PRODUCT_UID.trim())));
        }
        return optionProduct;
    }

}
