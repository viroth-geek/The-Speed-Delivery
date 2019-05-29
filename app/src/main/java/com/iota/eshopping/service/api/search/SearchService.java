package com.iota.eshopping.service.api.search;

import com.iota.eshopping.model.magento.search.searchResult.SearchResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author channarith.bong
 */
public interface SearchService {

    /**
     * Search Store and Product by provided coordinate
     *
     * @param searchTerm
     * @param latitude
     * @param longitude
     * @return List of SearchResult
     * @see SearchResult
     */
    @GET("V2/eshopping/search/{term}/{lat}/{long}")
    Observable<List<SearchResult>> search(@Path("term") String searchTerm, @Path("lat") Double latitude, @Path("long") Double longitude);
}
