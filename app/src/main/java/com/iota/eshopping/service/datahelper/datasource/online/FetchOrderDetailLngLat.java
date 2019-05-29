package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 5/29/18.
 */

public class FetchOrderDetailLngLat extends BaseService<List<String>> {


    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchOrderDetailLngLat(Long id, InvokeOnCompleteAsync<List<String>> onCompleteAsync) {
        super(onCompleteAsync);
        request(id);
    }

    /**
     *
     * @param id
     */
    private void request(Long id) {
        ServiceGenerator
                .createService(OrderService.class)
                .getOrderDetailLatLng(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
