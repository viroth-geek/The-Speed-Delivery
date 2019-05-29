package com.iota.eshopping.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.event.InvokeAnimations;
import com.iota.eshopping.fragment.cart.ItemAdjustment;
import com.iota.eshopping.fragment.productoption.ProductOptionDialog;
import com.iota.eshopping.model.ConfigurationProductOption;
import com.iota.eshopping.model.ProductOption;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.model.modelForView.ProductCategory;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.online.FetchOptionProduct;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.ImageViewUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author yeakleang.ay on 9/18/18.
 */

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ProductViewHolder>
        implements ItemAdjustment.OnChangeValue {

    private InvokeAnimations invokeAnimation;

    private Context context;
    private ProductCategory productCategory;

    private List<ProductItem> productItems;

    private Store store;

    private RelativeLayout optionLoadingProgressBar;

    /**
     * Constructor
     * @param context Context
     * @param productCategory List<Product>
     */
    public ProductCategoryAdapter(Context context, ProductCategory productCategory, List<ProductItem> productItems, Store store, InvokeAnimations invokeAnimation) {
        this.context = context;
        this.productCategory = productCategory;
        this.store = store;
        this.productItems = productItems;
        this.invokeAnimation = invokeAnimation;
    }

    /**
     * update item amount when back from MangeBasket
     *
     * @param productItems list of product item
     */
    public void updateItemAmount(List<ProductItem> productItems, boolean isUpdated) {

        if (productItems == null) {
            return;
        }

        if (productItems.isEmpty()) {
            for (ProductItem productItem : this.productItems) {
                productItem.setCount(0);
            }
            this.notifyDataSetChanged();
            return;
        }

        for (int j = 0; j < this.productItems.size(); j++) {
            int qty = 0;
            for (ProductItem item : productItems) {
                Product product = (Product) item.getItem();
                // update configure product
                if (this.productItems.get(j).getId().equals(product.getParentId())) {
                    if (isUpdated) {
                        this.productItems.get(j).setCount(0);
                        qty += this.productItems.get(j).getCount() + item.getCount();
                    }
                    else {
//                            qty = this.sections.get(i).productItems.get(j).getCount() + product.getCount() ;
                        qty = this.productItems.get(j).getCount() + item.getCount() ;
                    }
                    this.productItems.get(j).setCount(qty);
                    this.notifyItemChanged(j);
                }
                // update simple product
                else if (this.productItems.get(j).getId().equals(item.getId())) {

                    Product originalProduct = (Product) this.productItems.get(j).getItem();
                    Product localProduct = (Product) item.getItem();

                    if (localProduct.getImageUrl() == null || localProduct.getImageUrl().isEmpty()) {
                        localProduct.setImageUrl(originalProduct.getImageUrl());
                    }

                    this.productItems.set(j, item);
                    if (isUpdated) {
                        this.productItems.get(j).setCount(item.getCount());
                    }
                    else {
                        if (this.productItems == null) {
                            this.productItems = new ArrayList<>();
                        }

                        if (this.productItems.isEmpty()) {
                            this.productItems.add(item);
                        }
                        else {
                            boolean isAdded = false;
                            for (int k = 0; k < this.productItems.size(); k++) {
                                if (this.productItems.get(k).getId().equals(item.getId())) {
                                    isAdded = true;
                                }
                            }
                            if (!isAdded) {
                                this.productItems.add(item);
                            }
//                                invokeAnimation.invokeAnimationByItems(productItemList);
                        }
                        invokeAnimation.invokeAnimationByItems(this.productItems);
                    }
                    this.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * Setter productItems
     */
    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_product, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = this.productCategory.getProducts().get(position);
        ProductItem productItem = this.productItems.get(position);

        if (productItem.getCount() > 0) {
            holder.txt_number_of_item.setVisibility(View.VISIBLE);
            holder.txt_number_of_item.setText(String.format(Locale.getDefault(), "x %d", productItem.getCount()));
        }
        else {
            holder.txt_number_of_item.setVisibility(View.INVISIBLE);
        }

        holder.txt_product_name.setText(product.getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txt_product_detail.setText(Html.fromHtml(product.getDetail() == null ? "" : product.getDetail(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.txt_product_detail.setText(Html.fromHtml(product.getDetail() == null ? "" : product.getDetail()));
        }


        holder.txt_product_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(product.getPrice())));

        holder.setProductItem(productItem);

        String imageUrl = product.getImageUrl();

        String imageUrlLink = ApplicationConfiguration.PRODUCT_IMAGE_URL + imageUrl;
        ImageViewUtil.loadImageByUrl(context, imageUrlLink, holder.img_product);
    }

    @Override
    public int getItemCount() {
        return this.productCategory.getProducts().size();
    }

    /**
     * Item Adjustment
     */
    private void showItemAdjustment(ProductItem proItem) {
        ItemAdjustment adjustment = new ItemAdjustment();
        adjustment.setProduct(proItem, this);
        adjustment.setStore(this.store);
        if (context != null) {
            adjustment.show(((AppCompatActivity) context).getSupportFragmentManager(), "ITEM");
        }
    }

    @Override
    public void onChange(ProductItem productItem) {
        Product product = (Product) productItem.getItem();
        product.setCount(productItem.getCount());
        product.setStoreId(this.store.getId());
        updateItemAmount(Collections.singletonList(productItem), false);
    }

    /**
     * Setter optionLoadingProgressBar
     */
    public void setOptionLoadingProgressBar(RelativeLayout optionLoadingProgressBar) {
        this.optionLoadingProgressBar = optionLoadingProgressBar;
    }

    /**
     *
     * @param product com.iota.eshopping.model.Product
     */
    private void fetchOptionProduct(com.iota.eshopping.model.Product product) {
        new FetchOptionProduct(product.getId(), new InvokeOnCompleteAsync<List<ConfigurationProductOption>>() {
            @Override
            public void onComplete(List<ConfigurationProductOption> configurationProductOptions) {
                if (configurationProductOptions.size() > 0) {
                    showOptionDialog(product, configurationProductOptions.get(0));
                }
                optionLoadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                optionLoadingProgressBar.setVisibility(View.GONE);
                LoggerHelper.showErrorLog("Error: ", e);
                Toast.makeText(context, "Cannot get option product:\n" + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param product com.iota.eshopping.model.Product
     * @see com.iota.eshopping.model.Product
     * @param configurationProductOption list of configurationProductOption
     * @see ProductOption
     */
    private void showOptionDialog(com.iota.eshopping.model.Product product, ConfigurationProductOption configurationProductOption) {
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            ProductOptionDialog productOptionDialog = new ProductOptionDialog();
            if (context instanceof ProductOptionDialog.OnProductOptionChooseListener) {
                productOptionDialog.setOnOptionItemChooseListener((ProductOptionDialog.OnProductOptionChooseListener) context);
            }
            productOptionDialog.setConfigurationProductOption(configurationProductOption);
            productOptionDialog.setStoreId(store.getId());
            productOptionDialog.setParentProduct(product);
            productOptionDialog.show(activity.getSupportFragmentManager(), "options");
        }
        else {
            Toast.makeText(context, "Fail to show", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     */
    class ProductViewHolder extends RecyclerView.ViewHolder {

        private ProductItem productItem;

        private TextView txt_number_of_item;
        private TextView txt_product_name;
        private TextView txt_product_detail;
        private TextView txt_product_price;
        private ImageView img_product;

        /**
         *
         * @param itemView View
         */
        ProductViewHolder(View itemView) {
            super(itemView);

            txt_number_of_item = itemView.findViewById(R.id.txt_selected_items);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_detail = itemView.findViewById(R.id.txt_product_detail);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            img_product = itemView.findViewById(R.id.img_product);

            itemView.setOnClickListener(view -> {
                if (optionLoadingProgressBar.getVisibility() != View.VISIBLE) {
                    Product product = (Product) productItem.getItem();

                    com.iota.eshopping.model.Product productForModel = new com.iota.eshopping.model.Product();
                    productForModel.setProductId(product.getId());
                    productForModel.setSku(product.getSku());
                    productForModel.setName(product.getName());
                    productForModel.setPrice(product.getPrice().doubleValue());
                    productForModel.setQty(product.getCount());
                    productForModel.setProductType(product.getProductType());
                    productForModel.setId(product.getId());

                    if (product.getProductType().equals(ConstantValue.CONFIGURABLE_PRODUCT)) {
                        optionLoadingProgressBar.setVisibility(View.VISIBLE);
                        fetchOptionProduct(productForModel);
                    } else if (product.getProductType().equals(ConstantValue.SIMPLE_PRODUCT)) {
                        showItemAdjustment(productItem);
                    }
                }
            });
        }

        /**
         * Setter productItem
         */
        public void setProductItem(ProductItem productItem) {
            this.productItem = productItem;
        }
    }
}
