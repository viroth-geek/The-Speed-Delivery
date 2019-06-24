package com.planb.thespeed.fragment.productoption;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.ProductOptionRecyclerAdapter;
import com.planb.thespeed.adapter.StickyHeaderLayoutManager;
import com.planb.thespeed.model.Attribute;
import com.planb.thespeed.model.ConfigurationProductOption;
import com.planb.thespeed.model.Option;
import com.planb.thespeed.model.OptionProduct;
import com.planb.thespeed.model.OptionValue;
import com.planb.thespeed.model.Product;
import com.planb.thespeed.model.ProductOption;
import com.planb.thespeed.model.ProductOptionBody;
import com.planb.thespeed.model.modelForView.ProductAttributeOption;
import com.planb.thespeed.service.datahelper.datasource.online.FetchConfigurableProduct;
import com.planb.thespeed.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

/**
 * @author yeakleang.ay
 */
public class ProductOptionDialog extends DialogFragment implements View.OnClickListener, ProductOptionRecyclerAdapter.OnAttributeOptionClickListener {

    private TextView txtQty;
    private ProgressBar getItemProgressBar;
    private OnProductOptionChooseListener onOptionItemChooseListener;
    private ConfigurationProductOption configurationProductOption;
    private ProductAttributeOption currentSelectedProductAttributeOption;
    private ProductOptionRecyclerAdapter productOptionRecyclerAdapter;
    private Product parentProduct;

