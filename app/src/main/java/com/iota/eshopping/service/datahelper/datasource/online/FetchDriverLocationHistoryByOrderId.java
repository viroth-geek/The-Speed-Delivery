package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.DriverLocation;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 9/10/18.
 */

public class FetchDriverLocationHistoryByOrderId extends BaseService<List<DriverLocation>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchDriverLocationHistoryByOrderId(Long orderId, InvokeOnCompleteAsync<List<DriverLocation>> onCompleteAsync) {
        super(onCompleteAsync);
        request(orderId);
    }

    /**
     * request
     * @param orderId Long
     */
    private void request(Long orderId) {
        ServiceGenerator.createService(OrderService.class)
            .getLocationHistoryByOrderId(orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(this);
    }
}
