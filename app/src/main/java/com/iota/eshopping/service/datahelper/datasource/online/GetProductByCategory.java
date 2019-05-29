package com.iota.eshopping.service.datahelper.datasource.online;

import com.iota.eshopping.model.form.FormGetProductByCategory;
import com.iota.eshopping.model.magento.store.storeList.Category;
import com.iota.eshopping.server.ServiceGenerator;
import com.iota.eshopping.service.api.product.ProductService;
import com.iota.eshopping.service.base.BaseService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;

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
