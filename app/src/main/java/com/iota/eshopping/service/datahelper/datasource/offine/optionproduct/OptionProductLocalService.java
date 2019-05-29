package com.iota.eshopping.service.datahelper.datasource.offine.optionproduct;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.constant.entity.SQLStatement;
import com.iota.eshopping.constant.meta.OptionM;
import com.iota.eshopping.constant.meta.OptionProductM;
import com.iota.eshopping.model.OptionProduct;
import com.iota.eshopping.util.DBUtils;

import java.util.List;

/**
 * @author yeakleang.ay on 7/18/18.
 */

public class OptionProductLocalService {

    private SQLiteDatabase sqLiteDatabase;

    /**
     * constructor
     * @param sqLiteDatabase SQLiteDatabase
     */
    public OptionProductLocalService(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    /**
     * save
     * @param optionProduct OptionProduct
     * @return Long
     */
    public Long save(OptionProduct optionProduct) {
        try {
            ContentValues contentValues = getValuesFromOptionProduct(optionProduct);
            return sqLiteDatabase.insert(OptionProductM.TABLE_OPTION_PRODUCT, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     *
     * @return OptionProduct
     */
    public List<OptionProduct> getOptionProducts() {
        try {
            String sql = SQLStatement.SELECT_ALL + OptionProductM.TABLE_OPTION_PRODUCT;
            return DBUtils.rawQuery(sqLiteDatabase, new OptionProductRowMapper(), sql, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delete all product in cart
     * @return boolean
     */
    public boolean deleteAll() {
        try {
            return sqLiteDatabase.delete(OptionProductM.TABLE_OPTION_PRODUCT, null, null) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * delete where product uid
     * @param productUid String
     * @return boolean
     */
    public boolean deleteWhereProductUUID(String productUid) {
        try {
            return sqLiteDatabase.delete(OptionProductM.TABLE_OPTION_PRODUCT, OptionProductM.PRODUCT_UID + "=?", new String[] { productUid }) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param optionProduct OptionProduct
     * @return ContentValues
     */
    private ContentValues getValuesFromOptionProduct(OptionProduct optionProduct) {
        ContentValues contentValues = new ContentValues();
        if (optionProduct == null) {
            return contentValues;
        }
        contentValues.put(OptionProductM.PRODUCT_UID, optionProduct.getProductUid());
        contentValues.put(OptionProductM.OPTION_ID, optionProduct.getOptionId());
        contentValues.put(OptionProductM.TITLE, optionProduct.getTitle());
        contentValues.put(OptionProductM.PRODUCT_ID, optionProduct.getProductId());
        contentValues.put(OptionProductM.ORIGINAL_PRODUCT_ID, optionProduct.getOriginalProductId());
        return contentValues;
    }

}
