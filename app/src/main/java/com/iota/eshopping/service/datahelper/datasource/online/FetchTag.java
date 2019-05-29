package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.Tag;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.store.StoreService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 1/28/19.
 */

public class FetchTag extends BaseService<List<Tag>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchTag(InvokeOnCompleteAsync<List<Tag>> onCompleteAsync) {
        super(onCompleteAsync);
        request();
    }

    /**
     *
     */
    private void request() {
        ServiceGenerator
                .createService(StoreService.class)
                .getTags()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
