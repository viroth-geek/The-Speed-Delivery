package com.iota.eshopping.service;

import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.model.magento.store.storeList.ListStore;
import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.service.datahelper.datasource.online.FetchListStores;
import com.iota.eshopping.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by channarith.bong on 1/4/18.
 *
 * @author channarith.bong
 */
public class StoreService {

    private static StoreService instance;
    private List<Store> estoreList;
    private String baseImageUrl = ApplicationConfiguration.STORE_IMAGE_URL;

    /**
     *
     */
    private StoreService() {
        estoreList = new ArrayList<>();
    }

    /**
     * @return
     */
    public static StoreService getInstance() {
        if (instance == null) {
            instance = new StoreService();
        }
        return instance;
    }

    /**
     * @param restriction
     * @param onCompleteAsync
     * @return
     */
    public void loadStoreList(StoreRestriction restriction, InvokeOnCompleteAsync onCompleteAsync) {
        fetchStore(restriction, onCompleteAsync);
    }

    /**
     *
     */
    private void fetchStore(StoreRestriction restriction, final InvokeOnCompleteAsync onCompleteAsync) {

        new FetchListStores(restriction, new FetchListStores.InvokeOnCompleteAsync() {

            @Override
            public void onComplete(List<ListStore> listStores) {
                if (listStores != null && !listStores.isEmpty()) {
                    if (onCompleteAsync != null) {
                        onCompleteAsync.onComplete(listStores.get(0));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog(" << Store: " + e.getMessage());
            }
        });
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(ListStore listStore);

        void onError(Throwable e);
    }

}
