package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.ServerDateTime;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class FetchServerDateTime extends BaseService<List<ServerDateTime>> {


    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchServerDateTime(InvokeOnCompleteAsync<List<ServerDateTime>> onCompleteAsync) {
        super(onCompleteAsync);
        request();
    }

    /**
     * request
     */
    private void request() {
        ServiceGenerator.createService(StoreService.class)
                .getServerDateTime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
