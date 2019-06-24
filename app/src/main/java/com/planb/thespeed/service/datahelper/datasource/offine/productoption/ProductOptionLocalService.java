package com.planb.thespeed.service.datahelper.datasource.offine.productoption;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.planb.thespeed.constant.entity.SQLStatement;
import com.planb.thespeed.constant.meta.OptionM;
import com.planb.thespeed.constant.meta.ProductOptionM;
import com.planb.thespeed.model.ProductOption;
import com.planb.thespeed.util.DBUtils;

import java.util.List;

/**
 * @author yeakleang.ay on 7/18/18.
 */
public class ProductOptionLocalService {

    private SQLiteDatabase sqLiteDatabase;

    /**
     * constructor
     * @param sqLiteDatabase SQLiteDatabase
     */
    public ProductOptionLocalService(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    /**
     * save product option
     * @return boolean
     */
    public Long save(ProductOption productOption) {
        try {
            ContentValues contentValues = getValuesFromProductOption(productOption);
            return sqLiteDatabase.insert(ProductOptionM.TABLE_PRODUCT_OPTION, null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    };

    /**
     * get all product option
     * @return ProductOption
     */
    public List<ProductOption> getProductOptions() {
        try {
            String sql = SQLStatement.SELECT_ALL + ProductOptionM.TABLE_PRODUCT_OPTION + "po inner join" + OptionM.TABLE_OPTION + "o on po.id = o.fk_product_option";
            return DBUtils.rawQuery(sqLiteDatabase, new ProductOptionRowMapper(), sql, null);
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
            return sqLiteDatabase.delete(ProductOptionM.TABLE_PRODUCT_OPTION, null, null) > 0;
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
            return sqLiteDatabase.delete(ProductOptionM.TABLE_PRODUCT_OPTION, ProductOptionM.PRODUCT_UID + "=?", new String[] { productUid }) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * cast model to content values
     * @param productOption ProductOption
     * @return ContentValues
     */
    private ContentValues getValuesFromProductOption(ProductOption productOption) {
        ContentValues contentValues = new ContentValues();
        if (productOption == null) {
            return contentValues;
        }
        contentValues.put(ProductOptionM.PRODUCT_UID, productOption.getProductUid());
        contentValues.put(ProductOptionM.ATTRIBUTE_ID, productOption.getAttributeId());
        contentValues.put(ProductOptionM.LABEL, productOption.getLabel());
        contentValues.put(ProductOptionM.PRODUCT_ID, productOption.getProductId());
        contentValues.put(ProductOptionM.ORIGINAL_PRODUCT_ID, productOption.getOriginalProductId());
        return contentValues;
    }
}
