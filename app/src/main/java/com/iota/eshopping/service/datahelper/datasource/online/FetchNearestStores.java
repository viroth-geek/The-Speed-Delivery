package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 8/14/18.
 */

public class FetchNearestStores extends BaseService<List<Long>> {

    /**
     * constructor
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchNearestStores(StoreRestriction storeRestriction, InvokeOnCompleteAsync<List<Long>> onCompleteAsync) {
        super(onCompleteAsync);
        request(storeRestriction);
    }

    /**
     * request method
     * @param storeRestriction StoreRestriction
     */
    private void request(StoreRestriction storeRestriction) {
        ServiceGenerator
                .createService(StoreService.class)
                .getNearestStores(storeRestriction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

}
