package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.Tag;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.store.StoreService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

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
