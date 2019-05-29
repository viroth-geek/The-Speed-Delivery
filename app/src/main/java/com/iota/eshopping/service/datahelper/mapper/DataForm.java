package com.iota.eshopping.service.datahelper.mapper;

import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.Product;
import com.iota.eshopping.model.UserSecure;
import com.iota.eshopping.model.magento.store.storeList.Category;
import com.iota.eshopping.model.modelForView.ProductCategory;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.model.magento.catalog.Catalog;
import com.iota.eshopping.model.magento.catalog.ProductSku;
import com.iota.eshopping.model.magento.store.storeDetails.StoreDetail;
import com.iota.eshopping.model.magento.store.storeList.StoreView;

import java.util.List;
import java.util.Map;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */
public interface DataForm {

    /**
     * @param storeViewList
     * @return
     */
    List<Store> getStoreList(List<StoreView> storeViewList);

    /**
     * @param storeView
     * @return
     */
    Store getStore(StoreView storeView);

    /**
     * @param storeDetail
     * @return
     */
    Store getStoreByStoreDetail(StoreDetail storeDetail);

    /**
     * @param listMap
     * @return
     */
    List<ProductCategory> getProductCategories(Map<Catalog, List<ProductSku>> listMap);

    /**
     * @param listMap
     * @return
     */
    List<ProductCategory> getProductCategoriesByDetail(Map<Catalog, List<Product>> listMap);

    /**
     * @param userSecure
     * @return
     */
    UserSecure getUserSecure(UserSecure userSecure);

    /**
     * @param customerInfo
     * @return
     */
    Customer getCustomer(Customer customerInfo);

    /**
     * getProductCategory
     * @param categories List<ProductCategory>
     * @return List<Category>
     */
    List<ProductCategory> getProductCategory(List<Category> categories);
}
