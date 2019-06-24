package com.planb.thespeed.service.api.version;

import com.planb.thespeed.model.Version;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author yeakleang.ay on 10/1/18.
 */

public interface VersionAPI {

    /**
     * get current version
     * @return Observable<List<Version>>
     */
    @GET("V2/eshopping/mobile/getVersion/android")
    Observable<List<Version>> getCurrentVersion();

}
