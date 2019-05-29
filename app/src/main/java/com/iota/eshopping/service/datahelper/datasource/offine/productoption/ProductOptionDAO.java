package com.iota.eshopping.service.datahelper.datasource.offine.productoption;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.model.Option;
import com.iota.eshopping.model.ProductOption;
import com.iota.eshopping.server.DatabaseHelper;

import java.util.List;

/**
 * @author yeakleang.ay on 7/19/18.
 */

public class ProductOptionDAO {

    private ProductOptionLocalService productOptionLocalService;

    private OptionLocalService optionLocalService;

    /**
     * constructor
     * @param context Context
     */
    public ProductOptionDAO(Context context) {
        this.productOptionLocalService = new ProductOptionLocalService(DatabaseHelper.getInstance(context).getDatabase());
        this.optionLocalService = new OptionLocalService(DatabaseHelper.getInstance(context).getDatabase());
    }

    /**
     * save product option
     * @param productOption ProductOption
     */
    public void save(ProductOption productOption) {
        Long productOptionId = productOptionLocalService.save(productOption);
        if (productOptionId != 0) {
            for (Option option : productOption.getOptions()) {
                option.setProductUid(productOption.getProductUid());
                optionLocalService.save(option, productOptionId);
            }
        }
    }

    /**
     * get product option
     * @return ProductOption
     */
    public List<ProductOption> getProductOptions() {
        List<ProductOption> productOptions = productOptionLocalService.getProductOptions();
        if (productOptions != null && !productOptions.isEmpty()) {
            for (ProductOption productOption : productOptions) {
                List<Option> options = optionLocalService.getOptions(productOption.getId());
                productOption.setOptions(options);
            }
        }
        return productOptions;
    }

    /**
     * delete all
     * @return boolean
     */
    public boolean deleteAll() {
        return optionLocalService.deleteAll() && productOptionLocalService.deleteAll();
    }

    /**
     * delete where product uid
     * @param productUid String
     * @return boolean
     */
    public boolean deleteWhereProductUid(String productUid) {
        return optionLocalService.deleteWhereProductUUID(productUid) && productOptionLocalService.deleteWhereProductUUID(productUid) ;
    }
}
