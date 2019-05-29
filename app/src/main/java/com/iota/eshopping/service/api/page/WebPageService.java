package com.iota.eshopping.service.api.page;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author channarith.bong
 */
public interface WebPageService {

    /**
     * Get content of static page
     *
     * @param key Page name
     * @return HTML String
     */
    @GET("V2/eshopping/page/{key}")
    Observable<String> getContent(@Path("key") String key);
}
