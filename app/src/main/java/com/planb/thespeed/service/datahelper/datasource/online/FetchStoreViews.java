package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.store.storeList.StoreView;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 12/27/17.
 *
 * @author channarith.bong
 */

public class FetchStoreViews extends BaseService<List<StoreView>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchStoreViews(Long id, InvokeOnCompleteAsync<List<StoreView>> onCompleteAsync) {
        super(onCompleteAsync);
        request(id);
    }

    /**
     * Constructor
     * @param storeId Long
     * @param pageSize Long
     * @param onCompleteAsync InvokeOnCompleteAsync<List<StoreView>>
     */
    public FetchStoreViews(Long storeId, int pageSize, InvokeOnCompleteAsync<List<StoreView>> onCompleteAsync) {
        super(onCompleteAsync);
        request(storeId, pageSize);
    }

    /**
     *
     */
    private void request() {
        ServiceGenerator
                .createService(StoreService.class)
                .getStoreViews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     * @param id Long
     */
    private void request(Long id) {
        ServiceGenerator
                .createService(StoreService.class)
                .getStoreViewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    /**
     * request
     * @param storeId Long
     * @param pageSize Long
     */
    private void request(Long storeId, int pageSize) {
        ServiceGenerator
                .createService(StoreService.class)
                .getStoreViewByIdWithSizeProduct(storeId, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
