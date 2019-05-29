package com.iota.eshopping.service;

import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.model.Product;
import com.iota.eshopping.model.magento.catalog.Catalog;
import com.iota.eshopping.model.magento.catalog.ProductSku;
import com.iota.eshopping.model.magento.store.storeList.StoreView;
import com.iota.eshopping.model.modelForView.ProductCategory;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.service.datahelper.datasource.online.FetchProduct;
import com.iota.eshopping.service.datahelper.datasource.online.FetchProductDetail;
import com.iota.eshopping.service.datahelper.datasource.online.FetchStoreViews;
import com.iota.eshopping.service.datahelper.mapper.DataMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by channarith.bong on 1/4/18.
 *
 * @author channarith.bong
 */
public class CategoryService {

    private static CategoryService instance;
    private InvokeOnCompleteAsync onCompleteAsync;
    private List<ProductCategory> productCategoryList;
    private Map<Catalog, List<Product>> mapCatalogListDetail;
    private List<Product> productDetails;

    /**
     *
     */
    private CategoryService() {
        productCategoryList = new ArrayList<>();
        productDetails = new ArrayList<>();
        mapCatalogListDetail = new HashMap<>();
    }

    /**
     * @return
     */
    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    /**
     * @param id
     * @param onCompleteAsync
     */
    public void loadCategoryList(Long id, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        mapCatalogListDetail.clear();
        productDetails.clear();
        fetchCategory(id);
    }

    /**
     * @param id
     */
    private void fetchCategory(final Long id) {
        new FetchStoreViews(id, 0, new com.iota.eshopping.service.base.InvokeOnCompleteAsync<List<StoreView>>() {
            @Override
            public void onComplete(List<StoreView> storeViews) {
                if (storeViews != null) {
                    if (onCompleteAsync != null) {
                        Store store = DataMatcher.getInstance().getStore(storeViews.get(0));
                        onCompleteAsync.onComplete(store.getCategories());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onError(e);
                }
            }
        });
    }

    /**
     * @param catalog
     */
    private void fetchProductSku(final Catalog catalog) {
        new FetchProduct(catalog.getId(), new FetchProduct.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<ProductSku> productSkus) {
                if (productSkus != null) {
                    mapCatalogListDetail.clear();
                    productDetails.clear();
                    for (ProductSku productSku : productSkus) {
                        fetchProductDetail(catalog, productSku.getSku());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onError(e);
                }
            }
        });

    }

    /**
     * @param catalog
     * @param sku
     */
    private void fetchProductDetail(final Catalog catalog, final String sku) {
        new FetchProductDetail(sku, new FetchProductDetail.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Product productDetail) {
                if (productDetail != null) {
                    productDetails.add(productDetail);
                    mapCatalogListDetail.put(catalog, productDetails);
                    productCategoryList = DataMatcher.getInstance().getProductCategoriesByDetail(mapCatalogListDetail);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (onCompleteAsync != null) {
                    onCompleteAsync.onError(e);
                }
            }
        });
    }

    /**
     * Connection Handler
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<ProductCategory> productCategoryList);

        void onError(Throwable e);
    }
}
