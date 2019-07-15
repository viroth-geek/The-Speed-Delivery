package com.planb.thespeed.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.ProductItemAdapter;
import com.planb.thespeed.model.magento.store.storeList.StoreView;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.service.datahelper.mapper.DataMatcher;

import java.util.List;

/**
 * @author channarith.bong
 */
public class AddProductItemActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_item);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Store");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        bindData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Request data from server
     */
    private void bindData() {
        StoreView storeView = new StoreView();
        Store store = DataMatcher.getInstance().getStore(storeView);
        bindViewItem(store);
    }

    /**
     * Assign value into view
     *
     * @param store Store
     */
    private void bindViewItem(Store store) {
        if (store != null) {
            toolbar.setTitle(store.getName());
            List<ProductCategory> productCategories = store.getCategories();
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            ProductItemAdapter productItemAdapter = new ProductItemAdapter(AddProductItemActivity.this, productCategories);

            RecyclerView productCategoryList = findViewById(R.id.product_item_list);
            productCategoryList.setHasFixedSize(true);
            productCategoryList.setLayoutManager(layoutManager);
            productCategoryList.setAdapter(productItemAdapter);
        }
    }
}
