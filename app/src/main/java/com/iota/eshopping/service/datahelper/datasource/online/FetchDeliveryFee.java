package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.StoreDeliveryFee;
import com.iota.eshopping.model.form.FormForGetDeliveryFee;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

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
