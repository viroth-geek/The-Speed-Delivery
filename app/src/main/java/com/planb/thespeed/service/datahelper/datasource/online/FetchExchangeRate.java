package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.ExchangeRate;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 6/15/18.
 */

public class FetchExchangeRate extends BaseService<List<ExchangeRate>> {

    /**
     * Constructor
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchExchangeRate(InvokeOnCompleteAsync<List<ExchangeRate>> onCompleteAsync) {
        super(onCompleteAsync);
        request();
    }

    /**
     *
     */
    private void request() {
        ServiceGenerator.createService(StoreService.class)
                .getExchangeRate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);

    }
}
