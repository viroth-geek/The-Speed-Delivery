package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.magento.search.searchResult.SearchResult;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.search.SearchService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author channarith.bong
 */
public class FetchSearchResult implements Observer<List<SearchResult>> {

    private InvokeOnCompleteAsync onCompleteAsync;
    private List<SearchResult> searchResults;
    private Disposable disposable;

    /**
     * @param searchTerm
     * @param onCompleteAsync
     */
    public FetchSearchResult(String searchTerm, Double latitude, Double longitude, InvokeOnCompleteAsync onCompleteAsync) {
        this.onCompleteAsync = onCompleteAsync;
        request(searchTerm, latitude, longitude);
    }

    /**
     * @param searchTerm
     * @param latitude
     * @param longitude
     */
    private void request(String searchTerm, Double latitude, Double longitude) {
        ServiceGenerator
                .createService(SearchService.class)
                .search(searchTerm, latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        onCompleteAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        onCompleteAsync.onComplete(searchResults);
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<SearchResult> searchResults);

        void onError(Throwable e);
    }
}