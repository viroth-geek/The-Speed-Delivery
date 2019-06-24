package com.planb.thespeed.service.datahelper.datasource.offine.cart;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.planb.thespeed.constant.entity.SQLStatement;
import com.planb.thespeed.constant.meta.CartItemM;
import com.planb.thespeed.model.magento.addToCart.CartProductItem;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.util.DBUtils;
import com.planb.thespeed.util.LoggerHelper;

import java.util.List;

/**
 * Created by channarith.bong on 1/18/18.
 *
 * @author channarith.bong
 */

public class FetchCartDAO {

    private SQLiteDatabase database;

    /**
     * @param database SQLiteDatabase
     * @see SQLiteDatabase
     */
    public FetchCartDAO(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * insert
     * @param product Product
     * @return boolean
     */
    public boolean insert(Product product) {
        try {
            ContentValues values = getValueFromCartItem(product);
            return database.insert(CartItemM.TABLE_CART_ITEM, null, values) > 0;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * Get list of Cart Item
     *
     * @return List<Product>
     * @see CartProductItem
     */
    public List<Product> getListProducts() {
        try {
            String sql = SQLStatement.SELECT_ALL + CartItemM.TABLE_CART_ITEM;
            List<Product> products = DBUtils.rawQuery(database, new ItemRowMapper(), sql, null);
            return products;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * delete all product in cart
     * @return boolean
     */
    public boolean deleteAll() {
        try {
            return database.delete(CartItemM.TABLE_CART_ITEM, null, null) > 0;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("SQL : " + e.getMessage());
        }
        return false;
    }

    /**
     * delete where product uid
     * @param productUid String
     * @return boolean
     */
    public boolean deleteWhereProductUid(String productUid) {
        try {
            return database.delete(CartItemM.TABLE_CART_ITEM, CartItemM.PRODUCT_UID + "=?", new String[] { productUid }) > 0;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("SQL : " + e.getMessage());
        }
        return false;
    }

    /**
     * Convert CartProductItem into contentValue
     *
     * @param product Product
     * @return ContentValues
     */
    private ContentValues getValueFromCartItem(Product product) {
        ContentValues values = new ContentValues();

        if (product == null) {
            return values;
        }

        if (product.getId() != null) {
            values.put(CartItemM.PRODUCT_ID, product.getId());
        }
        if (product.getProductUid() != null) {
            values.put(CartItemM.PRODUCT_UID, product.getProductUid());
        }
        if (product.getName() != null) {
            values.put(CartItemM.PRODUCT_NAME, product.getName());
        }
        if (product.getSku() != null) {
            values.put(CartItemM.SKU, product.getSku());
        }
        if (product.getPrice() != null) {
            values.put(CartItemM.PRICE, product.getPrice());
        }
        if (product.getCount() != null) {
            values.put(CartItemM.QTY, product.getCount());
        }
        if (product.getParentId() != null) {
            values.put(CartItemM.PARENT_ID, product.getParentId());
        }
        if (product.getStoreId() != null) {
            values.put(CartItemM.STORE_ID, product.getStoreId());
        }
        if (product.getProductType() != null) {
            values.put(CartItemM.PRODUCT_TYPE, product.getProductType());
        }

        return values;
    }
}
