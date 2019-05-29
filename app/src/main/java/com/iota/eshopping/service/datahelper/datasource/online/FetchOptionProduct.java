package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.ConfigurationProductOption;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.product.ProductService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 6/13/18.
 */

public class FetchOptionProduct extends BaseService<List<ConfigurationProductOption>> {

    /**
     *
     * @param productId productId
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public FetchOptionProduct(Long productId, InvokeOnCompleteAsync<List<ConfigurationProductOption>> onCompleteAsync) {
        super(onCompleteAsync);
        request(productId);
    }

    /**
     * request
     * @param productId Long
     */
    private void request(Long productId) {
        ServiceGenerator.createService(ProductService.class)
                .getOptionProducts(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);

    }
}
