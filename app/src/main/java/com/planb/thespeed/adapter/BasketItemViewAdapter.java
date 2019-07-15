package com.planb.thespeed.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.planb.thespeed.R;
import com.planb.thespeed.event.OnSelectItemFromBasket;
import com.planb.thespeed.fragment.cart.ItemAdjustment;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductAttributeOption;
import com.planb.thespeed.model.modelForView.ProductItem;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.security.CurrencyConfiguration;
import com.planb.thespeed.util.NumberUtils;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

/**
 * @author channarith.bong
 */
public class BasketItemViewAdapter implements ItemAdjustment.OnChangeValue {
    private List<ProductItem> productItems;
    private Context context;
    private LinearLayout layoutParent;
    private OnSelectItemFromBasket onSelectItemFromBasket;
    private FragmentManager fragmentManager;
    private ProductAttributeOption productAttributeOption;
    private Store store;


    /**
     * @param context Context
     * @param layoutParent LinearLayout
     * @param productItems list of ProductItem
     * @param fragmentManager FragmentManager
     * @param onSelectItemFromBasket OnSelectItemFromBasket
     */
    public BasketItemViewAdapter(Context context, LinearLayout layoutParent, List<ProductItem> productItems, ProductAttributeOption productAttributeOption, FragmentManager fragmentManager, OnSelectItemFromBasket onSelectItemFromBasket, Store store) {
        this.productItems = productItems;
        this.context = context;
        this.layoutParent = layoutParent;
        this.onSelectItemFromBasket = onSelectItemFromBasket;
        this.fragmentManager = fragmentManager;
        this.productAttributeOption = productAttributeOption;
        this.store = store;
    }

    /**
     * @return LinearLayout
     */
    public LinearLayout setChildViewIntoLinearLayout() {

        if (productItems.isEmpty()) {
            layoutParent.removeAllViews();
            onSelectItemFromBasket.onSelect(this.productItems);
            return layoutParent;
        }

        for (int i = 0; i < productItems.size(); i++) {
            final ProductItem productItem = productItems.get(i);
            Product product = (Product) productItem.getItem();
            View child = LayoutInflater.from(context).inflate(R.layout.view_product_item, null);
            if (productItem.getCount() > 0) {
                TextView name = child.findViewById(R.id.txt_product_item_name);
                TextView desc = child.findViewById(R.id.txt_product_item_desc);
                TextView price = child.findViewById(R.id.txt_product_item_price);
                TextView itemCount = child.findViewById(R.id.txt_selected_items);
                ImageView delete = child.findViewById(R.id.img_delete_item);
                ImageView add = child.findViewById(R.id.img_add_item);
                delete.setClickable(true);
                delete.setOnClickListener(v -> {
                    if (removeItems(productItem)) {
                        layoutParent.removeAllViews();
                        setChildViewIntoLinearLayout();
                    }
                });

                add.setClickable(true);
                add.setOnClickListener(v -> {
                    if (addItems(productItem)) {
                        layoutParent.removeAllViews();
                        setChildViewIntoLinearLayout();
                    }
                });

                child.setOnClickListener(v -> showItemAdjustment(productItem, productAttributeOption));

                name.setText(product.getName());
                desc.setText(product.getDetail());
                price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(productItem.getCountPrice())));
                itemCount.setText(String.format(Locale.ENGLISH, "x %d", productItem.getCount()));
                layoutParent.addView(child);
            } else {
                layoutParent.removeView(child);
            }
        }

        onSelectItemFromBasket.onSelect(this.productItems);
        return layoutParent;
    }

    /**
     * Remove item form exist basket
     *
     * @param productItem ProductItem
     * @return boolean
     */
    private boolean removeItems(ProductItem productItem) {
        int count = productItem.getCount();
        if (count > 0) {
            productItem.setCount(productItem.getCount() - 1);
            return true;
        }

        return false;
    }

    /**
     * Add new item into list basket
     *
     * @param productItem ProductItem
     * @return boolean
     */
    private boolean addItems(ProductItem productItem) {
        int count = productItem.getCount();
        if (count < 100) {
            productItem.setCount(productItem.getCount() + 1);
            return true;
        }
        return false;
    }

    /**
     * Item Adjustment
     */
    private void showItemAdjustment(ProductItem proItem, ProductAttributeOption productAttributeOption) {
        ItemAdjustment adjustment = new ItemAdjustment();
        adjustment.setProduct(proItem, this);
        adjustment.setStore(store);
        productAttributeOption.setOptionProducts(Observable.fromIterable(productAttributeOption.getOptionProducts()).filter(optionProduct ->
                ((Product) proItem.getItem()).getProductUid().equals(optionProduct.getProductUid())).toList().blockingGet());
        adjustment.setProductAttributeOption(productAttributeOption);

        if (fragmentManager != null) {
            adjustment.show(fragmentManager, "ITEM");
        }
    }

    @Override
    public void onChange(ProductItem productItem) {
        layoutParent.removeAllViews();
        setChildViewIntoLinearLayout();
    }
}
