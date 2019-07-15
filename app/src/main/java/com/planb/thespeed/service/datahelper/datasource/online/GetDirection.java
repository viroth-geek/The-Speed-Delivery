package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.form.FormForGetDirection;
import com.planb.thespeed.model.direction.Direction;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 9/7/18.
 */

public class GetDirection extends BaseService<List<Direction>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public GetDirection(FormForGetDirection formForGetDirection, InvokeOnCompleteAsync<List<Direction>> onCompleteAsync) {
        super(onCompleteAsync);
        request(formForGetDirection);
    }

    /**
     * request
     * @param formForGetDirection FormForGetDirection
     */
    private void request(FormForGetDirection formForGetDirection) {
        ServiceGenerator.createService(StoreService.class)
                .getDirection(formForGetDirection)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
