package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.ServerDateTime;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

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
