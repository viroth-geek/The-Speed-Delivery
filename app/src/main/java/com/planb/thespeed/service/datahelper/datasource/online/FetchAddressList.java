package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.Customer;
import com.planb.thespeed.security.KeyManagement;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.user.CustomerService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 6/8/18.
 */

public class FetchAddressList extends BaseService<Customer> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchAddressList(Long userId, InvokeOnCompleteAsync<Customer> onCompleteAsync) {
        super(onCompleteAsync);
        request(userId);
    }

    /**
     *
     * @param userId Long
     */
    private void request(Long userId) {
        ServiceGenerator.createService(CustomerService.class, KeyManagement.getInstance().getTokenAdmin())
                .getAddressList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
