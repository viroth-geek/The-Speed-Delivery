package com.planb.thespeed.service.datahelper.datasource.offine.cart;

import android.database.Cursor;

import com.planb.thespeed.constant.meta.CartItemM;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.service.datahelper.datasource.offine.RowMapper;

/**
 * Created by channarith.bong on 1/18/18.
 *
 * @author channarith.bong
 */

public class ItemRowMapper implements RowMapper<Product> {

    @Override
    public Product mappedRow(Cursor cursor) {
        Product bean = new Product();

        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.PRODUCT_ID))) {
            bean.setId(cursor.getLong(cursor.getColumnIndex(CartItemM.PRODUCT_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.PRODUCT_UID))) {
            bean.setProductUid(cursor.getString(cursor.getColumnIndex(CartItemM.PRODUCT_UID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.PRODUCT_NAME))) {
            bean.setName(cursor.getString(cursor.getColumnIndex(CartItemM.PRODUCT_NAME)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.PRICE))) {
            bean.setPrice(cursor.getFloat(cursor.getColumnIndex(CartItemM.PRICE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.QTY))) {
            bean.setCount(cursor.getInt(cursor.getColumnIndex(CartItemM.QTY)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.SKU))) {
            bean.setSku(cursor.getString(cursor.getColumnIndex(CartItemM.SKU)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.PARENT_ID))) {
            bean.setParentId(cursor.getLong(cursor.getColumnIndex(CartItemM.PARENT_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.STORE_ID))) {
            bean.setStoreId(cursor.getLong(cursor.getColumnIndex(CartItemM.STORE_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CartItemM.PRODUCT_TYPE))) {
            bean.setProductType(cursor.getString(cursor.getColumnIndex(CartItemM.PRODUCT_TYPE)));
        }

        return bean;
    }
}
