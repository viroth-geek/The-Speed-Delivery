package com.planb.thespeed.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.OrderItemListViewAdapter;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.ExchangeRate;
import com.planb.thespeed.model.Option;
import com.planb.thespeed.model.OptionProduct;
import com.planb.thespeed.model.OptionValue;
import com.planb.thespeed.model.OrderDetail;
import com.planb.thespeed.model.ProductOption;
import com.planb.thespeed.model.StatusHistory;
import com.planb.thespeed.model.enumeration.MapViewType;
import com.planb.thespeed.model.enumeration.OrderStatusType;
import com.planb.thespeed.model.magento.search.SearchStoreRestriction;
import com.planb.thespeed.model.magento.store.storeList.ListStore;
import com.planb.thespeed.model.magento.store.storeList.StoreRestriction;
import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductAttributeOption;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.model.order.CustomOption;
import com.planb.thespeed.model.order.ExtensionAttribute;
import com.planb.thespeed.model.order.ItemOption;
import com.planb.thespeed.model.order.OptionDetail;
import com.planb.thespeed.model.order.OrderItem;
import com.planb.thespeed.security.CurrencyConfiguration;
import com.planb.thespeed.security.ProductLocalService;
import com.planb.thespeed.service.StoreService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.offine.optionproduct.OptionProductDAO;
import com.planb.thespeed.service.datahelper.datasource.offine.productoption.ProductOptionDAO;
import com.planb.thespeed.service.datahelper.datasource.online.FetchDeliveryDate;
import com.planb.thespeed.service.datahelper.datasource.online.FetchExchangeRate;
import com.planb.thespeed.service.datahelper.datasource.online.FetchOrderComment;
import com.planb.thespeed.service.datahelper.datasource.online.FetchOrderDetailLngLat;
import com.planb.thespeed.service.datahelper.datasource.online.FetchOrderItem;
import com.planb.thespeed.service.datahelper.datasource.online.FetchOrderServiceFee;
import com.planb.thespeed.service.datahelper.mapper.DataMatcher;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.preference.LocationPreference;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.planb.thespeed.util.NumberUtils.strMoney;
import static com.planb.thespeed.util.Utils.FormatDateTimeLocal;

/**
 * @author channarith.bong
 */
