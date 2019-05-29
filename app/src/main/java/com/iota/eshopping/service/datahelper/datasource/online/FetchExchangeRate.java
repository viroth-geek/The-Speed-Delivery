package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.ExchangeRate;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
