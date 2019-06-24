package com.planb.thespeed.service.datahelper.mapper.magentoRestructurer;

import com.google.android.gms.maps.model.LatLng;
import com.planb.thespeed.constant.ApplicationConfiguration;
import com.planb.thespeed.constant.json.StoreDetailAttribute;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.model.magento.catalog.Catalog;
import com.planb.thespeed.model.magento.catalog.ProductSku;
import com.planb.thespeed.model.magento.store.storeDetails.StoreDetail;
import com.planb.thespeed.model.magento.store.storeList.Category;
import com.planb.thespeed.model.magento.store.storeList.StoreView;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.model.modelForView.Tag;
import com.planb.thespeed.service.datahelper.datasource.sample.SampleData;
import com.planb.thespeed.service.datahelper.mapper.DataForm;
import com.planb.thespeed.util.DateUtil;
import com.planb.thespeed.util.LocationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */

public class MagentoStoreViewsRestructurer implements DataForm {

    @Override
    public List<Store> getStoreList(List<StoreView> storeViewList) {
        return matchStoreDataByList(storeViewList);
    }

    @Override
    public Store getStore(StoreView storeView) {
        return matchStore(storeView);
    }

    @Override
    public Store getStoreByStoreDetail(StoreDetail storeDetail) {
        return margeStoreByStoreDetail(storeDetail);
    }

    @Override
    public List<ProductCategory> getProductCategories(Map<Catalog, List<ProductSku>> listMap) {
        return null;
    }

    @Override
    public List<ProductCategory> getProductCategoriesByDetail(Map<Catalog, List<com.planb.thespeed.model.Product>> listMap) {
        return null;
    }

    @Override
    public UserSecure getUserSecure(UserSecure userSecure) {
        return null;
    }

    @Override
    public Customer getCustomer(Customer customerInfo) {
        return null;
    }

    @Override
    public List<ProductCategory> getProductCategory(List<Category> categories) {
        return margeProductCategoryV2(categories);
    }

    /**
     * @return
     */
    private List<Store> getSampleStores() {
        return SampleData.getInstance().getStores();
    }

    /**
     * List of store in Home Screen
     *
     * @param storeViews
     * @return
     */
    private List<Store> matchStoreDataByList(List<StoreView> storeViews) {

        // User location
        LatLng latLngOfSource = new LatLng(11, 104);

        // Store Location
        LatLng latLngOfDestination = new LatLng(11, 104);

        List<Store> newStores = new ArrayList<>();
        if (storeViews == null || storeViews.size() == 0) {
            return newStores;
        }

        for (int i = 0; i < storeViews.size(); i++) {
            if (Store.isSysStore(storeViews.get(i).getId())) {
                continue;
            }
            Store store = new Store();
            StoreView storeView = storeViews.get(i);
            store.setId(storeView.getId());
            store.setName(storeView.getName());
            store.setNameKh(storeView.getNameKh());
            store.setImageUrl(storeView.getImageUrl());
            store.setLogoUrl(storeView.getLogoUrl());
            store.setCategories(margeProductCategoryV2(storeView.getCategories()));
            store.setPercentage((float) storeView.getPercentage());
            store.setRate((float) storeView.getRate());
            store.setLatitude(storeView.getLatitude());
            store.setLongitude(storeView.getLongitude());
            store.setDescription(storeView.getDescription());
            store.setStatus(storeView.getStatus());
            store.setCloseHour(storeView.getCloseHour());
            store.setOpenHour(storeView.getOpenHour());
            store.setSponsor(storeView.getSponsor());
            store.setRecommend(storeView.getRecommend());
            store.setNewArrival(storeView.getNewArrival());
            store.setShippingMethod(storeView.getShippingMethod());
            store.setDistant(storeView.getDistant());
            store.setFee(storeView.getFeeBaseOnDistant());
            store.setStatusOpenToday(storeView.getStatusOpenToday());
            store.setStatusOpenTomorrow(storeView.getStatusOpenTomorrow());
            store.setOpenToday(storeView.isOpen());

            String tags = "";
            if (storeView.getTags() != null) {
                for (Tag tagItem : storeView.getTags()) {
                    tags += tagItem.getName() + " ";
                }
            }
            store.setTag(tags);

            String duration = LocationUtils.getDurationBetweenTwoLatLng(latLngOfSource, latLngOfDestination) + " ";
            store.setTimeDelivery(duration);
            newStores.add(store);

        }

        return newStores;
    }

