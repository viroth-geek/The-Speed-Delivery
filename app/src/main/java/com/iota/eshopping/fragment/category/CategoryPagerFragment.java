package com.iota.eshopping.fragment.category;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.ProductCategoryAdapter;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.event.InvokeAnimations;
import com.iota.eshopping.model.form.FormGetProductByCategory;
import com.iota.eshopping.model.magento.store.storeList.Category;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.model.modelForView.ProductCategory;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.online.GetProductByCategory;
import com.iota.eshopping.service.datahelper.mapper.DataMatcher;
import com.iota.eshopping.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeakleang.ay
 */
public class CategoryPagerFragment extends Fragment {

    private String title;

    private Store store;
    private ProductCategory productCategory;

    private OnLoadProductCompletedListener onLoadProductCompletedListener;
    private InvokeAnimations invokeAnimations;

    private ProductCategoryAdapter productCategoryAdapter;
    private RecyclerView productCategoryRecyclerView;
    private RelativeLayout optionLoadingProgressBar;

    private int PAGE = 1;
    private Integer count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_pager, container, false);

        productCategoryRecyclerView = view.findViewById(R.id.category_list);
        productCategoryRecyclerView.setHasFixedSize(true);

        productCategoryAdapter = new ProductCategoryAdapter(getContext(), productCategory, getProductItems(), this.store, invokeAnimations);
        productCategoryAdapter.setOptionLoadingProgressBar(optionLoadingProgressBar);
        productCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productCategoryRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        productCategoryRecyclerView.setAdapter(productCategoryAdapter);

        this.count = productCategory.getCount();

        productCategory.getProducts().clear();
        getProductByPageable(prepareForGetProduct(productCategory.getId(), 1));

        return view;
    }

    /**
     * scroll change for recycler view
     */
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = productCategoryRecyclerView.getLayoutManager().getChildCount();
            int totalItemCount = productCategoryRecyclerView.getLayoutManager().getItemCount();
            int firstVisibleItem = ((LinearLayoutManager) productCategoryRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                if ((count == null || PAGE < getLastPage()) && optionLoadingProgressBar.getVisibility() == View.GONE) {
                    PAGE += 1;
                    getProductByPageable(prepareForGetProduct(productCategory.getId(), PAGE));
                }
            }
        }
    };

    /**
     * Get product by pagable
     * @param formGetProductByCategory FormGetProductByCategory
     */
    private void getProductByPageable(FormGetProductByCategory formGetProductByCategory) {
        optionLoadingProgressBar.setVisibility(View.VISIBLE);
        new GetProductByCategory(formGetProductByCategory, new InvokeOnCompleteAsync<List<Category>>() {
            @Override
            public void onComplete(List<Category> data) {
                List<ProductCategory> productCategories = DataMatcher.getInstance().getProductCategory(data);
                for (ProductCategory category : productCategories) {
                    count = category.getCount();
                    productCategory.getProducts().addAll(category.getProducts());
                }
                productCategoryAdapter.setProductItems(getProductItems());
                productCategoryAdapter.notifyDataSetChanged();
                optionLoadingProgressBar.setVisibility(View.GONE);
                onLoadProductCompletedListener.onLoadProductCompleted(data);
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: ", e);
                optionLoadingProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * prepare get product
     * @param categoryId Long
     * @param page int
     * @return FormGetProductByCategory
     */
    private FormGetProductByCategory prepareForGetProduct(Long categoryId, int page) {
        FormGetProductByCategory formGetProductByCategory = new FormGetProductByCategory();
        formGetProductByCategory.setCategoryId(categoryId);
        formGetProductByCategory.setPage(page);
        formGetProductByCategory.setPageSize(ApplicationConfiguration.LIMIT);
        return formGetProductByCategory;
    }


    /**
     * Get title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter productCategory
     */
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    /**
     * Setter store
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * get product items
     * @return ProductItem
     */
    public List<ProductItem> getProductItems() {
        List<com.iota.eshopping.model.modelForView.ProductItem> productItems = new ArrayList<>();
        for (Product product : this.productCategory.getProducts()) {
            productItems.add(new com.iota.eshopping.model.modelForView.ProductItem<>(product.getId(), product.getPrice(), product));
        }
        return productItems;
    }

    /**
     * Setter invokeAnimations
     */
    public void setInvokeAnimations(InvokeAnimations invokeAnimations) {
        this.invokeAnimations = invokeAnimations;
    }

    /**
     * Setter optionLoadingProgressBar
     */
    public void setOptionLoadingProgressBar(RelativeLayout optionLoadingProgressBar) {
        this.optionLoadingProgressBar = optionLoadingProgressBar;
    }

    /**
     * Get productCategoryAdapter
     *
     * @return productCategoryAdapter
     */
    public ProductCategoryAdapter getProductCategoryAdapter() {
        return productCategoryAdapter;
    }

    /**
     * Get productCategoryRecyclerView
     *
     * @return productCategoryRecyclerView
     */
    public RecyclerView getProductCategoryRecyclerView() {
        return productCategoryRecyclerView;
    }

    /**
     * Get last page
     * @return
     */
    private int getLastPage() {
        int listSize = this.count;
        return listSize % ApplicationConfiguration.LIMIT == 0 ? listSize / ApplicationConfiguration.LIMIT : (listSize / ApplicationConfiguration.LIMIT) + 1 ;
    }

    /**
     * Setter onLoadProductCompletedListener
     */
    public void setOnLoadProductCompletedListener(OnLoadProductCompletedListener onLoadProductCompletedListener) {
        this.onLoadProductCompletedListener = onLoadProductCompletedListener;
    }

    /**
     *
     */
    public interface OnLoadProductCompletedListener {
        void onLoadProductCompleted(List<Category> categories);
    }

}
