package com.planb.thespeed.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.constant.ApplicationConfiguration;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.event.InvokeAnimations;
import com.planb.thespeed.fragment.cart.ItemAdjustment;
import com.planb.thespeed.fragment.productoption.ProductOptionDialog;
import com.planb.thespeed.model.ConfigurationProductOption;
import com.planb.thespeed.model.ProductOption;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.model.modelForView.ProductItem;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.security.CurrencyConfiguration;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.online.FetchOptionProduct;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.ImageViewUtil;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by channarith.bong on 12/6/17.
 *
 * @author channarith.bong
 */
public class ProductCategoryStickyAdapter extends SectioningAdapter implements ItemAdjustment.OnChangeValue {

    private InvokeAnimations invokeAnimation;
    private Context context;
    private List<Section> sections;
    private ProgressBar option_progress_bar;

    private Store store;

    private List<ProductItem> productItemList;

    private FragmentManager fragmentManager;

     /**
     *
     * @param context Context
     * @param store store
     * @param data list of ProductCategory
     * @param fragmentManager FragmentManager
     * @param invokeAnimation InvokeAnimations
     */
    public ProductCategoryStickyAdapter(Context context, Store store, List<ProductCategory> data, FragmentManager fragmentManager, InvokeAnimations invokeAnimation) {
        this.context = context;
        this.store = store;
        this.sections = setSection(data);
        this.invokeAnimation = invokeAnimation;
        this.productItemList = new ArrayList<>();
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).productItems.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, final int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_product, parent, false);
        return new ViewHolderChild(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_product_category, parent, false);

        return new ViewHolderHead(v);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section section = sections.get(sectionIndex);
        ViewHolderChild viewHolderChild = (ViewHolderChild) viewHolder;
        ProductItem productItem = section.productItems.get(itemIndex);
        Product product = (Product) productItem.getItem();

        if (productItem.getCount() > 0) {
            viewHolderChild.txt_number_of_item.setVisibility(View.VISIBLE);
            viewHolderChild.txt_number_of_item.setText("x " + productItem.getCount());
        }
        else {
            viewHolderChild.txt_number_of_item.setVisibility(View.INVISIBLE);
        }

        viewHolderChild.txt_product_name.setText(product.getName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolderChild.txt_product_detail.setText(Html.fromHtml(product.getDetail() == null ? "" : product.getDetail(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            viewHolderChild.txt_product_detail.setText(Html.fromHtml(product.getDetail() == null ? "" : product.getDetail()));
        }

        viewHolderChild.txt_product_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(product.getPrice())));
        viewHolderChild.setProductItem(productItem);
        String imageUrl = product.getImageUrl();

        String imageUrlLink = ApplicationConfiguration.PRODUCT_IMAGE_URL + imageUrl;
        ImageViewUtil.loadImageByUrl(context, imageUrlLink, viewHolderChild.img_product);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        ViewHolderHead hvh = (ViewHolderHead) viewHolder;
        hvh.txt_product_category.setText(s.header);
    }

    /**
     * Convert list of ProductCategory to Section for show in sticky list
     *
     * @param data list of ProductCategory
     * @return list of sections
     */
    private List<Section> setSection(List<ProductCategory> data) {
        List<Section> sections = new ArrayList<>();
        if (data != null && !data.isEmpty()) {
            for (ProductCategory productCategory : data) {
                List<ProductItem> productItems = new ArrayList<>();
                for (Product product : productCategory.getProducts()) {
                    productItems.add(new ProductItem<>(product.getId(), product.getPrice(), product));
                }
                sections.add(new Section(productCategory.getName(), productItems));
            }
        }
        else {
            sections.add(new Section(context.getString(R.string.no_product_found), new ArrayList<>()));
        }
        return sections;
    }

    /**
     * update item amount when back from MangeBasket
     *
     * @param productItems list of product item
     */
    public void updateItemAmount(List<ProductItem> productItems, boolean isUpdated) {

        if (productItems.isEmpty()) {
            for (Section section : sections) {
                for (ProductItem productItem : section.productItems) {
                    productItem.setCount(0);
                }
            }
            this.notifyAllSectionsDataSetChanged();
            return;
        }

        for (int i = 0; i < sections.size(); i++) {
            for (int j = 0; j < sections.get(i).productItems.size(); j++) {
                int qty = 0;
                for (ProductItem item : productItems) {
                    Product product = (Product) item.getItem();
                    // update configure product
                    if (sections.get(i).productItems.get(j).getId().equals(product.getParentId())) {
                        if (isUpdated) {
                            this.sections.get(i).productItems.get(j).setCount(0);
                            qty += this.sections.get(i).productItems.get(j).getCount() + item.getCount();
                        }
                        else {
//                            qty = this.sections.get(i).productItems.get(j).getCount() + product.getCount() ;
                            qty = this.sections.get(i).productItems.get(j).getCount() + item.getCount() ;
                        }
                        this.sections.get(i).productItems.get(j).setCount(qty);
                        this.notifySectionItemChanged(i, j);
                    }
                    // update simple product
                    else if (this.sections.get(i).productItems.get(j).getId().equals(item.getId())) {

                        Product originalProduct = (Product) this.sections.get(i).productItems.get(j).getItem();
                        Product localProduct = (Product) item.getItem();

                        if (localProduct.getImageUrl() == null || localProduct.getImageUrl().isEmpty()) {
                            localProduct.setImageUrl(originalProduct.getImageUrl());
                        }

                        this.sections.get(i).productItems.set(j, item);
                        if (isUpdated) {
                            this.sections.get(i).productItems.get(j).setCount(item.getCount());
                        }
                        else {
                            if (productItemList.isEmpty()) {
                                productItemList.add(item);
                            }
                            else {
                                boolean isAdded = false;
                                for (int k = 0; k < productItemList.size(); k++) {
                                    if (productItemList.get(k).getId().equals(item.getId())) {
                                        isAdded = true;
                                    }
                                }
                                if (!isAdded) {
                                    productItemList.add(item);
                                }
//                                invokeAnimation.invokeAnimationByItems(productItemList);
                            }
                            invokeAnimation.invokeAnimationByItems(productItemList);
                        }
                        this.notifySectionItemChanged(i, j);
                    }
                }
            }
        }
    }

    /**
     *
     * @param option_progress_bar ProgressBar
     */
    public void setOption_progress_bar(ProgressBar option_progress_bar) {
        this.option_progress_bar = option_progress_bar;
    }

    /**
     *
     * @param product com.iota.eshopping.model.Product
     */
    private void fetchOptionProduct(com.planb.thespeed.model.Product product) {
        new FetchOptionProduct(product.getId(), new InvokeOnCompleteAsync<List<ConfigurationProductOption>>() {
            @Override
            public void onComplete(List<ConfigurationProductOption> configurationProductOptions) {
                if (configurationProductOptions.size() > 0) {
                    showOptionDialog(product, configurationProductOptions.get(0));
                }
                option_progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                option_progress_bar.setVisibility(View.GONE);
                LoggerHelper.showErrorLog("Error: ", e);
                Toast.makeText(context, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @param product com.iota.eshopping.model.Product
     * @see com.planb.thespeed.model.Product
     * @param configurationProductOption list of configurationProductOption
     * @see ProductOption
     */
    private void showOptionDialog(com.planb.thespeed.model.Product product, ConfigurationProductOption configurationProductOption) {
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
     * @param productItemList list of product items
     */
    public void setProductItemList(List<ProductItem> productItemList) {
        this.productItemList = productItemList;
    }

    /**
     * Item Adjustment
     */
    private void showItemAdjustment(ProductItem proItem) {
        ItemAdjustment adjustment = new ItemAdjustment();
        adjustment.setProduct(proItem, this);
        adjustment.setStore(this.store);
        if (fragmentManager != null) {
            adjustment.show(fragmentManager, "ITEM");
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
     * Class for manage header and items
     */
    private class Section {
        String header;
        List<ProductItem> productItems;

        /**
         *
         * @param header String
         * @param productItems list of ProductItem
         */
        Section(String header, List<ProductItem> productItems) {
            this.header = header;
            this.productItems = productItems;
        }
    }

    /**
     * Class for handle header in list
     */
    private class ViewHolderHead extends HeaderViewHolder {

        private TextView txt_product_category;

        /**
         * @param itemView View
         */
        ViewHolderHead(View itemView) {
            super(itemView);
            txt_product_category = itemView.findViewById(R.id.txt_product_category);
        }
    }

    /**
     * Class for handle item in list
     */
    private class ViewHolderChild extends ItemViewHolder {

        private ProductItem productItem;

        private TextView txt_number_of_item;
        private TextView txt_product_name;
        private TextView txt_product_detail;
        private TextView txt_product_price;
        private ImageView img_product;

        /**
         * @param itemView View
         */
        ViewHolderChild(final View itemView) {
            super(itemView);
            txt_number_of_item = itemView.findViewById(R.id.txt_selected_items);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_detail = itemView.findViewById(R.id.txt_product_detail);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            img_product = itemView.findViewById(R.id.img_product);

            itemView.setOnClickListener(v -> {
                if (option_progress_bar.getVisibility() != View.VISIBLE) {
                    Product product = (Product) productItem.getItem();

                    com.planb.thespeed.model.Product productForModel = new com.planb.thespeed.model.Product();
                    productForModel.setProductId(product.getId());
                    productForModel.setSku(product.getSku());
                    productForModel.setName(product.getName());
                    productForModel.setPrice(product.getPrice().doubleValue());
                    productForModel.setQty(product.getCount());
                    productForModel.setProductType(product.getProductType());
                    productForModel.setId(product.getId());

                    if (product.getProductType().equals(ConstantValue.CONFIGURABLE_PRODUCT)) {
                        option_progress_bar.setVisibility(View.VISIBLE);
                        fetchOptionProduct(productForModel);
                    } else if (product.getProductType().equals(ConstantValue.SIMPLE_PRODUCT)) {
                        showItemAdjustment(productItem);
                    }
                }
            });
        }

        /**
         * @return ProductItem
         */
        public ProductItem getProductItem() {
            return productItem;
        }

        /**
         * @param productItem ProductItem
         */
        public void setProductItem(ProductItem productItem) {
            this.productItem = productItem;
        }
    }
}
