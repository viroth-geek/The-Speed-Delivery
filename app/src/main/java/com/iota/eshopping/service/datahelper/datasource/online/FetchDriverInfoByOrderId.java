package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.DriverInfo;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.order.OrderService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 12/11/18.
 */

public class FetchDriverInfoByOrderId extends BaseService<List<DriverInfo>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchDriverInfoByOrderId(Long orderId, InvokeOnCompleteAsync<List<DriverInfo>> onCompleteAsync) {
        super(onCompleteAsync);
        request(orderId);
    }

    /**
     *
     * @param orderId
     */
    private void request(Long orderId) {
        ServiceGenerator.createService(OrderService.class)
                .getDriverInfoByOrderId(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