    /**
     * @param storeView
     * @return
     */
    private Store matchStore(StoreView storeView) {

        Store store = new Store();
        String tags = "";
        if (storeView.getTags() != null) {
            for (Tag tagItem : storeView.getTags()) {
                tags += tagItem.getName() + " ";
            }
        }
        store.setName(storeView.getName());
        store.setNameKh(storeView.getNameKh());
        store.setId(storeView.getId());
        store.setTag(tags);
        store.setLatitude(storeView.getLatitude());
        store.setLongitude(storeView.getLongitude());
        store.setCategories(margeProductCategoryV2(storeView.getCategories()));
        return store;
    }

    /**
     * @param categories
     * @return
     */
    public List<ProductCategory> margeProductCategoryV2(List<Category> categories) {

        List<ProductCategory> productCategories = new ArrayList<>();
        for (Category category : categories) {
            List<Product> productList = new ArrayList<>();

            ProductCategory productCategory = new ProductCategory();
            productCategory.setName(category.getName());
            productCategory.setId(category.getId());
            productCategory.setCount(category.getCount());

            for (com.planb.thespeed.model.Product productDetail : category.getProducts()) {
                Product product = new Product();
                product.setId(productDetail.getId());
                product.setName(productDetail.getSku());
                product.setSku(productDetail.getSku());
                product.setPrice(Float.parseFloat(productDetail.getPrice().toString()));
                product.setDetail(productDetail.getDetail());
                product.setImageUrl(productDetail.getImageUrl() == null ? "" : productDetail.getImageUrl());
                product.setProductType(productDetail.getProductType());

                productList.add(product);
            }

            productCategory.setProducts(productList);
            productCategories.add(productCategory);
        }
        return productCategories;
    }

    /**
     * @param storeDetail
     * @return
     */
    private Store margeStoreByStoreDetail(StoreDetail storeDetail) {
        Store store = new Store();
        Store estoreFake = getSampleStores().get(0);
        String imageBaseURL = ApplicationConfiguration.STORE_IMAGE_URL;
        if (storeDetail != null) {
            store.setName(storeDetail.getName());
            store.setId(storeDetail.getId());
            store.setImageUrl(imageBaseURL + storeDetail.getValueOfString(StoreDetailAttribute.IMAGE));

            // Fake data
            store.setTag(estoreFake.getTag());
            store.setPercentage(estoreFake.getPercentage());
            store.setRate(estoreFake.getRate());
            store.setStoreTypes(estoreFake.getStoreTypes());
            store.setTimeDelivery(estoreFake.getTimeDelivery());
        }
        return store;
    }

    /**
     * @param userSecure
     * @return
     */
    private UserSecure margeCustomerModel(UserSecure userSecure) {
        UserSecure createCustomer = new UserSecure();
        Customer mCustomer = new Customer();
        if (userSecure != null) {
            mCustomer.setEmail(userSecure.getCustomer().getEmail());
            mCustomer.setFirstname(userSecure.getCustomer().getFirstname());
            mCustomer.setLastname(userSecure.getCustomer().getLastname());

            createCustomer.setPassword(userSecure.getPassword());
            createCustomer.setCustomer(mCustomer);
        }
        return createCustomer;
    }

    /**
     * @param customerInfo
     * @return
     */
    private Customer margeCustomer(Customer customerInfo) {
        Customer customer = new Customer();
        customer.setEntityId(customerInfo.getEntityId());
        customer.setLastname(customerInfo.getLastname());
        customer.setFirstname(customerInfo.getFirstname());
        customer.setCreatedAt(DateUtil.getCurrent());
        customer.setUpdateAt(DateUtil.getCurrent());
        customer.setEmail(customerInfo.getEmail());
        return customer;
    }

}
