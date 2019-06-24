package com.planb.thespeed.service.datahelper.datasource.offine.productoption;

import android.database.Cursor;

import com.planb.thespeed.constant.meta.ProductOptionM;
import com.planb.thespeed.model.ProductOption;
import com.planb.thespeed.service.datahelper.datasource.offine.RowMapper;

/**
 * @author yeakleang.ay on 7/18/18.
 */

public class ProductOptionRowMapper implements RowMapper<ProductOption> {

    @Override
    public ProductOption mappedRow(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        ProductOption productOption = new ProductOption();
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.ID.trim()))) {
            productOption.setId(cursor.getLong(cursor.getColumnIndex(ProductOptionM.ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.ATTRIBUTE_ID.trim()))) {
            productOption.setAttributeId(cursor.getString(cursor.getColumnIndex(ProductOptionM.ATTRIBUTE_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.LABEL.trim()))) {
            productOption.setLabel(cursor.getString(cursor.getColumnIndex(ProductOptionM.LABEL.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.PRODUCT_ID.trim()))) {
            productOption.setProductId(cursor.getLong(cursor.getColumnIndex(ProductOptionM.PRODUCT_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.PRODUCT_UID.trim()))) {
            productOption.setProductUid(cursor.getString(cursor.getColumnIndex(ProductOptionM.PRODUCT_UID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.ORIGINAL_PRODUCT_ID.trim()))) {
            productOption.setOriginalProductId(cursor.getLong(cursor.getColumnIndex(ProductOptionM.ORIGINAL_PRODUCT_ID.trim())));
        }
        if (!cursor.isNull(cursor.getColumnIndex(ProductOptionM.ORIGINAL_PRODUCT_ID.trim()))) {
            productOption.setOriginalProductId(cursor.getLong(cursor.getColumnIndex(ProductOptionM.ORIGINAL_PRODUCT_ID.trim())));
        }
        return productOption;
    }

}
