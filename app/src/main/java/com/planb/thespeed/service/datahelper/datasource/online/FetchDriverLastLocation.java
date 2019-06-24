package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.DriverLocation;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 9/10/18.
 */

public class FetchDriverLastLocation extends BaseService<List<DriverLocation>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchDriverLastLocation(Long orderId, InvokeOnCompleteAsync<List<DriverLocation>> onCompleteAsync) {
        super(onCompleteAsync);
        request(orderId);
    }

    /**
     * request
     * @param orderId Long
     */
    private void request(Long orderId) {
        ServiceGenerator.createService(OrderService.class)
                .getLastDriverLocationByOrderId(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
