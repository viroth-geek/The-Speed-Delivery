package com.planb.thespeed.service.datahelper.datasource.offine.optionproduct;

import android.database.Cursor;

import com.planb.thespeed.constant.meta.OptionValueM;
import com.planb.thespeed.model.OptionValue;
import com.planb.thespeed.service.datahelper.datasource.offine.RowMapper;

/**
 * @author yeakleang.ay on 7/19/18.
 */

public class OptionValueRowMapper implements RowMapper<OptionValue> {

    @Override
    public OptionValue mappedRow(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        OptionValue optionValue = new OptionValue();
        if (!cursor.isNull(cursor.getColumnIndex(OptionValueM.ID.trim()))) {
            optionValue.setId(cursor.getLong(cursor.getColumnIndex(OptionValueM.ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionValueM.OPTION_TYPE_ID.trim()))) {
            optionValue.setOptionTypeId(cursor.getLong(cursor.getColumnIndex(OptionValueM.OPTION_TYPE_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionValueM.OPTION_VALUE_TITLE.trim()))) {
            optionValue.setTitle(cursor.getString(cursor.getColumnIndex(OptionValueM.OPTION_VALUE_TITLE.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionValueM.PRICE.trim()))) {
            optionValue.setPrice(cursor.getFloat(cursor.getColumnIndex(OptionValueM.PRICE.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(OptionValueM.PRODUCT_UID.trim()))) {
            optionValue.setProductUid(cursor.getString(cursor.getColumnIndex(OptionValueM.PRODUCT_UID.trim())));
        }
        return optionValue;
    }
}
