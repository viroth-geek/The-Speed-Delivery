package com.iota.eshopping.fragment.cart;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.model.OptionProduct;
import com.iota.eshopping.model.ProductOption;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.model.modelForView.ProductAttributeOption;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.security.ProductLocalService;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.datahelper.datasource.offine.optionproduct.OptionProductDAO;
import com.iota.eshopping.service.datahelper.datasource.offine.productoption.ProductOptionDAO;
import com.iota.eshopping.service.datahelper.datasource.offine.productoption.ProductOptionLocalService;
import com.iota.eshopping.util.NumberUtils;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.reactivex.Observable;

/**
 * @author channarith.bong
 */

public class ItemAdjustment extends DialogFragment implements View.OnClickListener {

    private Button btn_update;
    private View outside_dialog;
    private ProductItem productItem;
    private OnChangeValue changeValue;

    private TextView txt_product_name;
    private TextView txt_product_price;
    private TextView txt_total_price;
    private TextView txt_amount;
    private ImageView img_add;
    private ImageView img_remove;

    private ProductItem productItemTemp;
    private ProductAttributeOption productAttributeOption;

    private ProductOptionDAO productOptionDAO;
    private OptionProductDAO optionProductDAO;

    private ProductLocalService productLocalService;

    private Store store;

    /**
     *
     */
    public ItemAdjustment() {
        super();
    }

    /**
     *
     * @param item ProductItem
     * @param change OnChangeValue
     */
    public void setProduct(ProductItem item, OnChangeValue change) {
        productItem = item;
        changeValue = change;
    }

    /**
     *
     * @param productAttributeOption ProductAttributeOption
     */
    public void setProductAttributeOption(ProductAttributeOption productAttributeOption) {
        this.productAttributeOption = productAttributeOption;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NkrDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_item_adjustment, container, false);
        btn_update = view.findViewById(R.id.btn_update);
        outside_dialog = view.findViewById(R.id.fragment_outside_dialog);
        View container_dialog = view.findViewById(R.id.container_dialog);
        txt_product_name = view.findViewById(R.id.txt_product_name);
        txt_product_price = view.findViewById(R.id.txt_product_price);
        txt_total_price = view.findViewById(R.id.txt_item_total_price);
        txt_amount = view.findViewById(R.id.txt_amount);
        img_add = view.findViewById(R.id.img_add);
        img_remove = view.findViewById(R.id.img_remove);
        img_add.setClickable(true);
        img_add.setOnClickListener(this);
        img_remove.setClickable(true);
        img_remove.setOnClickListener(this);
        outside_dialog.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        container_dialog.setOnClickListener(this);

        productOptionDAO = new ProductOptionDAO(getContext());
        optionProductDAO = new OptionProductDAO(getContext());

        productLocalService = new ProductLocalService(getContext());

        bindData();
        return view;
    }

    /**
     * Set data in to view
     */
    private void bindData() {
        productItemTemp = new ProductItem<>(productItem.getId(), productItem.getItem());
        productItemTemp.setUnitPrice(Float.valueOf(NumberUtils.strMoney(productItem.getUnitPrice())));

        if (productItem.getCount() == 0) {
            productItemTemp.setCount(productItem.getCount() + 1);
        } else {
            productItemTemp.setCount(productItem.getCount());
        }

        setView(productItemTemp);
    }

    /**
     *
     */
    private void setView(ProductItem productItem) {
        Product product = (Product) productItemTemp.getItem();
        txt_product_name.setText(product.getName());
        txt_total_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(productItem.getCountPrice())));
        txt_product_price.setText(String.format(Locale.ENGLISH, "$%.2f", productItem.getUnitPrice()));
        txt_amount.setText(String.format(Locale.ENGLISH, "%d", productItemTemp.getCount()));
    }

    @Override
    public void onClick(View view) {
        if (outside_dialog.equals(view)) {
            view.setVisibility(View.GONE);
            dismiss();
        } else if (btn_update.equals(view)) {



            if (store != null) {
                List<Product> wrongProducts = productLocalService.getListItem();
                wrongProducts = Observable.fromIterable(wrongProducts).filter(wrongProduct -> wrongProduct.getStoreId() != null && !wrongProduct.getStoreId().equals(store.getId())).toList().blockingGet();
                for (com.iota.eshopping.model.modelForView.Product wrongProduct : wrongProducts) {
                    productLocalService.deleteWhereProductUid(wrongProduct.getProductUid());
                    productOptionDAO.deleteWhereProductUid(wrongProduct.getProductUid());
                    optionProductDAO.deleteWhereProductUid(wrongProduct.getProductUid());
                }
            }

            Product product = (Product) productItemTemp.getItem();

            productLocalService.deleteWhereProductUid(product.getProductUid());

            String productUUID = UUID.randomUUID().toString();

            if (productAttributeOption != null) {
                for (ProductOption productOption : productAttributeOption.getProductOptions()) {
                    productOption.setProductUid(productUUID);
                    productOptionDAO.save(productOption);
                }

                for (OptionProduct optionProduct : productAttributeOption.getOptionProducts()) {
                    optionProduct.setProductUid(productUUID);
                    optionProductDAO.save(optionProduct);
                }
            }

            product.setStoreId(store.getId());
            product.setCount(productItemTemp.getCount());
            product.setProductUid(productUUID);

            productLocalService.insertItem(product);

            productItem.setCount(productItemTemp.getCount());
            productItem.setItem(product);
            changeValue.onChange(productItem);

            view.setVisibility(View.GONE);
            dismiss();

        } else if (img_remove.equals(view)) {
            if (productItemTemp.getCount() > 1) {
                productItemTemp.setCount(productItemTemp.getCount() - 1);
                txt_amount.setText(String.format(Locale.ENGLISH, "%d", productItemTemp.getCount()));
                setView(productItemTemp);
            }
        } else if (img_add.equals(view)) {
            if (productItemTemp.getCount() < ApplicationConfiguration.MAX_ITEM_ALLOW) {
                productItemTemp.setCount(productItemTemp.getCount() + 1);
                txt_amount.setText(String.format(Locale.ENGLISH, "%d", productItemTemp.getCount()));
                setView(productItemTemp);
            }
            txt_amount.setText(String.format(Locale.ENGLISH, "%d", productItemTemp.getCount()));
        }
    }

    /**
     * Setter store
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     *
     */
    public interface OnChangeValue {
        void onChange(ProductItem productItem);
    }
}
