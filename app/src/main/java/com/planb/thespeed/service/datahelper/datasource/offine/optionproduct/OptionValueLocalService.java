package com.planb.thespeed.service.datahelper.datasource.offine.optionproduct;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.planb.thespeed.constant.entity.SQLStatement;
import com.planb.thespeed.constant.meta.OptionProductM;
import com.planb.thespeed.constant.meta.OptionValueM;
import com.planb.thespeed.model.OptionValue;
import com.planb.thespeed.util.DBUtils;
import com.planb.thespeed.util.LoggerHelper;

import java.util.List;

/**
 * @author yeakleang.ay on 7/18/18.
 */

public class OptionValueLocalService {

    private SQLiteDatabase sqLiteDatabase;

    /**
     * constructor
     * @param sqLiteDatabase SQLiteDatabase
     */
    public OptionValueLocalService(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    /**
     * save
     * @param optionValue OptionValue
     * @return Long
     */
    public Long save(OptionValue optionValue, Long optionId) {
        try {
            ContentValues contentValues = getValuesFromOptionValue(optionValue, optionId);
            return sqLiteDatabase.insert(OptionValueM.TABLE_OPTION_VALUE, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * get option values
     * @param optionProductId Long
     * @return OptionValue
     */
    public List<OptionValue> getOptionValues(Long optionProductId) {
        try {
            String sql = SQLStatement.SELECT_ALL + OptionValueM.TABLE_OPTION_VALUE + " ov inner join" + OptionProductM.TABLE_OPTION_PRODUCT + " op on ov." + OptionValueM.FK_OPTION_PRODUCT.trim() + " = op." + OptionProductM.ID.trim() + " where op.id=" + optionProductId;
            LoggerHelper.showDebugLog("SQL: " + sql);
            return DBUtils.rawQuery(sqLiteDatabase, new OptionValueRowMapper(), sql, null);
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
            return sqLiteDatabase.delete(OptionValueM.TABLE_OPTION_VALUE, null, null) > 0;
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
            return sqLiteDatabase.delete(OptionValueM.TABLE_OPTION_VALUE, OptionValueM.PRODUCT_UID + "=?", new String[] { productUid }) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get values from option value
     * @param optionValue OptionValue
     * @return ContentValues
     */
    private ContentValues getValuesFromOptionValue(OptionValue optionValue, Long optionId) {
        ContentValues contentValues = new ContentValues();
        if (optionValue == null) {
            return contentValues;
        }
        contentValues.put(OptionValueM.PRODUCT_UID, optionValue.getProductUid());
        contentValues.put(OptionValueM.OPTION_TYPE_ID, optionValue.getOptionTypeId());
        contentValues.put(OptionValueM.OPTION_VALUE_TITLE, optionValue.getTitle());
        contentValues.put(OptionValueM.PRICE, optionValue.getPrice());
        contentValues.put(OptionValueM.FK_OPTION_PRODUCT, optionId);
        return contentValues;
    }

}
