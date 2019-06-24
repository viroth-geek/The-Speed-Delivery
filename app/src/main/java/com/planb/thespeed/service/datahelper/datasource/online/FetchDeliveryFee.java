package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.StoreDeliveryFee;
import com.planb.thespeed.model.form.FormForGetDeliveryFee;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 9/5/18.
 */

public class FetchDeliveryFee extends BaseService<List<StoreDeliveryFee>> {

    /**
     * Constructor
     * @param formForGetDeliveryFee FormForGetDeliveryFee
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchDeliveryFee(FormForGetDeliveryFee formForGetDeliveryFee, InvokeOnCompleteAsync<List<StoreDeliveryFee>> onCompleteAsync) {
        super(onCompleteAsync);
        request(formForGetDeliveryFee);
    }

    /**
     * request
     * @param formForGetDeliveryFee FormForGetDeliveryFee
     */
    private void request(FormForGetDeliveryFee formForGetDeliveryFee) {
        ServiceGenerator.createService(StoreService.class)
                .getDeliveryFee(formForGetDeliveryFee)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

}