    private Long storeId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_popup_product_option, container, false);

        RecyclerView productOptionRecyclerView = view.findViewById(R.id.product_option_recycler_view);

        productOptionRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        TextView txtProductTitle = view.findViewById(R.id.product_option_title);

        TextView txtProductSubTitle = view.findViewById(R.id.product_option_sub_title);

        txtQty = view.findViewById(R.id.txt_product_qty);

        ImageButton btnAdd = view.findViewById(R.id.btn_increase_item);

        ImageButton btnRemove = view.findViewById(R.id.btn_decrease_item);

        Button btnAddItem = view.findViewById(R.id.btn_add_item);

        getItemProgressBar = view.findViewById(R.id.get_item_progress_bar);

        productOptionRecyclerView.setHasFixedSize(true);

        currentSelectedProductAttributeOption = new ProductAttributeOption();

        productOptionRecyclerAdapter = new ProductOptionRecyclerAdapter(getContext(), configurationProductOption);
        productOptionRecyclerAdapter.setOnAttributeOptionClickListener(this);

        StickyHeaderLayoutManager stickyHeaderLayoutManager = new StickyHeaderLayoutManager();

        stickyHeaderLayoutManager.setItemPrefetchEnabled(false);

        productOptionRecyclerView.setLayoutManager(stickyHeaderLayoutManager);

        productOptionRecyclerView.setAdapter(productOptionRecyclerAdapter);

        productOptionRecyclerAdapter.notifyAllSectionsDataSetChanged();

        btnAdd.setOnClickListener(this);

        btnRemove.setOnClickListener(this);

        btnAddItem.setOnClickListener(this);

        txtProductTitle.setText(String.format("Options for %s", parentProduct.getName()));
        txtProductSubTitle.setText(parentProduct.getSku());

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_increase_item:
                txtQty.setText(String.format(Locale.ENGLISH, "%d", increaseValue(Integer.valueOf(txtQty.getText().toString()))));
                break;
            case R.id.btn_decrease_item:
                txtQty.setText(String.format(Locale.ENGLISH, "%d", decreaseValue(Integer.valueOf(txtQty.getText().toString()))));
                break;
            case R.id.btn_add_item:
                if (getItemProgressBar.getVisibility() != View.VISIBLE) {
                    if (isMeetRequirement()) {
                        getConfigurableProduct();
                    }
                    else {
                        Toast.makeText(getContext(), "Please select required field(*).", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * check requirement
     * @return boolean
     */
    private boolean isMeetRequirement() {

        int requirementProductOptionCount = 0;
        int hasRequirementProductOptionCount = 0;

        int requirementOptionProductCount = 0;
        int hasRequirementOptionProductCount = 0;

        // check requirement product option
        for (ProductOption productOption : configurationProductOption.getExtensionAttribute().getProductOptions()) {
            requirementProductOptionCount++;
            if (!currentSelectedProductAttributeOption.getProductOptions().isEmpty()) {
                List<ProductOption> currentSelectedProductOptions = Observable.fromIterable(currentSelectedProductAttributeOption.getProductOptions()).filter(productOptions -> !productOptions.getOptions().isEmpty()).toList().blockingGet();
                for (ProductOption selectedProductOption : currentSelectedProductOptions) {
                    if (productOption.getAttributeId().equals(selectedProductOption.getAttributeId())) {
                        hasRequirementProductOptionCount++;
                    }
                }
            }
            else {
                LoggerHelper.showDebugLog("Please select " + productOption.getLabel());
            }
        }
        // check requirement option product
        for (OptionProduct optionProduct : configurationProductOption.getOptionProducts()) {
            if (optionProduct.getRequired() && !currentSelectedProductAttributeOption.getOptionProducts().isEmpty()) {
                requirementOptionProductCount++;
                for (OptionProduct selectedOptionProduct : currentSelectedProductAttributeOption.getOptionProducts()) {
                    if (optionProduct.getOptionId().equals(selectedOptionProduct.getOptionId())) {
                        hasRequirementOptionProductCount++;
                    }
                    else {
                        LoggerHelper.showDebugLog("Please select " + optionProduct.getTitle());
                    }
                }
            }
            else if (optionProduct.getRequired()) {
                requirementOptionProductCount++;
                LoggerHelper.showDebugLog("Please select " + optionProduct.getTitle());
            }
        }

        return requirementProductOptionCount == hasRequirementProductOptionCount && requirementOptionProductCount == hasRequirementOptionProductCount;
    }

    /**
     *
     */
    private void getConfigurableProduct() {
        if (prepareForGetProduct() == null) {
            return;
        }
        getItemProgressBar.setVisibility(View.VISIBLE);
        new FetchConfigurableProduct(prepareForGetProduct(), new FetchConfigurableProduct.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<Product> products) {
                if (products == null) {
                    Toast.makeText(getContext(), "No product found.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Product product = products.get(0);
                product.setParentId(parentProduct.getId());
                product.setQty(Integer.valueOf(txtQty.getText().toString()));

//                currentSelectedProductAttributeOption = productOptionRecyclerAdapter.currentSelectedProductAttributeOption();
                for (ProductOption productOption : currentSelectedProductAttributeOption.getProductOptions()) {
                    productOption.setOriginalProductId(product.getProductId());
                }
                for (OptionProduct optionProduct : currentSelectedProductAttributeOption.getOptionProducts()) {
                    for (OptionValue optionValue : optionProduct.getOptionValues()) {
                        product.setPrice(product.getPrice() + optionValue.getPrice());
                    }
                    optionProduct.setOriginalProductId(product.getProductId());
                }
                
                onOptionItemChooseListener.onOptionChoose(product, currentSelectedProductAttributeOption);
                getItemProgressBar.setVisibility(View.GONE);
                dismiss();
            }

            @Override
            public void onError(Throwable e) {
                getItemProgressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @return ProductOptionBody
     */
    private ProductOptionBody prepareForGetProduct() {
        ProductOptionBody productOptionBody = new ProductOptionBody();
//        Customer customer = new UserAccount(getContext()).getCustomer();
//
//        if (customer == null) {
//            Toast.makeText(getContext(), "Please login to continue!", Toast.LENGTH_SHORT).show();
//            return null;
//        }

//        productOptionBody.setEmail(customer.getEmail());
        productOptionBody.setProductId(parentProduct.getId());
//        productOptionBody.setQty(Long.valueOf(txtQty.getText().toString()));
//        productOptionBody.setStoreId(storeId);

        List<Attribute> optionBodies = new ArrayList<>();
        List<ProductOption> productOptions = currentSelectedProductAttributeOption.getProductOptions();
        for (ProductOption productOption : productOptions) {
            for (Option option : productOption.getOptions()) {
                optionBodies.add(new Attribute(Long.valueOf(productOption.getAttributeId()), option.getValueIndex()));
            }
        }
        productOptionBody.setOptions(optionBodies);

        return productOptionBody;
    }

    /**
     *
     * @param onOptionItemChooseListener OnProductOptionChooseListener
     */
    public void setOnOptionItemChooseListener(OnProductOptionChooseListener onOptionItemChooseListener) {
        this.onOptionItemChooseListener = onOptionItemChooseListener;
    }

    /**
     *
     * @param configurationProductOption list of ConfigurationProductOption
     */
    public void setConfigurationProductOption(ConfigurationProductOption configurationProductOption) {
        this.configurationProductOption = configurationProductOption;
    }

    /**
     *
     * @param currentSelectedProductAttributeOption ProductAttributeOption
     */
    public void setCurrentSelectedProductAttributeOption(ProductAttributeOption currentSelectedProductAttributeOption) {
        this.currentSelectedProductAttributeOption = currentSelectedProductAttributeOption;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /**
     *
     * @param parentProduct Product
     */
    public void setParentProduct(Product parentProduct) {
        this.parentProduct = parentProduct;
    }

    /**
     *
     * @return int
     */
    private int increaseValue(int currentValue) {
        if (currentValue > 100) {
            return currentValue;
        }
        return currentValue + 1;
    }

    /**
     *
     * @return int
     */
    private int decreaseValue(int currentValue) {
        if (currentValue <= 1) {
            return currentValue;
        }
        return currentValue - 1;
    }

    @Override
    public void onAttributeOptionClick(ProductAttributeOption productAttributeOption) {
        setCurrentSelectedProductAttributeOption(productAttributeOption);
    }

    /**
     *
     */
    public interface OnProductOptionChooseListener {
        void onOptionChoose(Product product, ProductAttributeOption productAttributeOption);
    }
}