public class MyOrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_order_status;
    private TextView txt_order_id;
    private TextView txt_order_date;
    private TextView txt_order_address;

    private TextView txt_number_product;
    private TextView txt_sub_total;
    private TextView txt_delivery_fee;
    private TextView txt_service_fee;
    private TextView txt_total_price;
    private TextView txt_total_price_in_riel;
    private TextView txt_total_price_in_riel_title;
    private TextView txt_payment_method;

    private TextView txt_billing_name;
    private TextView txt_billing_address;
    private TextView txt_billing_phone;
    private TextView txt_billing_email;

    private TextView txt_shipping_name;
    private TextView txt_shipping_address;
    private TextView txt_shipping_phone;
    private TextView txt_shipping_email;

    private TextView txt_delivery_date;
    private TextView txt_comment;

    private LinearLayout driverLayout;
    private TextView txtDriverCode;
    private TextView txtDriverName;
    private TextView txtDriverPhoneNumber;
    private TextView txtTax;

    private TextView txtOrderComment;

    private Button btn_store_name;

    private Button btn_reorder;

    private RelativeLayout productLoadingProgressBar;

    private LinearLayout list_item;
    private FrameLayout loadingLayout;

    private OrderDetail orderDetail;
    private List<Product> products;

    private ProductAttributeOption productAttributeOption = new ProductAttributeOption();

    private Store store;

    private boolean isGoToStore = false;

    private boolean isGoToBasket = false;

    private Integer exchangeRate = 4100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        txt_order_status = findViewById(R.id.txt_order_status);
        txt_order_id = findViewById(R.id.txt_order_id);
        txt_order_date = findViewById(R.id.txt_order_date);
        txt_order_address = findViewById(R.id.txt_order_address);
        list_item = findViewById(R.id.list_order_product_item);

        txt_number_product = findViewById(R.id.txt_number_product);
        txt_sub_total = findViewById(R.id.txt_sub_total);
        txt_delivery_fee = findViewById(R.id.txt_delivery_fee);
        txt_service_fee = findViewById(R.id.txt_service_fee);
        txt_total_price = findViewById(R.id.txt_total_price);
        txt_total_price_in_riel = findViewById(R.id.txt_total_price_in_riel);
        txt_total_price_in_riel_title = findViewById(R.id.txt_total_price_in_riel_title);
        txt_payment_method = findViewById(R.id.txt_payment_method);

        txt_billing_name = findViewById(R.id.txt_billing_name);
        txt_billing_address = findViewById(R.id.txt_billing_address);
        txt_billing_phone = findViewById(R.id.txt_billing_phone);
        txt_billing_email = findViewById(R.id.txt_billing_email);

        txt_shipping_name = findViewById(R.id.txt_shipping_name);
        txt_shipping_address = findViewById(R.id.txt_shipping_address);
        txt_shipping_phone = findViewById(R.id.txt_shipping_phone);
        txt_shipping_email = findViewById(R.id.txt_shipping_email);
        txtTax = findViewById(R.id.txt_tax);

        txt_delivery_date = findViewById(R.id.txt_delivery_date);
        txt_comment = findViewById(R.id.txt_comment);

        txtOrderComment = findViewById(R.id.txt_order_comment);

        driverLayout = findViewById(R.id.driver_section);
        driverLayout.setVisibility(View.GONE);
        txtDriverCode = findViewById(R.id.txt_driver_code);
        txtDriverName = findViewById(R.id.txt_driver_name);
        txtDriverPhoneNumber = findViewById(R.id.txt_driver_phone_number);

        btn_reorder = findViewById(R.id.btn_reorder);
        btn_store_name = findViewById(R.id.btn_store_name);

        productLoadingProgressBar = findViewById(R.id.loading_progress_bar);

        loadingLayout = findViewById(R.id.container_float_loading);

        btn_store_name.setOnClickListener(this);
        btn_reorder.setOnClickListener(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bindData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            if (isNeedBackToHome()) {
                goHome();
            }
            finish();
        } else if (R.id.action_show_map == item.getItemId()) {
            showLocation();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Go home
     */
    private void goHome() {
        Intent intent = new Intent(this, BaseActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isNeedBackToHome()) {
            goHome();
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Request data from server
     */
    private void bindData() {

//        txtOrderComment.setVisibility(View.VISIBLE);
//        txtOrderComment.setText("Hello\nHi");

        orderDetail = (OrderDetail) getIntent().getSerializableExtra(ConstantValue.ITEMS);
        btn_store_name.setText(getStoreName(orderDetail.getStoreName()));
        if (orderDetail != null) {

            showLoading();

            fetchExchangeRate();
            getOrderServiceFee(orderDetail.getId());
            getOrderDetailLatLng(orderDetail.getId());
            getDeliveryDate(orderDetail.getId());
            getOrderComment(orderDetail.getId());
            fetchOrderItems(orderDetail.getId());

            loadListStore(prepareForGetStore(orderDetail.getStoreId()), true);

            bindOrderStatusHistoryComment();

            if (isCanReorder()) {
//                if (orderDetail.getStatus().equalsIgnoreCase(OrderStatusType.COMPLETE.name())) {
                    btn_reorder.setText(String.format("Reorder ($%s)", strMoney(orderDetail.getTotalDue().floatValue())));
//                }
//                else {
//                    btn_reorder.setVisibility(View.GONE);
//                }
            }
            else {
                btn_reorder.setText(R.string.btn_title_done);
            }

            txt_order_status.setText(orderDetail.getStatus());
            if (OrderStatusType.PENDING.toString().equalsIgnoreCase(orderDetail.getStatus()) ||
                    OrderStatusType.HOLDED.toString().equalsIgnoreCase(orderDetail.getStatus())) {
                txt_order_status.setTextColor(this.getResources().getColor(R.color.colorDeliverPrimary));
            } else if (OrderStatusType.PROCESSING.toString().equalsIgnoreCase(orderDetail.getStatus())) {
                txt_order_status.setTextColor(this.getResources().getColor(R.color.gray));
            } else if (OrderStatusType.COMPLETE.toString().equalsIgnoreCase(orderDetail.getStatus())) {
                txt_order_status.setTextColor(this.getResources().getColor(R.color.green));
            } else if (OrderStatusType.CANCELED.toString().equalsIgnoreCase(orderDetail.getStatus())) {
                txt_order_status.setTextColor(this.getResources().getColor(R.color.red));
            }

            txt_order_id.setText(orderDetail.getIncrementId());
            txt_order_address.setText(orderDetail.getBillingAddress().getStreet().get(0));

            txt_order_date.setText(String.format(" %s", FormatDateTimeLocal(orderDetail.getUpdatedAt())));

            txt_number_product.setText(String.format("%s", orderDetail.getTotalItemCount()));
            txt_sub_total.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(orderDetail.getSubtotal().floatValue())));
            txt_delivery_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(orderDetail.getShippingAmount().floatValue())));
            txt_total_price_in_riel_title.setText(R.string.total_price_in_riel);
//            txt_shipping_method.setText(orderDetail.getShippingDescription());
            txt_payment_method.setText(orderDetail.getPayment().getAdditionalInformation().get(0));
            txtTax.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(orderDetail.getTaxAmount().floatValue())));

            String billingName = orderDetail.getBillingAddress().getFirstname() + " " + orderDetail.getBillingAddress().getLastname();
            String billingAddress = orderDetail.getBillingAddress().getAddressLine();
            String billingPhone = orderDetail.getBillingAddress().getTelephone();
            String billingEmail = orderDetail.getBillingAddress().getEmail();
            txt_billing_name.setText(billingName);
            txt_billing_address.setText(billingAddress);
            txt_billing_phone.setText(billingPhone);
            txt_billing_email.setText(billingEmail);

            Address shippingAddress = orderDetail.getExtensionAttributes().getShippingAssignments().get(0).getShipping().getAddress();

            if (shippingAddress != null) {
                String shippingName = shippingAddress.getFirstname() + " " + shippingAddress.getLastname();
                String shippingAddressLine = shippingAddress.getAddressLine();
                if (shippingAddressLine == null) {
                    shippingAddressLine = shippingAddress.getStreet().get(0);
                }
                String shippingPhone = shippingAddress.getTelephone();
                String shippingEmail = shippingAddress.getEmail();
                txt_shipping_name.setText(shippingName);
                txt_shipping_address.setText(StringUtils.join(shippingAddress.getStreet(), " / "));
                txt_shipping_phone.setText(shippingPhone);
                txt_shipping_email.setText(shippingEmail);
            }
        }
    }

    /**
     * Bind order status history comment
     */
    private void bindOrderStatusHistoryComment() {
        StringBuilder commentHistory = new StringBuilder();
        for (int index = 0; index < orderDetail.getStatusHistories().size(); index++) {
            StatusHistory statusHistory = orderDetail.getStatusHistories().get(index);
            if (statusHistory.getIsVisibleOnFront() == 1) {
                commentHistory.append(statusHistory.getCreatedAt()).append(": ").append(statusHistory.getComment());
                if (index < orderDetail.getStatusHistories().size() - 1) {
                    commentHistory.append("\n");
                }
            }
        }
        if (!commentHistory.toString().isEmpty()) {
            txtOrderComment.setVisibility(View.VISIBLE);
            txtOrderComment.setText(commentHistory.toString());
        }
    }

    /**
     * check if can reorder
     * @return boolean
     */
    private boolean isCanReorder() {
        return getIntent().getBooleanExtra(ConstantValue.IS_CAN_REORDER, true);
    }

    /**
     * @return boolean
     */
    private boolean isNeedBackToHome() {
        return getIntent().getBooleanExtra(ConstantValue.HOME_CALLING, false);
    }

    /**
     * showLocation
     */
    private void showLocation() {

        if (orderDetail.getBillingAddress().getLatitude() == null || orderDetail.getBillingAddress().getLongitude() == null) {
            Toast.makeText(this, "Cannot locate order on the map.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (store == null) {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, RegisterLocationActivity.class);

        Location location = new Location(ConstantValue.LOCATION);
        location.setLatitude(orderDetail.getBillingAddress().getLatitude());
        location.setLongitude(orderDetail.getBillingAddress().getLongitude());

        intent.putExtra(ConstantValue.LOCATION, location);
        intent.putExtra(ConstantValue.ORDER_DETAIL, orderDetail);
        intent.putExtra(ConstantValue.LATITUDE, store.getLatitude());
        intent.putExtra(ConstantValue.LONGITUDE, store.getLongitude());
        intent.putExtra(ConstantValue.MAP_VIEW_TYPE, MapViewType.VIEW_ONLY);
        startActivityForResult(intent, ConstantValue.LOCATION_ACTIVITY_TAG_CODE);
    }

    /**
     * Get order detail lat lng
     * @param orderId Long
     */
    private void getOrderDetailLatLng(Long orderId) {
        new FetchOrderDetailLngLat(orderId, new InvokeOnCompleteAsync<List<String>>() {
            @Override
            public void onComplete(List<String> latLng) {
                double lat = Double.valueOf(latLng.get(0) == null ? "0" : latLng.get(0));
                double lng = Double.valueOf(latLng.get(1) == null ? "0" : latLng.get(1));
                if (lat != 0 && lng != 0) {
                    orderDetail.getBillingAddress().setLatitude(lat);
                    orderDetail.getBillingAddress().setLongitude(lng);
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: " + e);
            }
        });
    }

    /**
     * Get order service fee
     * @param id Long
     */
    private void getOrderServiceFee(Long id) {
        new FetchOrderServiceFee(id, new FetchOrderServiceFee.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String orderServiceFee) {
                updateServiceFee(Float.valueOf(orderServiceFee));
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     *
     * @param orderId Long
     */
    private void getDeliveryDate(Long orderId) {
        new FetchDeliveryDate(orderId, new FetchDeliveryDate.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String orderDate) {
                if (orderDate != null && !orderDate.isEmpty()) {
                    txt_delivery_date.setText(orderDate);
                }
                else {
                    txt_delivery_date.setText("N/A");
                }
            }

            @Override
            public void onError(Throwable e) {
                txt_delivery_date.setText("N/A");
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     *
     * @param orderId Long
     */
    private void getOrderComment(Long orderId) {
        new FetchOrderComment(orderId, new InvokeOnCompleteAsync<String>() {
            @Override
            public void onComplete(String comment) {
                if (comment != null && !comment.isEmpty()) {
                    txt_comment.setText(comment);
                }
                else {
                    txt_comment.setText("N/A");
                }
            }

            @Override
            public void onError(Throwable e) {
                txt_comment.setText("N/A");
            }
        });
    }

    /**
     *
     */
    private void showLoading() {
        txt_service_fee.setText(R.string.fetch_data_text);
        txt_total_price.setText(R.string.fetch_data_text);
        txt_total_price_in_riel.setText(R.string.fetch_data_text);
        txt_delivery_date.setText(R.string.fetch_data_text);
        txt_comment.setText(R.string.fetch_data_text);
    }

//    /**
//     *
//     * @param incrementId String
//     */
//    private void fetchOrderItems(String incrementId) {
//        new FetchOrderItems(incrementId, new FetchOrderItems.InvokeOnCompleteAsync() {
//            @Override
//            public void onComplete(List<ProductItem> productItems) {
//                productItemList = productItems;
//                loadListStore(prepareForGetStore(orderDetail.getStoreId()));
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LoggerHelper.showErrorLog("Error: ", e);
//            }
//        });
//    }

//    /**
//     * @param storeId Long
//     */
//    private void fetchProductCategory(final Long storeId) {
//        CategoryService.getInstance().loadCategoryList(storeId, new CategoryService.InvokeOnCompleteAsync() {
//            @Override
//            public void onComplete(List<ProductCategory> productCategoryList) {
//                if (productItemList != null && !productItemList.isEmpty()) {
//                    Intent intent = new Intent(MyOrderDetailActivity.this, ManageBasketActivity.class);
//                    intent.putExtra(ConstantValue.ITEMS, new ArrayList<>(itemsForView(productItemList, productCategoryList)));
//                    intent.putExtra(ConstantValue.STORE, MyOrderDetailActivity.this.store);
//
//                    btn_reorder.setVisibility(View.VISIBLE);
//                    loadingLayout.setVisibility(View.GONE);
//
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LoggerHelper.showErrorLog("Error: ", e);
//            }
//        });
//    }

    /**
     * fetch exchange rate
     */
    private void fetchExchangeRate() {
        new FetchExchangeRate(new InvokeOnCompleteAsync<List<ExchangeRate>>() {
            @Override
            public void onComplete(List<ExchangeRate> exchangeRates) {
                for (ExchangeRate rate : exchangeRates) {
                    MyOrderDetailActivity.this.exchangeRate = rate.getExchangeKh();
                    txt_total_price_in_riel_title.setText(String.format(Locale.ENGLISH, "Total Price in Riel (1$ = %d%s)", exchangeRate, CurrencyConfiguration.getRielSign()));
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MyOrderDetailActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    /**
//     *
//     * @param productItems ProductItem
//     * @return com.iota.eshopping.model.modelForView.ProductItem
//     */
//    private List<com.iota.eshopping.model.modelForView.ProductItem> itemsForView(List<ProductItem> productItems) {
//        List<com.iota.eshopping.model.modelForView.ProductItem> productItemList = new ArrayList<>();
//        for (ProductItem productItem : productItems)
//            productItemList.add(new com.iota.eshopping.model.modelForView.ProductItem<>(productItem.getProductId(), productItem.getQtyOrdered(), productItem.getPrice().floatValue(), 0F, new Product(productItem.getProductId(), productItem.getName(), productItem.getSku(), productItem.getName(), productItem.getPrice().floatValue(), productItem.getProductType(), productItem.getQtyOrdered(), productItem.getParentId())));
//        return productItemList;
//    }

    /**
     *
     * @param serviceFee Float
     */
    private void updateServiceFee(Float serviceFee) {
        txt_service_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(serviceFee)));
        txt_total_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(orderDetail.getTotalDue().floatValue())));
        txt_total_price_in_riel.setText(CurrencyConfiguration.getRielValue(orderDetail.getTotalDue(), exchangeRate));
    }

    /**
     *
     * @param storeRestriction StoreRestriction
     */
    private void loadListStore(StoreRestriction storeRestriction, boolean isForViewMap) {
        StoreService.getInstance().loadStoreList(storeRestriction, new StoreService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(ListStore listStore) {
                if (listStore.getList().size() > 0) {
                    Store store = DataMatcher.getInstance().getStore(listStore.getList().get(0));
                    MyOrderDetailActivity.this.store = store;
                    if (!isForViewMap) {
                        startActivity(intentForGoToStoreOrBasket(store));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog(" Store " + e.getMessage());
                Toast.makeText(MyOrderDetailActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * intent for go to screen
     * @param store Store
     * @return Intent
     */
    private Intent intentForGoToStoreOrBasket(Store store) {
        Intent intent = null;
        if (isGoToBasket) {
            intent = new Intent(MyOrderDetailActivity.this, ManageBasketActivity.class);
            intent.putExtra(ConstantValue.PRODUCT_ATTRIBUTE_OPTION, productAttributeOption);
            intent.putExtra(ConstantValue.ITEMS, new ArrayList<>(orderDetail.getProductItems()));
            intent.putExtra(ConstantValue.STORE, store);
            intent.putExtra(ConstantValue.ADDRESS, orderDetail.getBillingAddress());

            btn_reorder.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
        }
        else if (isGoToStore) {
            intent = new Intent(MyOrderDetailActivity.this, StoreActivity.class);
            intent.putExtra(ConstantValue.STORE, store);
        }
        return intent;
    }

    /**
     *
     * @param storeId Store Id
     * @return StoreRestriction
     */
    private StoreRestriction prepareForGetStore(Long storeId) {
        StoreRestriction storeRestriction = new StoreRestriction();
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setId(storeId);
        Address address;
        if (isGoToBasket) {
            address = orderDetail.getBillingAddress();
            if (address.getLatitude() != null && address.getLongitude() != null) {
                restriction.setLatitude(address.getLatitude());
                restriction.setLongitude(address.getLongitude());
            }
            else {
                btn_reorder.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
                Toast.makeText(this, "Cannot reorder: Address not found.", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        else if (isGoToStore) {
            address = LocationPreference.getLocation(this);
            if (address != null) {
                if (address.getLatitude() != null && address.getLongitude() != null) {
                    restriction.setLatitude(address.getLatitude());
                    restriction.setLongitude(address.getLongitude());
                }
                else {
                    btn_reorder.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(this, "Cannot reorder: Address not found.", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else {
                btn_reorder.setVisibility(View.VISIBLE);
                loadingLayout.setVisibility(View.GONE);
                Toast.makeText(this, "Cannot reorder: Address not found.", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        storeRestriction.setStoreRestriction(restriction);
        return storeRestriction;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_reorder) {
            if (isCanReorder()) {
                btn_reorder.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.VISIBLE);
                isGoToStore = false;
                isGoToBasket = true;
                saveCacheOrderItem(products, productAttributeOption);
                loadListStore(prepareForGetStore(orderDetail.getStoreId()), false);
            }
            else {
                onBackPressed();
            }
        }
        else if (view.getId() == R.id.btn_store_name) {
            isGoToBasket = false;
            isGoToStore = true;
            loadListStore(prepareForGetStore(orderDetail.getStoreId()), false);
        }
    }

    /**
     *
     * @param storeFullName String
     * @return String
     */
    private String getStoreName(String storeFullName) {
        String storeName;
        String names[] = storeFullName.split("\\n");
        try {
            storeName = names[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            LoggerHelper.showErrorLog("Store Name Error" + e.getMessage());
            storeName = names[names.length - 1];
        }
        return storeName;
    }

    /**
     * fetch order items
     * @param orderId Long
     */
    private void fetchOrderItems(Long orderId) {
        new FetchOrderItem(orderId, new InvokeOnCompleteAsync<List<OrderItem>>() {
            @Override
            public void onComplete(List<OrderItem> orderItemList) {
                bindTitleForProductItems(orderItemList);

                products = new ArrayList<>();
                List<com.planb.thespeed.model.modelForView.ProductItem> productItems = new ArrayList<>();
                for (OrderItem orderItem : orderItemList) {
                    String productUUID = UUID.randomUUID().toString();

                    Product product = new Product();
                    product.setProductUid(productUUID);
                    product.setId(orderItem.getItemId());
                    product.setParentId(orderItem.getProductId());
                    product.setName(orderItem.getName());
                    product.setSku(orderItem.getSku());
                    product.setProductType(orderItem.getProductType());
                    product.setPrice(orderItem.getPrice().floatValue());
                    product.setCount(orderItem.getQty().intValue());
                    products.add(product);
                    productItems.add(new com.planb.thespeed.model.modelForView.ProductItem<>(product.getId(), product.getCount(), product.getPrice(),product));
                    
                    if (orderItem.getProductOption() != null) {
                        com.planb.thespeed.model.order.ExtensionAttribute extensionAttribute = orderItem.getProductOption().getExtensionAttribute();
                        if (extensionAttribute != null) {

                            for (ItemOption itemOption : extensionAttribute.getItemOptions()) {
                                ProductOption productOption = new ProductOption();

                                productOption.setProductUid(productUUID);
                                productOption.setProductId(orderItem.getProductId());
                                productOption.setOriginalProductId(orderItem.getItemId());

                                productOption.setLabel(itemOption.getLabel());
                                productOption.setAttributeId(itemOption.getOptionId().toString());

                                Option option = new Option();
                                option.setProductUid(productUUID);
                                option.setLabel(itemOption.getLabel());
                                option.setValueIndex(itemOption.getOptionValue());
                                productOption.setOptions(Collections.singletonList(option));

                                productAttributeOption.getProductOptions().add(productOption);
                            }

                            List<OptionProduct> optionProducts = new ArrayList<>();
                            for (CustomOption customOption : extensionAttribute.getCustomOptions()) {
                                if (customOption.getOptionValue() != null && !customOption.getOptionValue().isEmpty()) {

                                    OptionProduct optionProduct = new OptionProduct();

                                    optionProduct.setProductUid(productUUID);
                                    optionProduct.setProductId(orderItem.getProductId());
                                    optionProduct.setOriginalProductId(orderItem.getItemId());
                                    optionProduct.setOptionId(customOption.getOptionId());
                                    optionProduct.setTitle(customOption.getLabel());

                                    if (customOption.getOptionDetails() != null) {
                                        List<OptionValue> optionValues = new ArrayList<>();
                                        for (OptionDetail optionDetail : customOption.getOptionDetails()) {
                                            OptionValue optionValue = new OptionValue();
                                            optionValue.setProductUid(productUUID);
                                            optionValue.setTitle(optionDetail.getTitle());
                                            optionValue.setOptionTypeId(optionDetail.getOptionTypeId());
                                            optionValue.setPrice(optionDetail.getPrice());
                                            optionValues.add(optionValue);
                                        }
                                        optionProduct.setOptionValues(optionValues);
                                    }

                                    optionProducts.add(optionProduct);
                                }
                            }

                            for (OptionProduct optionProduct : optionProducts) {
                                productAttributeOption.getOptionProducts().add(optionProduct);
                            }
                        }
                    }
                }

                orderDetail.setProductItems(productItems);
                productLoadingProgressBar.setVisibility(View.GONE);

                OrderItemListViewAdapter listViewAdapter = new OrderItemListViewAdapter(MyOrderDetailActivity.this, list_item, orderDetail.getProductItems());
                list_item = listViewAdapter.setChildViewIntoLinearLayout();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MyOrderDetailActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                productLoadingProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * save cache product
     * @param products List<Product>
     * @param productAttributeOption ProductAttributeOption
     */
    private void saveCacheOrderItem(List<Product> products, ProductAttributeOption productAttributeOption) {
        ProductLocalService productLocalService = new ProductLocalService(this);
        ProductOptionDAO productOptionDAO = new ProductOptionDAO(this);
        OptionProductDAO optionProductDAO = new OptionProductDAO(this);

        productLocalService.deleteAll();
        productOptionDAO.deleteAll();
        optionProductDAO.deleteAll();

        for (Product product : products) {
            product.setStoreId(orderDetail.getStoreId());
            productLocalService.insertItem(product);
        }

        for (ProductOption productOption : productAttributeOption.getProductOptions()) {
            productOptionDAO.save(productOption);
        }

        for (OptionProduct optionProduct : productAttributeOption.getOptionProducts()) {
            optionProductDAO.save(optionProduct);
        }
    }

    /**
     * bind title with option
     * @param orderItems List of OrderItem
     */
    private void bindTitleForProductItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProductOption() != null) {
                ExtensionAttribute extensionAttribute = orderItem.getProductOption().getExtensionAttribute();
                if (extensionAttribute != null) {
                    for (ItemOption itemOption : extensionAttribute.getItemOptions()) {
                        String productName = orderItem.getName();
                        productName += "\n- " + itemOption.getLabel() + ": " + itemOption.getValue();
                        orderItem.setName(productName);
                        orderItem.setSku(productName);
                    }
                    StringBuilder productName = new StringBuilder(orderItem.getSku());
                    for (int index = 0; index < extensionAttribute.getCustomOptions().size(); index++) {
                        CustomOption customOption = extensionAttribute.getCustomOptions().get(index);
                        if (customOption.getOptionDetails() != null) {
                            if (customOption.getLabel() != null && customOption.getLabel().contains("[N]")) {
                                customOption.setLabel(customOption.getLabel().replace("[N]", ""));
                            }
                            productName.append("\n- ").append(customOption.getLabel());
                            for (OptionDetail optionDetail : customOption.getOptionDetails()) {
//                                productName.append("\n\t- ").append(optionDetail.getTitle()).append(": ").append("$").append(optionDetail.getPrice());
                                productName.append("\n\t- ").append(optionDetail.getTitle());
                            }
                            orderItem.setSku(productName.toString());
                        }
                    }
                }
            }
        }
    }
}
