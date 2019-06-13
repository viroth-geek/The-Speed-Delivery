package com.iota.eshopping.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.BasketItemViewAdapter;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.event.OnSelectItemFromBasket;
import com.iota.eshopping.fragment.time.NkrTimePicker;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.OptionProduct;
import com.iota.eshopping.model.OptionValue;
import com.iota.eshopping.model.ProductOption;
import com.iota.eshopping.model.ServerDateTime;
import com.iota.eshopping.model.StoreDeliveryFee;
import com.iota.eshopping.model.enumeration.DayType;
import com.iota.eshopping.model.form.FormForGetDeliveryFee;
import com.iota.eshopping.model.magento.addToCart.CartAttribute;
import com.iota.eshopping.model.magento.addToCart.CartItemsRequest;
import com.iota.eshopping.model.magento.addToCart.CartOption;
import com.iota.eshopping.model.magento.addToCart.CartProductItems;
import com.iota.eshopping.model.magento.addToCart.ResponseAddToCart;
import com.iota.eshopping.model.magento.store.StoreFee;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.model.modelForView.ProductAttributeOption;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.security.ProductLocalService;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.offine.optionproduct.OptionProductDAO;
import com.iota.eshopping.service.datahelper.datasource.offine.productoption.ProductOptionDAO;
import com.iota.eshopping.service.datahelper.datasource.online.AddMultiItemsToCart;
import com.iota.eshopping.service.datahelper.datasource.online.CalculateServiceFee;
import com.iota.eshopping.service.datahelper.datasource.online.FetchDeliveryFee;
import com.iota.eshopping.service.datahelper.datasource.online.FetchServerDateTime;
import com.iota.eshopping.util.AlertUtils;
import com.iota.eshopping.util.AuthUtils;
import com.iota.eshopping.util.DateUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NumberUtils;
import com.iota.eshopping.util.preference.LocationPreference;
import com.iota.eshopping.util.preference.StorePreference;
import com.iota.eshopping.util.preference.TimeDeliveryPreference;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author channarith.bong
 */
public class ManageBasketActivity extends AppCompatActivity implements View.OnClickListener, OnSelectItemFromBasket {

    private Button btn_go_to_checkout;
    private LinearLayout list_basket_items;

    private TextView txtSubTotal;
    private TextView txtTotalItem;

    private TextView txt_bk_change;
    private TextView txt_bk_change_time;
    private TextView txt_estore_name;

    private BasketItemViewAdapter listOfLinearLayout;
    private static List<ProductItem> productItems;
    private static List<ProductItem> itemsUpdated;
    private String[] timeSelectionToday;
    private FragmentManager fragmentManager;

    private UserAccount userAccount;
    private View container_float_loading;

    private Store store;
    private float itemAmount = 0f;
    private Float deliveryFee = 0f;
    private static int ATTEMPT_COUNT = 0;
    private static int MAX_ATTEMPT = 2;

    private Map<Product, List<OptionValue>> productListHashMap = new HashMap<>();
    private ProductAttributeOption productAttributeOption;
    private ProductLocalService productLocalService;

    private ProductOptionDAO productOptionDAO;
    private OptionProductDAO optionProductDAO;

