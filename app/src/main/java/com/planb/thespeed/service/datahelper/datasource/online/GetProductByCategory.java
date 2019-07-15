package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.model.form.FormGetProductByCategory;
import com.planb.thespeed.model.magento.store.storeList.Category;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.product.ProductService;
import com.planb.thespeed.service.base.BaseService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yeakleang.ay on 9/25/18.
 */

public class GetProductByCategory extends BaseService<List<Category>> {


    /**
     * Constructor
     *
     * @param onCompleteAsync InvokeOnCompleteAsync
     */
    public GetProductByCategory(FormGetProductByCategory formGetProductByCategory, InvokeOnCompleteAsync<List<Category>> onCompleteAsync) {
        super(onCompleteAsync);
        request(formGetProductByCategory);
    }

    /**
     * request
     * @param formGetProductByCategory FormGetProductByCategory
     */
    private void request(FormGetProductByCategory formGetProductByCategory) {
        ServiceGenerator.createService(ProductService.class)
                .getProductByCategory(formGetProductByCategory)
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }
}
