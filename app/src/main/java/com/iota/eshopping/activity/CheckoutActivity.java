package com.iota.eshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.fragment.location.AddressSelectFragment;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.model.StoreDeliveryFee;
import com.iota.eshopping.model.enumeration.OrderStatusType;
import com.iota.eshopping.model.form.FormForGetDeliveryFee;
import com.iota.eshopping.model.magento.addToCart.ResponseAddToCart;
import com.iota.eshopping.model.magento.order.ExtensionAttribute;
import com.iota.eshopping.model.magento.order.OrderProduct;
import com.iota.eshopping.model.magento.search.SearchStoreRestriction;
import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.model.modelForView.CreateAddress;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.security.ProductLocalService;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.iota.eshopping.service.datahelper.datasource.offine.optionproduct.OptionProductDAO;
import com.iota.eshopping.service.datahelper.datasource.offine.productoption.ProductOptionDAO;
import com.iota.eshopping.service.datahelper.datasource.online.AddNewAddress;
import com.iota.eshopping.service.datahelper.datasource.online.BulkOrderProducts;
import com.iota.eshopping.service.datahelper.datasource.online.ClearItemFromCart;
import com.iota.eshopping.service.datahelper.datasource.online.FetchDeliveryFee;
import com.iota.eshopping.service.datahelper.datasource.online.FetchOrderDetails;
import com.iota.eshopping.util.AlertUtils;
import com.iota.eshopping.util.AuthUtils;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.PhoneNumberField;
import com.iota.eshopping.util.preference.LocationPreference;
import com.iota.eshopping.util.preference.StorePreference;
import com.iota.eshopping.util.preference.TimeDeliveryPreference;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.iota.eshopping.util.NumberUtils.strMoney;

/**
 * @author channarith.bong
 */
