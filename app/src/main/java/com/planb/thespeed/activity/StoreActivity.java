package com.planb.thespeed.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.CategoryPagerAdapter;
import com.planb.thespeed.adapter.ProductCategoryAdapter;
import com.planb.thespeed.adapter.ProductOrderAdapter;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.event.InvokeAnimations;
import com.planb.thespeed.fragment.cart.ItemAdjustment;
import com.planb.thespeed.fragment.category.CategoryPagerFragment;
import com.planb.thespeed.fragment.productoption.ProductOptionDialog;
import com.planb.thespeed.model.Product;
import com.planb.thespeed.model.enumeration.DayType;
import com.planb.thespeed.model.magento.store.storeList.Category;
import com.planb.thespeed.model.modelForView.OrderItem;
import com.planb.thespeed.model.modelForView.ProductAttributeOption;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.model.modelForView.ProductItem;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.security.CurrencyConfiguration;
import com.planb.thespeed.security.ProductLocalService;
import com.planb.thespeed.service.CategoryService;
import com.planb.thespeed.service.datahelper.datasource.sample.SampleData;
import com.planb.thespeed.util.DeliveryAnimationUtils;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.FontManager;
import com.planb.thespeed.util.ImageViewUtil;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.NumberUtils;
import com.planb.thespeed.util.preference.StorePreference;
import com.planb.thespeed.util.preference.TimeDeliveryPreference;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;

/**
 * @author channarith.bong
 */
