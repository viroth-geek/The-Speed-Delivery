package com.iota.eshopping.security;

import android.content.Context;

import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.datahelper.datasource.offine.cart.FetchCartDAO;
import com.iota.eshopping.util.LoggerHelper;

import java.util.List;

/**
 * @author channarith.bong
 */
public class ProductLocalService {

    private FetchCartDAO dao;

    /**
     * @param context Context
     */
    public ProductLocalService(Context context) {
        dao = new FetchCartDAO(DatabaseHelper.getInstance(context).getDatabase());
    }

    /**
     * @param product Product
     */
    public boolean insertItem(Product product) {
        return dao.insert(product);
    }

    /**
     * @return list of Product
     */
    public List<Product> getListItem() {
        return dao.getListProducts();
    }

    /**
     * delete all
     */
    public void deleteAll() {
        dao.deleteAll();
    }

    /**
     * delete where product uid
     * @param productUid String
     * @return boolean
     */
    public Boolean deleteWhereProductUid(String productUid) {
        return dao.deleteWhereProductUid(productUid);
    }
}