public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_change_address;
    private TextView txt_delivery_address;
    private TextView txt_delivery_fee;
    private TextView txt_tax;
    private TextView txt_total;
    private EditText txt_comment;
    private TextView txt_text_count;
    private EditText edtStreet;
    private Button btn_place_order;
    private View container_float_loading;
    private TextView txt_container_loading_label;
    private ProgressBar loading_cycle_i;
    private PhoneNumberField edt_phone_number;

    private Address address;
    private List<ProductItem> productItems;
    private float itemAmount;
    private Store store;
    private ExtensionAttribute extensionAttribute;

    private ResponseAddToCart responseAddToCart;

    private UserAccount userAccount;

    private float serviceFee = 0;
    private boolean shouldSaveAddress = true;
    private FetchAddressDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        userAccount = new UserAccount(this);
        btn_change_address = findViewById(R.id.btn_change_address1);

        txt_delivery_address = findViewById(R.id.txt_delivery_address);
        txt_delivery_address.setOnClickListener(view -> this.loadMap());

        edtStreet = findViewById(R.id.edt_street);

        TextView txt_sub_total = findViewById(R.id.txt_sub_total);
        txt_delivery_fee = findViewById(R.id.txt_delivery_fee);
        TextView txt_service_fee = findViewById(R.id.txt_service_fee);
        txt_total = findViewById(R.id.txt_total);
        btn_change_address.setOnClickListener(this);
        btn_place_order = findViewById(R.id.btn_place_order);
        container_float_loading = findViewById(R.id.container_float_loading);
        txt_container_loading_label = findViewById(R.id.txt_container_loading_label);
        loading_cycle_i = findViewById(R.id.loading_cycle_ii);
        edt_phone_number = findViewById(R.id.edt_phone_number);
        txt_comment = findViewById(R.id.txt_comment);
        txt_text_count = findViewById(R.id.txt_text_count);
        txt_tax = findViewById(R.id.txt_tax);

        db = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bindData();

        productItems = (List<ProductItem>) getIntent().getSerializableExtra(ConstantValue.ITEMS);
        store = (Store) getIntent().getSerializableExtra(ConstantValue.STORE);

        for (ProductItem productItem : productItems) {
            for (int i = 0; i < productItem.getCount(); i++) {
                itemAmount += Float.valueOf(strMoney(productItem.getUnitPrice()));
            }
        }
        if (itemAmount == 0) {
            finish();
        }

        serviceFee = StorePreference.getServiceFee(this);

        txt_sub_total.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(itemAmount)));
        txt_service_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(serviceFee)));
        txt_delivery_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(store.getFee())));
        txt_total.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney((itemAmount + serviceFee + store.getFee()))));

        if (getIntent().hasExtra(ConstantValue.RESPONSE_ADD_TO_CART)) {
            responseAddToCart = (ResponseAddToCart) getIntent().getSerializableExtra(ConstantValue.RESPONSE_ADD_TO_CART);
        }

        if (responseAddToCart != null) {
            txt_tax.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(Float.valueOf(String.valueOf(responseAddToCart.getTax())))));
        }

        if (getIntent().hasExtra(ConstantValue.ADDRESS)) {
            address = (Address) getIntent().getSerializableExtra(ConstantValue.ADDRESS);
        }
        else {
            address = LocationPreference.getLocation(this);
        }

        if (address != null) {

            extensionAttribute = new ExtensionAttribute();
            extensionAttribute.setLongitude(address.getLongitude().toString());
            extensionAttribute.setLatitude(address.getLatitude().toString());

            if (address.getStreet().size() > 0) {
                txt_delivery_address.setText(address.getStreet().get(0));
            }
            else {
                txt_delivery_address.setText(address.getAddressLine());
            }

//            if (address.getTelephone() != null && !address.getTelephone().isEmpty()) {
//                edt_phone_number.setText(address.getTelephone());
//            }

        }
        else {
            txt_delivery_address.setText(R.string.no_address_found);
            Toast.makeText(this, R.string.no_address_found, Toast.LENGTH_SHORT).show();
        }

        txt_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txt_text_count.setText(String.format(Locale.ENGLISH, "%d/400", charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            clearBasketFromServer();
//            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (btn_change_address.equals(v)) {
            if (checkListAddress().size() > 0) {
                showAddressSelectedFragment();
            }
            else {
                loadMap();
            }
        }
    }

    /**
     * check address list
     * @return List<Address>
     */
    private List<Address> checkListAddress() {
        List<Address> addressList = null;
        try {
            addressList = db.getListAddress();
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Local Address List Error : " + e.getMessage());
        }
        return addressList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == ConstantValue.HOME_CALLING_CODE) {
            finish();
        }

        if (ConstantValue.INTENT_ACTIVITY_TAG_CODE == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                if (data.hasExtra(ConstantValue.ADDRESS)) {

                    android.location.Address addressMap = (android.location.Address) data.getExtras().get(ConstantValue.ADDRESS);

                    if (addressMap != null) {
                        address = new Address();
                        address.setCountryId(addressMap.getCountryCode());
                        address.setCity(addressMap.getLocality());
                        address.setPostcode(addressMap.getPostalCode() == null ? ConstantValue.POST_CODE : addressMap.getPostalCode());
                        address.setAddressLine(addressMap.getAddressLine(0));
                        List<String> streets = new ArrayList<>();
                        streets.add(addressMap.getAddressLine(0));
                        address.setStreet(streets);
                        address.setLatitude(addressMap.getLatitude());
                        address.setLongitude(addressMap.getLongitude());

                        extensionAttribute = new ExtensionAttribute();
                        extensionAttribute.setLongitude(addressMap.getLongitude()+"");
                        extensionAttribute.setLatitude(addressMap.getLatitude()+"");

                        loadDeliveryFee(prepareForGetDeliveryFee());
//                        loadListStore(prepareStore(new SearchStoreRestriction()));
                        txt_delivery_address.setText(addressMap.getAddressLine(0));
                        shouldSaveAddress = true;

//                        Intent intent = new Intent(this, AddAddressActivity.class);
//                        intent.putExtra(ConstantValue.ADDRESS, address);
//                        intent.putExtra(ConstantValue.EDIT_ADDRESS, false);
//                        this.startActivity(intent);

                    } else {
                        LoggerHelper.showErrorLog("Address Null");
                        Toast.makeText(this, " Cannot get address!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        clearBasketFromServer();
        super.onBackPressed();
    }

    /**
     *
     */
    private void prepareCheckout() {
        btn_place_order.setEnabled(false);
        showLoadingBar(true, true, "Processing");
        OrderProduct orderProduct = getCustomerReadyHasCart();

        if (orderProduct != null) {
            if (orderProduct.getDeliveryDate() == null || orderProduct.getDeliveryDate().isEmpty()) {
                Toast.makeText(this, "Please add delivery date.", Toast.LENGTH_SHORT).show();
            }
            if (shouldSaveAddress) {
                saveShippingAddress(this.address);
            }
            checkout(orderProduct);
        }
    }

    /**
     *
     */
    private void checkout(final OrderProduct customerBoughtProduct) {
        final String token = userAccount.getCustomer().getRpToken();
        AuthUtils.isTokenValid(token, isValid -> {
            if (isValid) {
                new BulkOrderProducts(customerBoughtProduct, token, new BulkOrderProducts.InvokeOnCompleteAsync() {
                    @Override
                    public void onComplete(List<String> orderIncrements) {
                        if (orderIncrements != null) {
                            showLoadingBar(true, false, "Order ID : " + orderIncrements.get(0));
                            new ProductLocalService(CheckoutActivity.this).deleteAll();
                            new ProductOptionDAO(CheckoutActivity.this).deleteAll();
                            new OptionProductDAO(CheckoutActivity.this).deleteAll();
                            gotoMyOrderDetail(orderIncrements.get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            Toast.makeText(CheckoutActivity.this, " Internet Problem ... " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            showLoadingBar(false, true, null);
                        } else {
                            Toast.makeText(CheckoutActivity.this, " ORDER ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            LoggerHelper.showErrorLog(" ORDER ERROR " + e);
                            showLoadingBar(false, true, null);
                        }
                        btn_place_order.setEnabled(true);
                    }
                });
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Request data
     */
    private void bindData() {
        btn_place_order.setOnClickListener(view -> {
            if (address == null) {
                Toast.makeText(CheckoutActivity.this, "Please select a location! ", Toast.LENGTH_SHORT).show();
            } else if (getTelephone().isEmpty() || getTelephone().length() <= 10) {
                Toast.makeText(CheckoutActivity.this, "Invalid phone number.", Toast.LENGTH_SHORT).show();
            } else {
                prepareCheckout();
            }
        });
    }

    /**
     *
     */
    private void gotoMyOrderDetail(String orderIncrements) {
        showLoadingBar(true, true, " Load order detail ..");
        final Long id = userAccount.getCustomer().getId();
        loadOrderDetail(id, orderIncrements);
    }


    /**
     * @param id Long
     * @param orderIncrement String
     */
    private void loadOrderDetail(Long id, final String orderIncrement) {
        new FetchOrderDetails(id, orderIncrement, OrderStatusType.PENDING, new FetchOrderDetails.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<OrderDetail> orderDetails) {
                if (orderDetails != null) {
                    for (OrderDetail orderDetail : orderDetails) {
                        if (orderDetail.getIncrementId().equals(orderIncrement)) {
                            showLoadingBar(true, false, " Order detail get success !");
                            Intent intent = new Intent(CheckoutActivity.this, MyOrderDetailActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConstantValue.ITEMS, orderDetail);
                            intent.putExtra(ConstantValue.IS_CAN_REORDER, false);
                            intent.putExtra(ConstantValue.HOME_CALLING, true);
                            startActivityForResult(intent, ConstantValue.INTENT_ACTIVITY_TAG_CODE);
                            finish();
                        }
                    }
                } else {
                    showLoadingBar(false, false, "Cannot get order detail!");
                    LoggerHelper.showErrorLog("Order Detail Not Found!");
                }
            }

            @Override
            public void onError(Throwable e) {
                showLoadingBar(false, false, "Cannot get order detail!");
                LoggerHelper.showErrorLog("Order Detail : " + e);
                Toast.makeText(CheckoutActivity.this, "Cannot get order detail: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Clear all item for reselect item
     */
    private synchronized void clearBasketFromServer() {
        final String mail = userAccount.getCustomer().getEmail();
        final String token = userAccount.getCustomerToken();
        showLoadingBar(true, true, "Checking Basket..");
        new ClearItemFromCart(mail, token, new ClearItemFromCart.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Integer itemNumber) {
                if (itemNumber != null) {
                    if (itemNumber != 0) {
                        clearBasketFromServer();
                    } else {
                        finish();
                    }
                } else {
                    clearBasketFromServer();
                }
            }

            @Override
            public void onError(Throwable e) {
                showLoadingBar(false, false, ExceptionUtils.translateExceptionMessage(e));
                Toast.makeText(CheckoutActivity.this, ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("Clear Item Error ", e);
                finish();
            }
        });
    }

    /**
     * @return OrderProduct
     */
    private OrderProduct getCustomerReadyHasCart() {
        OrderProduct orderProduct = new OrderProduct();
        Customer customer = userAccount.getCustomer();
        List<String> street = new ArrayList<>();
        orderProduct.setShippingCode(store.getShippingMethod());
        if (this.address != null) {
            Address address = this.address;
            address.getStreet().add(edtStreet.getText().toString());
            address.setAddressLine(null);
            address.setCountryName(null);
            address.setCountryCode(null);
            address.setLatitude(null);
            address.setLongitude(null);
            address.setId(null);
            address.setTelephone(getTelephone());
            address.setLastname(customer.getLastname());
            address.setFirstname(customer.getFirstname());
            address.setPostcode("12101");
            address.setCountryId("KH");
            orderProduct.setAddress(address);
        } else {
            // The default value if location null
            street.add("271");
            Address address = new Address();
            address.setLastname(customer.getLastname());
            address.setFirstname(customer.getFirstname());
            address.setPostcode("12101");
            address.setCountryId("KH");
            address.setStreet(street);
            address.setCity("Phnom Penh");
            address.setTelephone("010312444");
            orderProduct.setAddress(address);
        }
        orderProduct.setEmail(customer.getEmail());
        orderProduct.setComment(txt_comment.getText().toString().trim());
        orderProduct.setDeliveryDate(TimeDeliveryPreference.getTimeDelivery(this));

        if (extensionAttribute.getLatitude() != null && extensionAttribute.getLongitude() != null) {
            address.setExtensionAttribute(extensionAttribute);
        }
        else {
            Toast.makeText(this, "No address available.", Toast.LENGTH_SHORT).show();
            return null;
        }
        return orderProduct;
    }

    /**
     * @param productItems list of ProductItem
     */
    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
    }

    /**
     * @return String
     */
    private String getTelephone() {
        String phone = edt_phone_number.getText().toString();
        if (phone.equals(ConstantValue.TEL_SAMPLE)) {
            phone = "";
        }
        return phone;
    }

    /**
     *
     */
    private void showLoadingBar(boolean show, boolean showProgress, String label) {
        container_float_loading.setVisibility(show ? View.VISIBLE : View.GONE);
        txt_container_loading_label.setText(label != null ? label : "Loading...");
        loading_cycle_i.setVisibility(showProgress ? View.VISIBLE : View.GONE);
        btn_place_order.setEnabled(!show);
    }

    /**
     * Load map activity to detect location
     */
    private void loadMap() {
        Intent intent = new Intent(this, RegisterLocationActivity.class);
        intent.putExtra(ConstantValue.ADDRESS, address);
        intent.putExtra(ConstantValue.STORE_NAME, store.getName());
        intent.putExtra(ConstantValue.LATITUDE, store.getLatitude());
        intent.putExtra(ConstantValue.LONGITUDE, store.getLongitude());
        startActivityForResult(intent, ConstantValue.INTENT_ACTIVITY_TAG_CODE);
    }

    /**
     * @param restriction SearchStoreRestriction
     * @return StoreRestriction
     */
    private StoreRestriction prepareStore(SearchStoreRestriction restriction) {
        StoreRestriction storeRestriction = new StoreRestriction();
        restriction.setId(store.getId());
        restriction.setLongitude(address.getLongitude());
        restriction.setLatitude(address.getLatitude());
        storeRestriction.setStoreRestriction(restriction);
        return storeRestriction;
    }

    /**
     * @param storeRestriction StoreRestriction
     */
//    private void loadListStore(StoreRestriction storeRestriction) {
//
//        txt_delivery_fee.setText(R.string.updating_price);
//        txt_total.setText(R.string.updating_price);
//
//        StoreService.getInstance().loadStoreList(storeRestriction, new StoreService.InvokeOnCompleteAsync() {
//            @Override
//            public void onComplete(List<Store> stores) {
//                if (stores != null && !stores.isEmpty()) {
//                    store = stores.get(0);
//                    txt_delivery_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(store.getFee())));
//                    txt_total.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney((itemAmount + serviceFee + store.getFee()))));
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LoggerHelper.showErrorLog(" Store " + e.getMessage());
//            }
//        });
//    }

    /**
     *
     * @param address Address
     */
    private void saveShippingAddress(Address address) {
        new AddNewAddress(prepareData(address), new AddNewAddress.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<com.iota.eshopping.model.modelForView.Address> addresses) {
                // save shipping address successfully
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    /**
     *
     * @param address com.iota.eshopping.model.Address
     * @return CreateAddress
     */
    private CreateAddress prepareData(com.iota.eshopping.model.Address address) {
        LoggerHelper.showDebugLog("Add: " + address);
        CreateAddress createAddress = new CreateAddress();
        com.iota.eshopping.model.modelForView.Address add = new com.iota.eshopping.model.modelForView.Address();
        add.setCustomerId(new UserAccount(this).getCustomer().getId());
        add.setPostCode(address.getPostcode());
        add.setCity(address.getCity());
        add.setStreets(Arrays.asList(address.getStreet().get(0), edtStreet.getText().toString()));
        add.setTelephone(address.getTelephone());
        add.setCompany("");
        add.setDefaultBilling(true);
        add.setDefaultShipping(true);
        add.setLongitude(Double.valueOf(address.getExtensionAttribute().getLongitude()));
        add.setLatitude(Double.valueOf(address.getExtensionAttribute().getLatitude()));
        createAddress.setAddress(add);
        return createAddress;
    }

    /**
     *
     */
    private void showAddressSelectedFragment() {
        AddressSelectFragment addressSelectFragment = new AddressSelectFragment();
        addressSelectFragment.setOnChangeAddress(new AddressSelectFragment.OnChangeAddress() {
            @Override
            public void onAddressSelect(com.iota.eshopping.model.Address address) {
                if (address != null) {

                    CheckoutActivity.this.shouldSaveAddress = false;
                    CheckoutActivity.this.address = address;

                    edt_phone_number.setText(address.getTelephone());
                    txt_delivery_address.setText(address.getAddressLine());

                    extensionAttribute = new ExtensionAttribute();
                    extensionAttribute.setLongitude(address.getLongitude()+"");
                    extensionAttribute.setLatitude(address.getLatitude()+"");

//                    loadListStore(prepareStore(new SearchStoreRestriction()));
                    loadDeliveryFee(prepareForGetDeliveryFee());
                }
            }

            @Override
            public void onIsRequestMap(boolean isRequest) {
                if (isRequest) {
                    loadMap();
                }
            }
        });

        addressSelectFragment.show(getSupportFragmentManager(), ConstantValue.ADDRESS);
    }

    /**
     * load delivery fee
     * @param formForGetDeliveryFee FormForGetDeliveryFee
     */
    private void loadDeliveryFee(FormForGetDeliveryFee formForGetDeliveryFee) {

        txt_delivery_fee.setText(R.string.updating_price);
        txt_total.setText(R.string.updating_price);

        new FetchDeliveryFee(formForGetDeliveryFee, new InvokeOnCompleteAsync<List<StoreDeliveryFee>>() {
            @Override
            public void onComplete(List<StoreDeliveryFee> storeDeliveryFees) {
                if (storeDeliveryFees.size() > 0) {
                    StoreDeliveryFee storeDeliveryFee = storeDeliveryFees.get(0);
                    store.setFee(storeDeliveryFee.getDeliveryFee().floatValue());
                    store.setShippingMethod(storeDeliveryFee.getShippingMethod());
                    store.setDistant(storeDeliveryFee.getDistance().floatValue());

                    txt_delivery_fee.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney(store.getFee())));
                    txt_total.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), strMoney((itemAmount + serviceFee + store.getFee()))));
                    btn_place_order.setEnabled(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                btn_place_order.setEnabled(false);
                txt_delivery_fee.setText(R.string.error_getting_data);
                txt_total.setText(R.string.error_getting_data);

                AlertUtils.showConfirmDialog(CheckoutActivity.this, ExceptionUtils.translateExceptionMessage(e), "Please reselect delivery address", "OK", (dialogInterface, i) -> {
                    btn_change_address.performClick();
                });

                LoggerHelper.showErrorLog(" Error " + e);
            }
        });
    }

    /**
     * form for get delivery fee
     * @return FormForGetDeliveryFee
     */
    private FormForGetDeliveryFee prepareForGetDeliveryFee() {
        FormForGetDeliveryFee formForGetDeliveryFee = new FormForGetDeliveryFee();
        formForGetDeliveryFee.setStoreId(store.getId());
        formForGetDeliveryFee.setLatitude(address.getLatitude());
        formForGetDeliveryFee.setLongitude(address.getLongitude());
        return formForGetDeliveryFee;
    }
}
