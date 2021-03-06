package com.planb.thespeed.service.datahelper.mapper;

import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.Product;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.model.magento.store.storeList.Category;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.model.magento.catalog.Catalog;
import com.planb.thespeed.model.magento.catalog.ProductSku;
import com.planb.thespeed.model.magento.store.storeDetails.StoreDetail;
import com.planb.thespeed.model.magento.store.storeList.StoreView;
import com.planb.thespeed.service.datahelper.mapper.magentoRestructurer.MagentoStoreViewsRestructurer;

import java.util.List;
import java.util.Map;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */
public class DataMatcher implements DataForm {
    public static DataMatcher instance;
    private MagentoStoreViewsRestructurer restructurer;

    /**
     *
     */
    private DataMatcher() {
        restructurer = new MagentoStoreViewsRestructurer();
    }

    /**
     * @return
     */
    public static DataMatcher getInstance() {
        if (instance == null) {
            instance = new DataMatcher();
        }
        return instance;
    }


    @Override
    public List<Store> getStoreList(List<StoreView> storeViewList) {
        return restructurer.getStoreList(storeViewList);
    }

    @Override
    public Store getStore(StoreView storeView) {
        return restructurer.getStore(storeView);
    }

    @Override
    public Store getStoreByStoreDetail(StoreDetail storeDetail) {
        return restructurer.getStoreByStoreDetail(storeDetail);
    }

    @Override
    public List<ProductCategory> getProductCategories(Map<Catalog, List<ProductSku>> listMap) {
        return restructurer.getProductCategories(listMap);
    }

    @Override
    public List<ProductCategory> getProductCategoriesByDetail(Map<Catalog, List<Product>> listMap) {
        return restructurer.getProductCategoriesByDetail(listMap);
    }

    @Override
    public UserSecure getUserSecure(UserSecure userSecure) {
        return restructurer.getUserSecure(userSecure);
    }

    @Override
    public Customer getCustomer(Customer customerInfo) {
        return restructurer.getCustomer(customerInfo);
    }

    @Override
    public List<ProductCategory> getProductCategory(List<Category> categories) {
        return restructurer.margeProductCategoryV2(categories);
    }

}