public class StoreActivity extends AppCompatActivity implements View.OnClickListener,
        InvokeAnimations,
        ProductOptionDialog.OnProductOptionChooseListener,
        ItemAdjustment.OnChangeValue,
        CategoryPagerFragment.OnLoadProductCompletedListener {

    private TabLayout categoryTabLayout;
    private ViewPager categoryViewPager;

    private RecyclerView itemProductList;
    private FrameLayout viewBasket;
    private FrameLayout viewLoading;
    private Button btn_basket;
    private Toolbar toolbar;
    private TextView txt_estore_name, txt_estore_type, txt_duration, txt_percentage, txt_tag, txt_rated, txt_estore_desc, txt_is_open;
    private TextView txt_basket_item_count, txt_basket_item_price;
    private ImageView img_estore;
    private ProgressBar progressBar;
    private RelativeLayout optionLoadingProgressBar;

    private Pair<Integer, Float> basketItems;
    private List<ProductItem> productItems;
    private List<ProductCategory> productCategoryListTemp;

    private List<CategoryPagerFragment> categoryPagerFragments;

    private ProductCategoryAdapter stickyAdapter;
    private Store store;
    private Typeface font;

    private ProductLocalService productLocalService;
    private ProductAttributeOption productAttributeOption;

    private static int MAX_ATTEMPT = 2;
    private static int ATTEMPT_COUNT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estore);

        font = FontManager.getTypeface(this, FontManager.FONTAWESOME);

        productLocalService = new ProductLocalService(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Store");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        img_estore = findViewById(R.id.img_estore);
        txt_estore_name = findViewById(R.id.txt_estore_name);
        txt_estore_type = findViewById(R.id.txt_estore_type_list);
        txt_percentage = findViewById(R.id.txt_percentage);
        txt_rated = findViewById(R.id.txt_rating);
        txt_tag = findViewById(R.id.txt_tag);
        txt_duration = findViewById(R.id.txt_time);
        txt_estore_desc = findViewById(R.id.txt_estore_desc);
        txt_is_open = findViewById(R.id.txt_is_open);
        btn_basket = findViewById(R.id.btn_basket);

        viewBasket = findViewById(R.id.container_float_basket);
        viewLoading = findViewById(R.id.container_float_loading);
        progressBar = findViewById(R.id.loading_cycle_ii);
        optionLoadingProgressBar = findViewById(R.id.loading_progress_bar);
        optionLoadingProgressBar.setVisibility(View.GONE);
        txt_basket_item_count = findViewById(R.id.txt_basket_item_count);
        txt_basket_item_price = findViewById(R.id.txt_basket_item_price);

        categoryTabLayout = findViewById(R.id.tab_layout_category);
        categoryViewPager = findViewById(R.id.view_pager_category);

//        productCategoryList = findViewById(R.id.category_list);
//        productCategoryList.setHasFixedSize(true);

        setUpViewPager();

        itemProductList = findViewById(R.id.item_orders_list);
        bindData();
    }

    /**
     * set up view pager
     */
    private void setUpViewPager() {
        categoryTabLayout.setupWithViewPager(categoryViewPager);
        categoryTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                stickyAdapter = categoryPagerFragments.get(tab.getPosition()).getProductCategoryAdapter();
                if (stickyAdapter != null) {
                    stickyAdapter.updateItemAmount(productItems, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (btn_basket.equals(v)) {
            List<ProductItem> items = Observable.fromIterable(productItems).filter(productItem -> productItem.getCount() > 0).toList().blockingGet();
            if (!items.isEmpty()) {

                String tomorrowText = TimeDeliveryPreference.getTimeDeliveryText(this).split(" ")[0];
                if (tomorrowText.equalsIgnoreCase(DayType.TOMORROW.toString())) {
                    if (!store.isStatusOpenTomorrow()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Store Message");
                        builder.setMessage(store.getNameKh().isEmpty() ? store.getName() : store.getNameKh() + " is not opened tomorrow.");
                        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                        builder.create().show();
                        return;
                    }
                }

                if (!store.isOpenToday()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Store Message");
                    builder.setMessage(store.getNameKh().isEmpty() ? store.getName() : store.getNameKh() + " is not open.");
                    builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.create().show();
                    return;
                }
                Log.d(ConstantValue.TAG_LOG, "onClick: " + productItems.size());
                Intent intent = new Intent(this, ManageBasketActivity.class);
                intent.putExtra(ConstantValue.ITEMS, (Serializable) productItems);
                intent.putExtra(ConstantValue.STORE, store);
                startActivityForResult(intent, ConstantValue.GO_TO_BASKET);


            } else {
                Toast.makeText(this, "No item in basket.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantValue.HOME_CALLING_CODE) {
            finish();
        }
        // update item amount from ManageBasket
        else if (requestCode == ConstantValue.GO_TO_BASKET) {

            if (data != null) {
                if (data.hasExtra(ConstantValue.PRODUCT_ITEMS)) {
                    productItems = (List<ProductItem>) data.getSerializableExtra(ConstantValue.PRODUCT_ITEMS);
                } else {
                    return;
                }
            }

            showProgressBar(View.GONE);

            if (stickyAdapter != null) {
                stickyAdapter.updateItemAmount(productItems, true);
            }

            addToBasket(productItems);
            showBasketBox();

        }
    }

    @Override
    public void invokeAnimationByItems(List<ProductItem> productItems) {
        if (productItems != null && !productItems.isEmpty()) {
            addToBasket(productItems);
            showBasketBox();
        }
    }

    /**
     * Add list of items into collection (Basket)
     *
     * @param productItems ProductItems
     */
    private void addToBasket(List<ProductItem> productItems) {
        if (productItems == null || productItems.isEmpty()) {
            this.productItems = new ArrayList<>();
            this.basketItems = new Pair<>(0, 0f);
        }
        else {
            int count = 0;
            float price = 0f;

            for (ProductItem productItem : productItems) {
                if (productItem.getCount() > 0) {
                    count += productItem.getCount();
                    for (int i = 0; i < productItem.getCount(); i++) {
                        price += productItem.getUnitPrice();
                    }
                }
            }
            this.basketItems = new Pair<>(count, price);
        }

        this.productItems = productItems;
    }

    /**
     * Show Dialog box of basket
     */
    private void showBasketBox() {
        txt_basket_item_count.setText(String.format(getString(R.string.items), basketItems.first));
        txt_basket_item_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(basketItems.second)));

        if (viewBasket.getVisibility() != View.VISIBLE) {
            DeliveryAnimationUtils.loadAnimationSlideUp(this, viewBasket);

            // update recycler view height
            viewBasket.measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//            productCategoryList.getLayoutParams().height = productCategoryList.getHeight() - viewBasket.getMeasuredHeight();
        }
    }

    /**
     * Set value into each control in view of Store
     *
     * @param store Store
     */
    private void bindViewStore(Store store) {
        if (store != null) {

//            StringBuilder estoreType = new StringBuilder();
//            if (store.getStoreTypes() != null) {
//                for (StoreType type : store.getStoreTypes()) {
//                    if (store.getStoreTypes().lastIndexOf(type) == store.getStoreTypes().size() - 1) {
//                        estoreType.append(type.getTypeName()).append(".");
//                    } else {
//                        estoreType.append(type.getTypeName()).append(", ");
//                    }
//                }
//            }

            String tag = getString(R.string.fa_tag) + " " + store.getTag();
            if (Objects.equals(store.getTag(), "") || store.getTag().isEmpty()) {
                tag = "";
            }

            String openHour = store.getOpenHour();
            if (openHour == null || openHour.isEmpty()) {
                openHour = "None";
            }

            toolbar.setTitle(store.getName());

            txt_estore_type.setText(store.getName());
            if (store.getNameKh() != null && !store.getNameKh().isEmpty()) {
//                vh.txt_estore.setText(store.getName() + "/" + store.getNameKh());
                txt_estore_name.setText(store.getNameKh());
            } else {
                txt_estore_name.setText(store.getName());
            }

//            txt_estore_name.setText(store.getName());
//            txt_estore_type.setText(estoreType.toString());
            if (!store.isOpenToday()) {
                txt_is_open.setTextColor(getResources().getColor(R.color.red));
            } else {
                txt_is_open.setTextColor(getResources().getColor(R.color.green));
            }
            txt_is_open.setText(store.isOpenToday() ? "Open" : "Closed");
            txt_percentage.setTypeface(font);
            txt_percentage.setText(String.format("%s %s %%", getString(R.string.fa_smile_o), store.getPercentage()));
            txt_rated.setText(String.format("%s ", store.getRate()));
            txt_tag.setTypeface(font);
            txt_tag.setText(tag);
            txt_duration.setText(openHour); // Using 'Open Hour' instead 'Duration'
            txt_estore_desc.setText(store.getDescription());

            btn_basket.setOnClickListener(this);
        }
    }

    /**
     * Set data into order items
     *
     * @param orderItems Order Item
     */
    private void bindViewOrdersItems(List<OrderItem> orderItems) {
        ProductOrderAdapter productOrderAdapter = new ProductOrderAdapter(StoreActivity.this, orderItems);
        itemProductList.setHasFixedSize(true);
        itemProductList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemProductList.setAdapter(productOrderAdapter);
    }

    /**
     * Request data form server
     */
    private void bindData() {
        StorePreference.clearServiceFee(this);

        store = (Store) getIntent().getSerializableExtra(ConstantValue.STORE);

//        byte[] imageBytes = getIntent().getByteArrayExtra(ConstantValue.STORE_IMAGE);
//        if (imageBytes != null) {
//            img_estore.setImageBitmap(ImageViewUtil.bitMap(imageBytes));
//        }
        ImageViewUtil.loadImageByUrl(this, store.getLogoUrl(), img_estore);

        List<OrderItem> orderItems = SampleData.getInstance().getOrderItems();

        bindViewStore(store);
        bindViewOrdersItems(orderItems);
        loadCategory(store.getId());
    }

    /**
     * fetch cached products
     */
    private void fetchCachedProducts() {
        List<com.planb.thespeed.model.modelForView.Product> products = productLocalService.getListItem();
        products = Observable.fromIterable(products).filter(product -> product.getStoreId() != null && product.getCount() > 0 && product.getStoreId().equals(store.getId())).toList().blockingGet();

        if (products.isEmpty()) {
            return;
        }

        if (this.productItems == null) {
            this.productItems = new ArrayList<>();
        } else {
            this.productItems.clear();
        }

        for (com.planb.thespeed.model.modelForView.Product product : products) {
            this.productItems.add(new ProductItem<>(product.getId(), product.getCount(), product.getPrice(), product));
        }

        if (stickyAdapter != null) {
            stickyAdapter.updateItemAmount(this.productItems, true);
        }

        addToBasket(this.productItems);
        showBasketBox();
    }

    /**
     * Get data of category by Store ID
     *
     * @param storeId Store ID
     */
    private void loadCategory(final Long storeId) {
        showProgressBar(View.VISIBLE);
        CategoryService.getInstance().loadCategoryList(storeId, new CategoryService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<ProductCategory> productCategoryList) {
                productCategoryListTemp = Observable.fromIterable(productCategoryList).filter(productCategory -> productCategory.getCount() > 0).toList().blockingGet();
//                if (productCategoryListTemp != null && !productCategoryListTemp.isEmpty()) {
                    bindViewCategoryProduct(productCategoryListTemp);
//                } else {
//                    Toast.makeText(StoreActivity.this, R.string.no_product_found, Toast.LENGTH_SHORT).show();
//                    showProgressBar(View.GONE);
//                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    ATTEMPT_COUNT++;
                    if (ATTEMPT_COUNT <= MAX_ATTEMPT) {
                        loadCategory(storeId);
                    } else {
                        Toast.makeText(StoreActivity.this, R.string.cannot_get_product, Toast.LENGTH_SHORT).show();
                        showProgressBar(View.GONE);
                    }
                } else {
                    LoggerHelper.showErrorLog("Error: ", e);
                    Toast.makeText(StoreActivity.this, "Problem while getting product: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                    showProgressBar(View.GONE);
                }
            }
        });
    }

    /**
     * Set value into view of Product of each categories
     *
     * @param productCategories List of product category
     */
    private void bindViewCategoryProduct(List<ProductCategory> productCategories) {
//        if (productCategories.size() == 0) {
//            Toast.makeText(StoreActivity.this, R.string.no_product_found, Toast.LENGTH_SHORT).show();
//            showProgressBar(View.GONE);
//        } else {

            categoryPagerFragments = new ArrayList<>();
            for (int index = 0; index < productCategories.size(); index++) {
                ProductCategory productCategory = productCategories.get(index);
                CategoryPagerFragment categoryPagerFragment = new CategoryPagerFragment();
                categoryPagerFragment.setTitle(productCategory.getName());
                categoryPagerFragment.setProductCategory(productCategory);
                categoryPagerFragment.setStore(this.store);
                categoryPagerFragment.setInvokeAnimations(this);
                categoryPagerFragment.setOptionLoadingProgressBar(optionLoadingProgressBar);
                categoryPagerFragment.setOnLoadProductCompletedListener(this);
                categoryPagerFragments.add(categoryPagerFragment);


            }

            CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager(), categoryPagerFragments);
            categoryViewPager.setAdapter(categoryPagerAdapter);

            if (categoryTabLayout.getTabCount() > 0) {
                categoryTabLayout.getTabAt(0).select();
            }

