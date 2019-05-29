package com.iota.eshopping.service.datahelper.datasource.offine.productoption;

import android.database.Cursor;

import com.iota.eshopping.constant.meta.OptionM;
import com.iota.eshopping.model.Option;
import com.iota.eshopping.service.datahelper.datasource.offine.RowMapper;

/**
 * @author yeakleang.ay on 7/19/18.
 */

public class OptionRowMapper implements RowMapper<Option> {

    @Override
    public Option mappedRow(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        Option option = new Option();
        if (!cursor.isNull(cursor.getColumnIndex(OptionM.ID.trim()))) {
            option.setId(cursor.getLong(cursor.getColumnIndex(OptionM.ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionM.LABEL.trim()))) {
            option.setLabel(cursor.getString(cursor.getColumnIndex(OptionM.LABEL.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionM.VALUE.trim()))) {
            option.setValueIndex(cursor.getLong(cursor.getColumnIndex(OptionM.VALUE.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionM.PRODUCT_UID.trim()))) {
            option.setProductUid(cursor.getString(cursor.getColumnIndex(OptionM.PRODUCT_UID.trim())));
        }
        return option;
    }
}
