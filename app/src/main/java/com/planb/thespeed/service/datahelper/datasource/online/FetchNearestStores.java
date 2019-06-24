package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.store.storeList.StoreRestriction;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

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