//            StickyHeaderLayoutManager stickyLayoutManager = new StickyHeaderLayoutManager();
//            stickyAdapter = new ProductCategoryStickyAdapter(StoreActivity.this, store, productCategories, getSupportFragmentManager(), this);
//            stickyAdapter.setOption_progress_bar(optionProgressBar);
//            productCategoryList.setLayoutManager(stickyLayoutManager);
//            productCategoryList.setAdapter(stickyAdapter);
            showProgressBar(View.GONE);
//        }
        fetchCachedProducts();
    }

    /**
     * Show dialog of progress bar
     *
     * @param show View Type (View.Visible, View.InVisible)
     */
    private void showProgressBar(int show) {
        viewLoading.setVisibility(show);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    /**
     * Item Adjustment
     */
    private void showItemAdjustment(ProductItem proItem, ProductAttributeOption productAttributeOption) {
        ItemAdjustment adjustment = new ItemAdjustment();
        adjustment.setStore(store);
        adjustment.setProduct(proItem, this);
        adjustment.setProductAttributeOption(productAttributeOption);

        if (getSupportFragmentManager() != null) {
            adjustment.show(getSupportFragmentManager(), "ITEM");
        }
    }

    /**
     * cast from product to product item
     * @param product Product
     * @return ProductItem
     */
    private ProductItem getProductItem(Product product) {
        com.planb.thespeed.model.modelForView.Product productForView = new com.planb.thespeed.model.modelForView.Product();
        Float price = product.getPrice() == null ? 0.00f : product.getPrice().floatValue();

        productForView.setCount(product.getQty());
        productForView.setId(product.getProductId());
        productForView.setProductType(product.getProductType());
        productForView.setName(product.getName());
        productForView.setParentId(product.getParentId());
        productForView.setPrice(price);
        productForView.setSku(product.getSku());
        productForView.setDetail(product.getName());

        return new ProductItem<>(product.getProductId(), product.getQty(), price, productForView);
    }

    @Override
    public void onOptionChoose(Product product, ProductAttributeOption productAttributeOption) {
        this.productAttributeOption = productAttributeOption;
        showItemAdjustment(getProductItem(product), productAttributeOption);
    }

    @Override
    public void onChange(ProductItem productItem) {

        if (productItems == null) {
            productItems = new ArrayList<>();
        }

//        boolean isExist = false;

        com.planb.thespeed.model.modelForView.Product product = (com.planb.thespeed.model.modelForView.Product) productItem.getItem();
        product.setProductType(ConstantValue.CONFIGURABLE_PRODUCT);

        productItems.add(productItem);

        for (int i = 0; i < productCategoryListTemp.size(); i++) {
            ProductCategory productCategory = productCategoryListTemp.get(i);
            for (int j = 0; j < productCategory.getProducts().size(); j++) {
                com.planb.thespeed.model.modelForView.Product product1 = productCategory.getProducts().get(j);
                if(product.getParentId() != null){
                    if (product.getParentId().equals(product1.getId())) {
                        stickyAdapter.updateItemAmount(Collections.singletonList(productItem), false);
                    }
                }
            }
        }

//        stickyAdapter.setProductItems(this.productItems);
//        stickyAdapter.setProductItems(this.productItems);
        addToBasket(productItems);
        showBasketBox();
    }

    @Override
    public void onLoadProductCompleted(List<Category> categories) {
        fetchCachedProducts();
    }

}
