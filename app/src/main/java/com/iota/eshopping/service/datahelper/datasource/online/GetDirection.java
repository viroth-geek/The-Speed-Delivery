package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.form.FormForGetDirection;
import com.iota.eshopping.model.direction.Direction;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

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
