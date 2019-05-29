package com.iota.eshopping.service.api.product;

import com.iota.eshopping.model.ConfigurationProductOption;
import com.iota.eshopping.model.Product;
import com.iota.eshopping.model.ProductOptionBody;
import com.iota.eshopping.model.form.FormGetProductByCategory;
import com.iota.eshopping.model.magento.catalog.Catalog;
import com.iota.eshopping.model.magento.catalog.ProductSku;
import com.iota.eshopping.model.magento.store.storeList.Category;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by channarith.bong on 12/21/17.
 *
 * @author channarith.bong
 */
public interface ProductService {

    /**
     * Get Catalog Level One
     *
     * @return Catalog
     * @see Catalog
     */
    @GET("all/V1/categories")
    Call<Catalog> getCatalogLevelOne();

    /**
     * Get List of Catalog level two
     *
     * @param id
     * @return List of ProductSKU
     * @see ProductSku
     */
    @GET("V1/categories/{categoryId}/products")
    Call<List<ProductSku>> getCatalogLevelTwo(@Path("categoryId") Long id);

    /**
     * Get Catalog level one
     *
     * @return Catalog
     * @see Catalog
     */
    @GET("all/V1/categories")
    Observable<Catalog> getCatalogLevelOneRx();

    /**
     * Get all Category list
     *
     * @return Catalog
     * @see Catalog
     */
    @GET("all/V1/categories")
    Observable<Catalog> getAllCategoryList();

    /**
     * Get Catalog level two
     *
     * @param id
     * @return List of ProductSku
     * @see ProductSku
     */
    @GET("V1/categories/{categoryId}/products")
    Observable<List<ProductSku>> getCatalogLevelTwoRx(@Path("categoryId") Long id);

    /**
     * Get product detail of product sku
     *
     * @param sku
     * @return Product of the SKU
     * @see Product
     */
    @GET("V1/products/{productSku}")
    Observable<Product> getProductDetailBySku(@Path("productSku") String sku);

    /**
     *
     * @param productId
     * @return
     */
    @GET("V2/eshopping/store/productOption/{productId}")
    Observable<List<ConfigurationProductOption>> getOptionProducts(@Path("productId") Long productId);

    /**
     *
     * @param productOptionBody ProductOptionBody
     * @return Observable<List<Product>>
     */
    @POST("V2/eshopping/order/getChildProduct")
    Observable<List<Product>> getChildProduct(@Body ProductOptionBody productOptionBody);

    /**
     * get product by category
     * @param formGetProductByCategory FormGetProductByCategory
     * @return List<Category>
     */
    @POST("V2/eshopping/store/getProductByStoreCategory")
    Observable<List<Category>> getProductByCategory(@Body FormGetProductByCategory formGetProductByCategory);
}
