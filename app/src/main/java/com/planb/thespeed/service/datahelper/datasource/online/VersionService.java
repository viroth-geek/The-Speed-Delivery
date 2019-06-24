package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.Version;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.version.VersionAPI;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 10/1/18.
 */

public class VersionService extends BaseService<List<Version>> {

    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public VersionService(InvokeOnCompleteAsync<List<Version>> onCompleteAsync) {
        super(onCompleteAsync);
        request();
    }

    /**
     * request
     */
    private void request() {
        ServiceGenerator.createService(VersionAPI.class)
                .getCurrentVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

}
