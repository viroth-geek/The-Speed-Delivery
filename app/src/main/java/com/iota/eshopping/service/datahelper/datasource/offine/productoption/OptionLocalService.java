package com.iota.eshopping.service.datahelper.datasource.offine.productoption;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.constant.entity.SQLStatement;
import com.iota.eshopping.constant.meta.OptionM;
import com.iota.eshopping.constant.meta.OptionValueM;
import com.iota.eshopping.constant.meta.ProductOptionM;
import com.iota.eshopping.model.Option;
import com.iota.eshopping.util.DBUtils;

import java.util.List;

/**
 * @author yeakleang.ay on 7/19/18.
 */

public class OptionLocalService {

    private SQLiteDatabase sqLiteDatabase;

    /**
     * constructor
     * @param sqLiteDatabase SQLiteDatabase
     */
    public OptionLocalService(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    /**
     * save option
     * @param option Option
     * @param productOptionId Long
     * @return
     */
    public boolean save(Option option, Long productOptionId) {
        try {
            ContentValues contentValues = getValuesFromOption(option, productOptionId);
            return sqLiteDatabase.insert(OptionM.TABLE_OPTION, null, contentValues) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get options
     * @param productOptionId
     * @return
     */
    public List<Option> getOptions(Long productOptionId) {
        try {
            String sql = SQLStatement.SELECT_ALL + OptionM.TABLE_OPTION + " o inner join" + ProductOptionM.TABLE_PRODUCT_OPTION + "po on o." + OptionM.ID.trim() + " = po." + ProductOptionM.ID.trim() + " where po." + ProductOptionM.ID.trim() + "=" + productOptionId;
            return DBUtils.rawQuery(sqLiteDatabase, new OptionRowMapper(), sql, null);
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
            return sqLiteDatabase.delete(OptionM.TABLE_OPTION, null, null) > 0;
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
            return sqLiteDatabase.delete(OptionM.TABLE_OPTION, OptionM.PRODUCT_UID + "=?", new String[] { productUid }) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get content values from option
     * @param option Option
     * @param productOptionId Long
     * @return ContentValues
     */
    private ContentValues getValuesFromOption(Option option, Long productOptionId) {
        if (option == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(OptionValueM.PRODUCT_UID, option.getProductUid());
        contentValues.put(OptionM.LABEL, option.getLabel());
        contentValues.put(OptionM.VALUE, option.getValueIndex());
        contentValues.put(OptionM.FK_PRODUCT_OPTION, productOptionId);
        return contentValues;
    }
}