    private String serverDateTime = DateUtil.getNowAdd45Mn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_basket);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btn_go_to_checkout = findViewById(R.id.btn_place_order);
        btn_go_to_checkout.setOnClickListener(this);
        list_basket_items = findViewById(R.id.list_bk_product_item);
        txt_bk_change = findViewById(R.id.txt_bk_change);
        txt_estore_name = findViewById(R.id.txt_estore_name);

        txtSubTotal = findViewById(R.id.txt_sub_total);
        txtTotalItem = findViewById(R.id.txt_total_item_count);
        
        txt_estore_name.setOnClickListener(this);
        txt_bk_change.setOnClickListener(this);
        
        container_float_loading = findViewById(R.id.container_float_loading);
        txt_bk_change_time = findViewById(R.id.txt_bk_change_time);

        timeSelectionToday = getResources().getStringArray(R.array.static_deliver_time_today);

        String timeDelivery = TimeDeliveryPreference.getTimeDeliveryText(this);
        if (timeDelivery != null && !timeDelivery.isEmpty()) {
            txt_bk_change_time.setText(timeDelivery);
        }

        productLocalService = new ProductLocalService(this);
        productOptionDAO = new ProductOptionDAO(this);
        optionProductDAO = new OptionProductDAO(this);

        fetchServerDateTime();

        bindData();
    }

    @Override
    public void onClick(View v) {
        if (txt_bk_change.equals(v)) {
            showTimePicker();
        }
        else if (btn_go_to_checkout.equals(v)) {
            List<ProductItem> items = Observable.fromIterable(itemsUpdated).filter(productItem -> productItem.getCount() > 0).toList().blockingGet();
            if (items != null && !items.isEmpty()) {
                boolean isCanOrder = true;
                String tomorrowText = TimeDeliveryPreference.getTimeDeliveryText(this).split(" ")[0];
                if (tomorrowText.equalsIgnoreCase(DayType.TOMORROW.toString())) {
                    if (!store.isStatusOpenTomorrow()) {
                        isCanOrder = false;
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Store Message");
                        builder.setMessage(store.getNameKh().isEmpty() ? store.getName() + " is not opened tomorrow." : store.getNameKh() + " is not opened tomorrow.");
                        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                        builder.create().show();
                    }
                }
                else {
                    if (!store.isOpenToday()) {
                        isCanOrder = false;
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Store Message");
                        builder.setMessage(store.getNameKh().isEmpty() ? store.getName() + " is not open." : store.getNameKh() + " is not open.");
                        builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
                        builder.create().show();
                    }
                }

                if (isCanOrder) {
                    if (userAccount.checkIsReadyLogged()) {
                        if (TimeDeliveryPreference.getTimeDelivery(this) != null) {
                            prepareBeforeCheckout();
                        } else {
                            Toast.makeText(this, "Please choose time delivery.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

            } else {
                Toast.makeText(this, "No items to order", Toast.LENGTH_SHORT).show();
            }
        }
        else if (txt_estore_name.equals(v)) {
            Intent intent = new Intent(this, StoreActivity.class);
            intent.putExtra(ConstantValue.STORE, store);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_basket, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            sendDataToBack();
        } else if (R.id.action_clear_basket == item.getItemId()) {
            for (int i = 0; i < productItems.size(); i++) {
                productItems.get(i).setCount(0);
            }
            getTotalValue(productItems);

            productItems.clear();

            productOptionDAO.deleteAll();
            optionProductDAO.deleteAll();

            ProductLocalService productLocalService = new ProductLocalService(this);
            productLocalService.deleteAll();

            listOfLinearLayout = new BasketItemViewAdapter(this, list_basket_items, productItems, productAttributeOption, fragmentManager, this, store);
            list_basket_items = listOfLinearLayout.setChildViewIntoLinearLayout();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * fetch server date time
     */
    private void fetchServerDateTime() {
        new FetchServerDateTime(new InvokeOnCompleteAsync<List<ServerDateTime>>() {
            @Override
            public void onComplete(List<ServerDateTime> serverDateTimes) {
                if (serverDateTimes != null && !serverDateTimes.isEmpty()) {
                    ServerDateTime serverDateTime = serverDateTimes.get(0);
                    String dateTime = serverDateTime.getDate() + " " + serverDateTime.getTime();
                    setServerDateTime(dateTime);
                }
                else {
                    setServerDateTime(DateUtil.getNowAdd45Mn());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    if (ATTEMPT_COUNT < MAX_ATTEMPT) {
                        fetchServerDateTime();
                        ATTEMPT_COUNT++;
                    }
                    else {
                        setServerDateTime(DateUtil.getNowAdd45Mn());
                    }
                }
                else {
                    setServerDateTime(DateUtil.getNowAdd45Mn());
                }
            }
        });
    }

    /**
     * send data to store activity when go back
     */
    private void sendDataToBack() {
        Intent intent = new Intent();
        intent.putExtra(ConstantValue.PRODUCT_ITEMS, new ArrayList<>(productItems));
        setResult(ConstantValue.GO_TO_BASKET, intent);
//        clearBasket();
        finish();
    }

    @Override
    public void onBackPressed() {
        sendDataToBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantValue.HOME_CALLING_CODE) {
            finish();
        } else if (ConstantValue.INTENT_ACTIVITY_TAG_CODE == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                if (data.hasExtra(ConstantValue.ADDRESS)) {

                }
                    android.location.Address addressMap = (android.location.Address) data.getExtras().get(ConstantValue.ADDRESS);

                    if (addressMap != null) {
                        Address address = new Address();
                        address.setCountryId(addressMap.getCountryCode());
                        address.setCity(addressMap.getLocality());
                        address.setPostcode(addressMap.getPostalCode() == null ? ConstantValue.POST_CODE : addressMap.getPostalCode());
                        address.setAddressLine(addressMap.getAddressLine(0));
                        List<String> streets = new ArrayList<>();
                        streets.add(addressMap.getAddressLine(0));
                        address.setStreet(streets);
                        address.setLatitude(addressMap.getLatitude());
                        address.setLongitude(addressMap.getLongitude());

                    fetchDeliveryFee(prepareForGetDeliveryFee(address));
                }
            }
        }
    }

    @Override
    public void onSelect(List<ProductItem> productItems) {
        getTotalValue(productItems);

        productLocalService.deleteAll();
        // save cached product
        for (ProductItem productItem : productItems) {
            com.iota.eshopping.model.modelForView.Product product = (com.iota.eshopping.model.modelForView.Product) productItem.getItem();
            product.setCount(productItem.getCount());
            product.setStoreId(this.store.getId());
            productLocalService.insertItem(product);
        }
    }

    /**
     * Calculate Item selected price, amounts
     *
     * @param productItems list of ProductItem
     */
    private void getTotalValue(List<ProductItem> productItems) {

        itemsUpdated = productItems;

        itemAmount = 0f;
        int itemCount = 0;

        for (ProductItem productItem : productItems) {
            itemCount += productItem.getCount();
            for (int i = 0; i < productItem.getCount(); i++) {
                itemAmount += Float.valueOf(NumberUtils.strMoney(productItem.getUnitPrice()));
            }
        }

        calculateServiceFee(store.getId(), itemAmount);

        txtSubTotal.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(itemAmount)));
        txtTotalItem.setText(String.valueOf(itemCount));

//        txt_item_service_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(serviceFee)));
//        txt_item_total.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(itemTotal)));
    }

    /**
     * Show time picker
     */
    private void showTimePicker() {
        NkrTimePicker timePicker = new NkrTimePicker();
        timePicker.setTimeChangeListener(time -> {
            if (time != null) {
                LoggerHelper.showDebugLog("Time: " + time);
                switch (time.first) {
                    case TODAY:
                        if (timeSelectionToday[0].equals(time.second)) {
                            txt_bk_change_time.setText(String.format("Deliver %s", time.second.toLowerCase()));
                            TimeDeliveryPreference.saveTimeDelivery(this, DateUtil.formatDateTimeFromServerAndAdd45Mn(serverDateTime));
                            TimeDeliveryPreference.saveTimeDeliveryText(this, "Deliver " + time.second.toLowerCase());
                        } else {
                            txt_bk_change_time.setText(String.format("Today %s", time.second));
                            TimeDeliveryPreference.saveTimeDelivery(ManageBasketActivity.this, DateUtil.getToday() + " " + time.second);
                            TimeDeliveryPreference.saveTimeDeliveryText(ManageBasketActivity.this, "Today " + time.second);
                        }
                        break;
                    case TOMORROW:
                        txt_bk_change_time.setText(String.format("Tomorrow %s", time.second));
                        TimeDeliveryPreference.saveTimeDelivery(ManageBasketActivity.this, DateUtil.getTomorrow() + " " + time.second);
                        TimeDeliveryPreference.saveTimeDeliveryText(ManageBasketActivity.this, "Tomorrow " + time.second);
                        break;
                }
            }
        });
        timePicker.show(fragmentManager, "TIME");
    }

    /**
     * Bind data from server
     */
    private void bindData() {
        userAccount = new UserAccount(this);

        store = (Store) getIntent().getSerializableExtra(ConstantValue.STORE);
        txt_estore_name.setText(store.getName());

        fetchDeliveryFee(prepareForGetDeliveryFee(null));

        if (getIntent().hasExtra(ConstantValue.PRODUCT_ATTRIBUTE_OPTION)) {
            productAttributeOption = (ProductAttributeOption) getIntent().getSerializableExtra(ConstantValue.PRODUCT_ATTRIBUTE_OPTION);
        }
        else {
            productAttributeOption = new ProductAttributeOption();
            productAttributeOption.setProductOptions(productOptionDAO.getProductOptions());
            productAttributeOption.setOptionProducts(optionProductDAO.getOptionProducts());
        }

        productAttributeOption.setOptionProducts(Observable.fromIterable(productAttributeOption.getOptionProducts()).filter(optionProduct -> !optionProduct.getOptionValues().isEmpty()).toList().blockingGet());
        arrangeOptionProduct();

        productItems = (List<ProductItem>) getIntent().getSerializableExtra(ConstantValue.ITEMS);
        itemsUpdated = productItems;

        bindProductDetail(true);

//        listOfLinearLayout = new BasketItemViewAdapter(this, list_basket_items, productItems, productAttributeOption, fragmentManager, this);
//        list_basket_items = listOfLinearLayout.setChildViewIntoLinearLayout();
    }

    /**
     * arrange option product
     */
    private void arrangeOptionProduct() {
        List<OptionProduct> optionProducts = new ArrayList<>();
        for (OptionProduct optionProduct : productAttributeOption.getOptionProducts()) {
            if (optionProducts.isEmpty()) {
                optionProducts.add(optionProduct);
            }
            else {
                boolean isExist = false;
                for (OptionProduct optionProduct1 : optionProducts) {
                    if (optionProduct.getOptionId().equals(optionProduct1.getOptionId()) && optionProduct.getProductUid().equals(optionProduct1.getProductUid())) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    optionProducts.add(optionProduct);
                }
                else {
                    for (OptionProduct optionProduct1 : optionProducts) {
                        if (optionProduct.getOptionId().equals(optionProduct1.getOptionId()) && optionProduct.getProductUid().equals(optionProduct1.getProductUid())) {
                            List<OptionValue> optionValueList = new ArrayList<>();
                            for (OptionValue optionValue : optionProduct.getOptionValues()) {
                                boolean isExist1 = false;
                                for (OptionValue optionValue2 : optionValueList) {
                                    if (optionValue2.getOptionTypeId().equals(optionValue.getOptionTypeId())) {
                                        isExist1 = true;
                                    }
                                }
                                if (!isExist1) {
                                    optionValueList.add(optionValue);
                                }
                            }
                            optionProduct1.getOptionValues().addAll(optionValueList);
                        }
                    }
                }
            }
        }

        productAttributeOption.setOptionProducts(optionProducts);
    }

    /**
     * fetch cart local service
     */
    private void fetchProductFromLocalCart() {
        List<com.iota.eshopping.model.modelForView.Product> products = productLocalService.getListItem();
        products = Observable.fromIterable(products).filter(product -> product.getCount() > 0 && product.getStoreId() != null && product.getStoreId().equals(store.getId())).toList().blockingGet();

        if (products.isEmpty()) {
            return;
        }

        productItems = new ArrayList<>();
        for (com.iota.eshopping.model.modelForView.Product product : products) {
            productItems.add(new ProductItem<>(product.getId(), product.getCount(), product.getPrice(), product));
        }
        itemsUpdated = productItems;

        productAttributeOption.setProductOptions(productOptionDAO.getProductOptions());
        productAttributeOption.setOptionProducts(optionProductDAO.getOptionProducts());
        productAttributeOption.setOptionProducts(Observable.fromIterable(productAttributeOption.getOptionProducts()).filter(optionProduct -> !optionProduct.getOptionValues().isEmpty()).toList().blockingGet());

        arrangeOptionProduct();
        bindProductDetail(false);
    }

    /**
     * Get all select items from view and request to server
     */
    private void prepareBeforeCheckout() {

        container_float_loading.setVisibility(View.VISIBLE);
        fetchProductFromLocalCart();

        if (itemsUpdated != null) {
            if (itemsUpdated.size() > 0) {
                List<CartProductItems> cartProductItems = new ArrayList<>();
                for (ProductItem productItem : productItems) {
                    Product product = (Product) productItem.getItem();
                    if (product.getParentId() == null || product.getParentId() == 0) {
                        CartProductItems items = new CartProductItems();
                        items.setProductId(productItem.getId());
                        items.setQty(Long.valueOf(productItem.getCount()));
                        cartProductItems.add(items);
                    }
                    else {
                        CartProductItems items = new CartProductItems();
                        items.setProductId(product.getParentId());
                        items.setQty(Long.valueOf(productItem.getCount()));
                        if (productAttributeOption != null) {
                            if (productAttributeOption.getProductOptions() != null && !productAttributeOption.getProductOptions().isEmpty()) {
                                List<ProductOption> productOptions = productAttributeOption.getProductOptions();
                                List<CartAttribute> cartAttributes = new ArrayList<>();
                                for (ProductOption productOption : productOptions) {
//                                    if (productOption.getProductId().equals(product.getParentId()) && productOption.getOriginalProductId().equals(product.getId())) {
                                    if (productOption.getProductUid().equals(product.getProductUid())) {
                                        CartAttribute cartAttribute = new CartAttribute();
                                        cartAttribute.setAttributeId(Long.valueOf(productOption.getAttributeId()));
                                        cartAttribute.setValueIndex(productOption.getOptions().get(0).getValueIndex());
                                        cartAttributes.add(cartAttribute);
                                    }
                                }
                                items.setCartAttributes(cartAttributes);
                            }
                            else {
                                items.setCartAttributes(new ArrayList<>());
                            }
                            if (productAttributeOption.getOptionProducts() != null && !productAttributeOption.getOptionProducts().isEmpty()) {
                                List<OptionProduct> optionProducts = productAttributeOption.getOptionProducts();
                                List<CartOption> cartOptions = new ArrayList<>();
                                if (optionProducts != null && !optionProducts.isEmpty()) {
                                    for (OptionProduct optionProduct : optionProducts) {
//                                        if (optionProduct.getProductId().equals(product.getParentId()) && optionProduct.getOriginalProductId().equals(product.getId())) {
                                        if (optionProduct.getProductUid().equals(product.getProductUid())) {
                                            List<Long> optionsTypeIds = new ArrayList<>();
                                            for (OptionValue optionValue : optionProduct.getOptionValues()) {
                                                boolean isExist = false;
                                                for (Long id : optionsTypeIds) {
                                                    if (id.equals(optionValue.getOptionTypeId())) {
                                                        isExist = true;
                                                    }
                                                }
                                                if (!isExist) {
                                                    optionsTypeIds.add(optionValue.getOptionTypeId());
                                                }
                                            }
                                            if (!optionsTypeIds.isEmpty()) {
                                                CartOption cartOption = new CartOption();
                                                cartOption.setOptionId(optionProduct.getOptionId());
                                                cartOption.setOptionTypeIds(optionsTypeIds);
                                                cartOptions.add(cartOption);
                                            }
                                        }
                                    }
                                }
                                items.setCartOptions(cartOptions);
                            }
                            else {
                                items.setCartOptions(new ArrayList<>());
                            }
                        }
                        cartProductItems.add(items);
                    }
                }

                CartItemsRequest cartItemRequest = prepareCart();
                cartItemRequest.setItems(cartProductItems);

                processAddToCart(cartItemRequest);
            } else {
                Toast.makeText(this, "No item to order.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * bind product to detail to show option
     */
    private void bindProductDetail(boolean shouldFilter) {
        int countOption = 0;
        for (ProductItem productItem : itemsUpdated) {
            Product product = (Product) productItem.getItem();
            StringBuilder detailBuilder = new StringBuilder();
            countOption++;
            if (!productAttributeOption.getOptionProducts().isEmpty()) {
                for (OptionProduct optionProduct : productAttributeOption.getOptionProducts()) {
                    if (!optionProduct.getOptionValues().isEmpty()) {
                        if (product.getProductUid().equals(optionProduct.getProductUid())) {
                            detailBuilder.append(optionProduct.getTitle())
                                    .append(":\n");
                            int countValue = 0;
                            for (OptionValue optionValue : optionProduct.getOptionValues()) {
                                countValue++;
                                detailBuilder
                                        .append("\t\t")
                                        .append(optionValue.getTitle())
                                        .append(": $").append(optionValue.getPrice());
                                if (countValue < optionProduct.getOptionValues().size()) {
                                    detailBuilder.append("\n");
                                }
                            }
                            if (countOption <= productAttributeOption.getOptionProducts().size()) {
                                detailBuilder.append("\n");
                            } else {
                                countOption = 0;
                            }
                        }
                    }
                }
            }
            addProductWithOptionToMap(product, productAttributeOption);
            product.setDetail(detailBuilder.toString());
        }
        if (shouldFilter) {
            filterProduct();
        }
    }

    /**
     * filter same product with option
     * @param product Product
     * @param productAttributeOption ProductAttributeOption
     */
    private void addProductWithOptionToMap(Product product, ProductAttributeOption productAttributeOption) {
        if (product.getProductType() == null) {
            if (product.getParentId() != null || product.getParentId() != 0L) {
                product.setProductType(ConstantValue.CONFIGURABLE_PRODUCT);
            } else {
                product.setProductType(ConstantValue.SIMPLE_PRODUCT);
            }
        }

        if (product.getProductType().equals(ConstantValue.SIMPLE_PRODUCT)) {
            productListHashMap.put(product, new ArrayList<>());
        }
        else {
            if (productAttributeOption.getOptionProducts().isEmpty()) {
                productListHashMap.put(product, new ArrayList<>());
            }
            else {
                List<OptionProduct> list = Observable.fromIterable(productAttributeOption.getOptionProducts()).filter(optionProduct -> optionProduct.getProductUid().equals(product.getProductUid())).toList().blockingGet();
                if (list.isEmpty()) {
                    productListHashMap.put(product, new ArrayList<>());
                }
                else {
                    for (OptionProduct optionProduct : productAttributeOption.getOptionProducts()) {
                        if (optionProduct.getProductUid().equals(product.getProductUid())) {
                            List<OptionValue> optionValues = Observable.fromIterable(optionProduct.getOptionValues()).filter(optionValue -> optionValue.getProductUid().equals(product.getProductUid())).sorted((optionValue, t1) -> optionValue.getOptionTypeId().compareTo(t1.getOptionTypeId())).toList().blockingGet();
                            optionProduct.setOptionValues(optionValues);
                            productListHashMap.put(product, optionProduct.getOptionValues());
                        }
                    }
                }
            }
        }
    }

    /**
     * filter same product
     */
    private void filterProduct() {
        Map<Product, List<OptionValue>> productListMap = new HashMap<>();
        List<Long> previousValue = new ArrayList<>();
        Product previousProduct = null;
        for (Map.Entry<Product, List<OptionValue>> myMap : productListHashMap.entrySet()) {
            if (myMap.getKey().getProductType().equals(ConstantValue.SIMPLE_PRODUCT)) {
                productListMap.put(myMap.getKey(), myMap.getValue());
            }
            else {
                Product currentProduct = null;
                List<Long> currentValue = new ArrayList<>();

                boolean isExist = true;
                for (Map.Entry<Product, List<OptionValue>> ignored : productListHashMap.entrySet()) {
                    currentValue.clear();
                    for (OptionValue optionValue : myMap.getValue()) {
                        currentValue.add(optionValue.getOptionTypeId());
                    }

                    if ((previousProduct != null && !myMap.getKey().getId().equals(previousProduct.getId()) && previousValue.equals(currentValue)) || !previousValue.equals(currentValue)) {
                        isExist = false;
                    }
                    else if ((previousProduct != null && myMap.getKey().getId().equals(previousProduct.getId()) && previousValue.equals(currentValue))) {
                        currentProduct = previousProduct;
                    }
                    else if (previousProduct == null) {
                        isExist = false;
                    }
                }

                if (!isExist) {
                    productListMap.put(myMap.getKey(), myMap.getValue());
                }
                else {
                    if (currentProduct != null) {
                        Product product = myMap.getKey();
                        product.setCount(product.getCount() + previousProduct.getCount());
                        productListMap.remove(previousProduct);
                        productListMap.put(product, myMap.getValue());
                    }
                }

                previousProduct = myMap.getKey();
                previousValue = currentValue;
            }
        }

        productItems.clear();
        for(Map.Entry<Product, List<OptionValue>> map : productListMap.entrySet()) {
            Product product = map.getKey();
            productItems.add(new ProductItem<>(product.getId(), product.getCount(), product.getPrice(), product));
        }

        listOfLinearLayout = new BasketItemViewAdapter(this, list_basket_items, productItems, productAttributeOption, fragmentManager, this, store);
        list_basket_items = listOfLinearLayout.setChildViewIntoLinearLayout();
    }

    /**
     * go checkout
     */
    private void gotoCheckout(ResponseAddToCart responseAddToCart) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        intent.putExtra(ConstantValue.STORE, store);
        intent.putExtra(ConstantValue.RESPONSE_ADD_TO_CART, responseAddToCart);
        intent.putExtra(ConstantValue.ITEMS, new ArrayList<>(itemsUpdated));
        if (getIntent().hasExtra(ConstantValue.ADDRESS)) {
            Address address = (Address) getIntent().getSerializableExtra(ConstantValue.ADDRESS);
            intent.putExtra(ConstantValue.ADDRESS, address);
        }
        startActivity(intent);
    }

    /**
     * Submit a item to server
     *
     * @param cartItemsRequest CartItemsRequest
     */
    private synchronized void processAddToCart(final CartItemsRequest cartItemsRequest) {
        // Customer token
        String token = userAccount.getCustomerToken();


        AuthUtils.isTokenValid(token, isValid -> {

            Log.d(ConstantValue.TAG_LOG, "local token " + userAccount.getCustomerToken());

            if (isValid) {
                // Add items to server
                new AddMultiItemsToCart(cartItemsRequest, token, new InvokeOnCompleteAsync<List<ResponseAddToCart>>() {
                    @Override
                    public void onComplete(List<ResponseAddToCart> responseAddToCarts) {
                        if (responseAddToCarts != null && !responseAddToCarts.isEmpty()) {
                            ResponseAddToCart responseAddToCart = responseAddToCarts.get(0);

                            if (responseAddToCart.getCartProductItems() != null && !responseAddToCart.getCartProductItems().isEmpty()) {
                                container_float_loading.setVisibility(View.GONE);
                                gotoCheckout(responseAddToCart);
                            }
                            else {
                                container_float_loading.setVisibility(View.GONE);
                                Toast.makeText(ManageBasketActivity.this, "Can't add item to cart.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            container_float_loading.setVisibility(View.GONE);
                            Toast.makeText(ManageBasketActivity.this, "Can't add item to cart.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            while (ATTEMPT_COUNT <= MAX_ATTEMPT) {
                                processAddToCart(cartItemsRequest);
                                ATTEMPT_COUNT++;
                            }
                        } else {
                            container_float_loading.setVisibility(View.GONE);
                            Toast.makeText(ManageBasketActivity.this, "Cannot add item to cart. Please try again.", Toast.LENGTH_SHORT).show();
                            LoggerHelper.showErrorLog(" add item " + e);
                        }
                    }
                });
            } else {
                container_float_loading.setVisibility(View.GONE);
                Toast.makeText(this, "token is not authorized!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_go_to_checkout.setEnabled(true);
    }

    /**
     * @return CartItemsRequest
     */
    private CartItemsRequest prepareCart() {
        CartItemsRequest itemsRequest = new CartItemsRequest();
        Customer customer = userAccount.getCustomer();
        itemsRequest.setStoreId(store.getId());
        itemsRequest.setEmail(customer.getEmail());
        return itemsRequest;
    }

    /**
     * Clear all items from list of marked in basket
     */
    private void clearBasket() {
        productItems.clear();
        listOfLinearLayout = new BasketItemViewAdapter(this, list_basket_items, productItems, productAttributeOption, fragmentManager, this, store);
        list_basket_items = listOfLinearLayout.setChildViewIntoLinearLayout();
    }

    /**
     *
     * @param storeId Long
     * @param total Float
     */
    private void calculateServiceFee(Long storeId, Float total) {

        btn_go_to_checkout.setEnabled(false);

        new CalculateServiceFee(new StoreFee(storeId, total), new CalculateServiceFee.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Float serviceFee) {
                StorePreference.saveServiceFee(ManageBasketActivity.this, serviceFee);
                btn_go_to_checkout.setEnabled(true);
            }

            @Override
            public void onError(Throwable e) {
                if (ATTEMPT_COUNT < MAX_ATTEMPT) {
                    calculateServiceFee(storeId, total);
                    ATTEMPT_COUNT++;
                }
                else {
                    Toast.makeText(ManageBasketActivity.this, "Cannot not get service fee.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Get delivery fee
     * @param formForGetDeliveryFee FormForGetDeliveryFee
     */
    private void fetchDeliveryFee(FormForGetDeliveryFee formForGetDeliveryFee) {

        btn_go_to_checkout.setEnabled(false);

        if (formForGetDeliveryFee == null) {
            Toast.makeText(ManageBasketActivity.this, "Cannot not get delivery fee. Please restart the app and try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        new FetchDeliveryFee(formForGetDeliveryFee, new InvokeOnCompleteAsync<List<StoreDeliveryFee>>() {
            @Override
            public void onComplete(List<StoreDeliveryFee> storeDeliveryFees) {
                btn_go_to_checkout.setEnabled(true);
                if (storeDeliveryFees.size() > 0) {
                    StoreDeliveryFee storeDeliveryFee = storeDeliveryFees.get(0);
                    deliveryFee = storeDeliveryFee.getDeliveryFee().floatValue();
                    store.setFee(storeDeliveryFee.getDeliveryFee().floatValue());
                    store.setShippingMethod(storeDeliveryFee.getShippingMethod());
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: ", e);
//                Toast.makeText(ManageBasketActivity.this, "Cannot not get delivery fee.", Toast.LENGTH_SHORT).show();
                AlertUtils.showConfirmDialog(ManageBasketActivity.this, "Message", "Cannot not get delivery fee.", "OK", (DialogInterface.OnClickListener) (dialogInterface, i) -> {
                    openGoogleMap();
                });
            }
        });
    }

    /**
     * Open google map
     */
    private void openGoogleMap() {
        Intent intent = new Intent(this, RegisterLocationActivity.class);
        intent.putExtra(ConstantValue.ADDRESS, LocationPreference.getLocation(this));
        intent.putExtra(ConstantValue.STORE_NAME, store.getName());
        intent.putExtra(ConstantValue.LATITUDE, store.getLatitude());
        intent.putExtra(ConstantValue.LONGITUDE, store.getLongitude());
        intent.putExtra(ConstantValue.FROM_BASKET, true);
        startActivityForResult(intent, ConstantValue.INTENT_ACTIVITY_TAG_CODE);
    }

    /**
     * Prepare for get delivery fee
     * @return FormForGetDeliveryFee
     */
    private FormForGetDeliveryFee prepareForGetDeliveryFee(Address address) {

        if (address == null) {
            if (getIntent().hasExtra(ConstantValue.ADDRESS)) {
                address = (Address) getIntent().getSerializableExtra(ConstantValue.ADDRESS);
            }
            else {
                address = LocationPreference.getLocation(this);
            }
        }

        FormForGetDeliveryFee formForGetDeliveryFee = new FormForGetDeliveryFee();
        formForGetDeliveryFee.setStoreId(store.getId());
        formForGetDeliveryFee.setLatitude(address.getLatitude());
        formForGetDeliveryFee.setLongitude(address.getLongitude());
        return formForGetDeliveryFee;
    }

    /**
     * Setter serverDateTime
     */
    public void setServerDateTime(String serverDateTime) {
        this.serverDateTime = serverDateTime;
    }
}
