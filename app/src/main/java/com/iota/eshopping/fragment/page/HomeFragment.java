package com.iota.eshopping.fragment.page;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.activity.BaseActivity;
import com.iota.eshopping.activity.ManageBasketActivity;
import com.iota.eshopping.activity.RegisterLocationActivity;
import com.iota.eshopping.activity.SearchActivity;
import com.iota.eshopping.adapter.StoreRecyclerAdapter;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.event.ISaveAddress;
import com.iota.eshopping.fragment.time.NkrTimePicker;
import com.iota.eshopping.model.ServerDateTime;
import com.iota.eshopping.model.magento.search.SearchStoreRestriction;
import com.iota.eshopping.model.magento.store.storeList.ListStore;
import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.security.ProductLocalService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.online.FetchListStores;
import com.iota.eshopping.service.datahelper.datasource.online.FetchServerDateTime;
import com.iota.eshopping.service.datahelper.mapper.DataMatcher;
import com.iota.eshopping.util.DateUtil;
import com.iota.eshopping.util.DeliveryAnimationUtils;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NumberUtils;
import com.iota.eshopping.util.preference.LocationPreference;
import com.iota.eshopping.util.preference.TimeDeliveryPreference;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author channarith.bong
 */
public class HomeFragment extends Fragment implements View.OnClickListener, ISaveAddress {

    private FrameLayout viewBasket;

    private StoreRecyclerAdapter adapter;
    private RecyclerView estoreList;
    private RecyclerView.LayoutManager layoutManager;

    private RelativeLayout loadingProgressBar;
    private Button btn_deliver_time;
    private Button btn_selected_location;
    private View container_location_time;
    private Button btn_basket;

    private List<Store> storeList;

    private TextView txt_basket_item_count;
    private TextView txt_basket_item_price;

    private Pair<Integer, Float> basketItems;
    private List<ProductItem> productItems;

    private boolean isShowAds = true;
    private String[] timeSelectionToday;
    private static com.iota.eshopping.model.Address mAddress;

    private static int MAX_ATTEMPT = 2;
    private static int ATTEMPT_COUNT = 0;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private ListStore listStore;

    private int PAGE = 1;

    private String serverDateTime = DateUtil.getNowAdd45Mn();

    private int typeOfFilter = 0;

    /**
     * @return HomeFragment
     */
    public static HomeFragment getInstance(com.iota.eshopping.model.Address address) {
        mAddress = address;
        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        timeSelectionToday = view.getResources().getStringArray(R.array.static_deliver_time_today);
        container_location_time = view.findViewById(R.id.container_location_time);
        btn_deliver_time = view.findViewById(R.id.btn_deliver_time);
        btn_deliver_time.setOnClickListener(this);
        btn_selected_location = view.findViewById(R.id.btn_selected_location);
        btn_selected_location.setOnClickListener(this);
        viewBasket = view.findViewById(R.id.container_home_float_basket);
        loadingProgressBar = view.findViewById(R.id.loading_progress_bar);
        loadingProgressBar.setVisibility(View.GONE);
        btn_basket = view.findViewById(R.id.btn_basket);
        btn_basket.setOnClickListener(this);

        txt_basket_item_count = view.findViewById(R.id.txt_basket_item_count);
        txt_basket_item_price = view.findViewById(R.id.txt_basket_item_price);

        if (getResources().getBoolean(R.bool.isLarge)) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position != 0) {
                        return 1;
                    }
                    else {
                        return 2;
                    }
                }
            });
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        estoreList = view.findViewById(R.id.estore_list);
        estoreList.setHasFixedSize(true);
        estoreList.setLayoutManager(layoutManager);
        estoreList.addOnScrollListener(recyclerViewOnScrollListener);

        TimeDeliveryPreference.clear(getContext());

        checkLocationPermission();

        fetchServerDateTime();

        btn_deliver_time.setText(TimeDeliveryPreference.getTimeDeliveryText(getContext()));

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

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItem = 0;

            if (layoutManager instanceof LinearLayoutManager) {
                firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
            else if (layoutManager instanceof GridLayoutManager) {
                firstVisibleItem = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
            if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                if (!listStore.isLastPage() && loadingProgressBar.getVisibility() == View.GONE) {
                    PAGE += 1;
                    loadStoreList(prepareForGetStore(mAddress));
                }
            }
        }
    };

    /**
     * fetch server date time
     */
    private void fetchServerDateTime() {

        TimeDeliveryPreference.saveTimeDeliveryText(getContext(), "Deliver now");

        new FetchServerDateTime(new InvokeOnCompleteAsync<List<ServerDateTime>>() {
            @Override
            public void onComplete(List<ServerDateTime> serverDateTimes) {
                if (serverDateTimes != null && !serverDateTimes.isEmpty()) {
                    ServerDateTime serverDateTime = serverDateTimes.get(0);
                    String dateTime = serverDateTime.getDate() + " " + serverDateTime.getTime();
                    setServerDateTime(dateTime);
                    TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.formatDateTimeFromServerAndAdd45Mn(dateTime));
                }
                else {
                    setServerDateTime(DateUtil.getNowAdd45Mn());
                    TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.getNowAdd45Mn());
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
                        TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.getNowAdd45Mn());
                    }
                }
                else {
                    setServerDateTime(DateUtil.getNowAdd45Mn());
                    TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.getNowAdd45Mn());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        String timeDelivery = TimeDeliveryPreference.getTimeDeliveryText(getContext());
        if (timeDelivery != null && !timeDelivery.isEmpty()) {
            btn_deliver_time.setText(timeDelivery);
        }

        if (storeList != null && !storeList.isEmpty()) {
            fetchCachedProducts();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (btn_deliver_time.equals(view)) {
            showTimePicker();
        } else if (btn_selected_location.equals(view)) {
//            loadStoreFromAddress();
            showLocationPicker();
        }
        else if (btn_basket.equals(view)) {
            List<ProductItem> items = Observable.fromIterable(productItems).filter(productItem -> productItem.getCount() > 0).toList().blockingGet();
            if (!items.isEmpty()) {

                Product product = (Product) this.productItems.get(0).getItem();
                if (storeList != null) {
                    Store store = Observable.fromIterable(storeList).filter(store1 -> store1.getId().equals(product.getStoreId())).toList().blockingGet().get(0);
                    Intent intent = new Intent(getContext(), ManageBasketActivity.class);
                    intent.putExtra(ConstantValue.ITEMS, (Serializable) productItems);
                    intent.putExtra(ConstantValue.STORE, store);
                    startActivityForResult(intent, ConstantValue.GO_TO_BASKET);
                } else {
                    Toast.makeText(getContext(), "Cannot go to basket", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "No item to in basket.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantValue.MAP_DATA_TAG_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    PAGE = 1;
                    loadAddressFromMap(data);
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        } else if (requestCode == ConstantValue.GO_TO_BASKET) {
            if (data != null) {
                if (data.hasExtra(ConstantValue.PRODUCT_ITEMS)) {
                    productItems = (List<ProductItem>) data.getSerializableExtra(ConstantValue.PRODUCT_ITEMS);
                } else {
                    return;
                }
            }
            addToBasket(productItems);
            showBasketBox();
        }
    }

    /**
     * show basket box
     */
    private void showBasketBox() {

        txt_basket_item_count.setText(String.format(getString(R.string.items), basketItems.first));
        txt_basket_item_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney(basketItems.second)));

        if (viewBasket.getVisibility() != View.VISIBLE) {
            DeliveryAnimationUtils.loadAnimationSlideUp(getContext(), viewBasket);
        }
    }

    /**
     * fetch cached products
     */
    private void fetchCachedProducts() {
        ProductLocalService productLocalService = new ProductLocalService(getContext());
        List<com.iota.eshopping.model.modelForView.Product> products = productLocalService.getListItem();

        products = Observable.fromIterable(products).filter(product -> product.getCount() > 0).toList().blockingGet();

        if (products.isEmpty()) {
            if (viewBasket.getVisibility() == View.VISIBLE) {
                viewBasket.setVisibility(View.GONE);
            }
            return;
        }

        if (this.productItems == null) {
            this.productItems = new ArrayList<>();
        }
        else {
            this.productItems.clear();
        }

        for (Product product : products) {
            this.productItems.add(new ProductItem<>(product.getId(), product.getCount(), product.getPrice(), product));
        }

        addToBasket(this.productItems);
        boolean isStoreExist = false;
        for (Store store : storeList) {
            if (store.getId().equals(products.get(0).getStoreId())) {
                isStoreExist = true;
                break;
            }
        }

        if (isStoreExist) {
            showBasketBox();
        }
        else {
            if (viewBasket.getVisibility() != View.GONE) {
                viewBasket.setVisibility(View.GONE);
                DeliveryAnimationUtils.loadAnimationSlideDown(getContext(), viewBasket);
            }
        }
    }

    /**
     *
     * @param productItems list of ProductItem
     */
    private void addToBasket(List<ProductItem> productItems) {

        if (productItems == null) {
            this.productItems = new ArrayList<>();
            this.basketItems = new Pair<>(0, 0f);
            return;
        }

        int count = 0;
        float price = 0f;
        for (ProductItem productItem : productItems) {
            if (productItem.getCount() > 0) {
                count += productItem.getCount();
                for (int i = 0; i < productItem.getCount(); i++) {
                    price += productItem.getUnitPrice();
                }
            }
        }

        this.productItems = productItems;
        this.basketItems = new Pair<>(count, price);
    }

    /**
     *
     * @param address com.iota.eshopping.model.Address
     * @return StoreRestriction
     */
    private StoreRestriction prepareForGetStore(com.iota.eshopping.model.Address address) {
        StoreRestriction storeRestriction = new StoreRestriction();
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setOpen(typeOfFilter);
        restriction.setPage(PAGE);
        restriction.setLimit(ApplicationConfiguration.LIMIT);
        if (address != null) {
            restriction.setLatitude(address.getLatitude());
            restriction.setLongitude(address.getLongitude());
        }
        storeRestriction.setStoreRestriction(restriction);
        return storeRestriction;
    }


    /**
     * Get all available store for agent
     */
    private void loadStoreList(final StoreRestriction restriction) {

        if (loadingProgressBar.getVisibility() == View.GONE) {
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
        new FetchListStores(restriction, new FetchListStores.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<ListStore> listStores) {
                if (listStores != null && !listStores.isEmpty()) {

                    if (HomeFragment.this.listStore == null) {
                        HomeFragment.this.listStore = listStores.get(0);
                    } else {
                        HomeFragment.this.listStore.getList().addAll(listStores.get(0).getList());
                        HomeFragment.this.listStore.setCurrentPage(listStores.get(0).getCurrentPage());
                        HomeFragment.this.listStore.setLastPage(listStores.get(0).getLastPage());
                        HomeFragment.this.listStore.setLastPage(listStores.get(0).isLastPage());
                        HomeFragment.this.listStore.setNumberPerPage(listStores.get(0).getNumberPerPage());
                        HomeFragment.this.listStore.setTotalRecord(listStores.get(0).getTotalRecord());
                    }

                    if (HomeFragment.this.isAdded()) {
                        if (storeList == null) {
                            storeList = new ArrayList<>();
                            storeList = DataMatcher.getInstance().getStoreList(listStores.get(0).getList());

                            adapter = new StoreRecyclerAdapter(getActivity(), storeList, isShowAds);
                            adapter.setLoadingProgressBar(loadingProgressBar);
                            adapter.notifyDataSetChanged();
                            estoreList.setAdapter(adapter);
                        }
                        else {
                            storeList.addAll(DataMatcher.getInstance().getStoreList(listStores.get(0).getList()));
                            adapter.notifyDataSetChanged();
                        }

                        fetchCachedProducts();
                    } else {
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "No Store Found!", Toast.LENGTH_SHORT).show();
                }
                loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e instanceof SocketTimeoutException) {
                    ATTEMPT_COUNT++;
                    if (ATTEMPT_COUNT <= MAX_ATTEMPT) {
                        Toast.makeText(getActivity(), "Trying to get list of store ...", Toast.LENGTH_SHORT).show();
                        loadStoreList(restriction);
                    } else {
                        if (PAGE > 1) {
                            PAGE--;
                        }
                        loadingProgressBar.setVisibility(View.GONE);
                        LoggerHelper.showErrorLog(" Error Store ! ", e);
                        Toast.makeText(getActivity(), "Error: " + e, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (PAGE > 1) {
                        PAGE--;
                    }
                    loadingProgressBar.setVisibility(View.GONE);
                    LoggerHelper.showErrorLog(" Error Store ! ", e);
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Show dialog for select time
     */
    private void showTimePicker() {
        FragmentManager fragmentManager = getChildFragmentManager();
        NkrTimePicker timePicker = new NkrTimePicker();
        timePicker.setTimeChangeListener(time -> {
            Button button = container_location_time.findViewById(R.id.btn_deliver_time);
            if (time != null) {
                switch (time.first) {
                    case TODAY:
                        if (timeSelectionToday[0].equals(time.second)) {
                            button.setText(String.format("Deliver %s", time.second));
                            TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.formatDateTimeFromServerAndAdd45Mn(serverDateTime));
                            TimeDeliveryPreference.saveTimeDeliveryText(getContext(), "Deliver " + time.second);
                        } else {
                            button.setText(String.format("Today %s", time.second));
                            TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.getToday() + " " + time.second);
                            TimeDeliveryPreference.saveTimeDeliveryText(getContext(), "Today " + time.second);
                        }
                        break;
                    case TOMORROW:
                        button.setText(String.format("Tomorrow %s", time.second));
                        TimeDeliveryPreference.saveTimeDelivery(getContext(), DateUtil.getTomorrow() + " " + time.second);
                        TimeDeliveryPreference.saveTimeDeliveryText(getContext(), "Tomorrow " + time.second);
                        break;
                }
            }
        });
        timePicker.show(fragmentManager, "TIME");
    }

    /**
     * Get Address form MAP
     * @param data Intent
     */
    private void loadAddressFromMap(Intent data) {
        Address address = data.getParcelableExtra(ConstantValue.ADDRESS);
        if (address != null) {
            Location location = new Location(ConstantValue.LOCATION);
            location.setLatitude(address.getLatitude());
            location.setLongitude(address.getLongitude());
            com.iota.eshopping.model.Address address1 = new com.iota.eshopping.model.Address();
            address1.setPostcode(address.getPostalCode());
            List<String> addressLine = new ArrayList<>();
            addressLine.add(address.getAddressLine(0));
            address1.setStreet(addressLine);
            address1.setCity(address.getLocality());
            address1.setLatitude(address.getLatitude());
            address1.setLongitude(address.getLongitude());
            loadStoreList(prepareForGetStore(address1));
        }
    }

    /**
     * Open Map screen to get the location
     */
    private void showLocationPicker() {
        Intent intent = new Intent(getActivity(), RegisterLocationActivity.class);
        startActivityForResult(intent, ConstantValue.MAP_DATA_TAG_CODE);
    }

    /**
     *
     * @param intent Intent
     */
    @Subscribe
    public void getCurrentLocation(Intent intent) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Cannot get current location")
                .setMessage("Please choose delivery location")
                .setPositiveButton("OK", (dialogInterface, i) -> btn_selected_location.performClick());
        AlertDialog alertDialog = alertBuilder.create();

        Double longitude = intent.getDoubleExtra(ConstantValue.LONGITUDE, 0d);
        Double latitude = intent.getDoubleExtra(ConstantValue.LATITUDE, 0d);
        if (longitude != 0d && latitude != 0d && HomeFragment.this.listStore == null) {
            if (alertDialog.isShowing()) {
                alertDialog.cancel();
            }
            com.iota.eshopping.model.Address address = new com.iota.eshopping.model.Address(latitude, longitude);
            getGeoCode(address.getLongitude(), address.getLatitude());
            loadStoreList(prepareForGetStore(address));
            EventBus.getDefault().unregister(this);
        }
        else {
            alertDialog.show();
        }

//        com.iota.eshopping.model.Address address = LocationPreference.getLocation(getContext());
//        if (address == null) {
//            Double longitude = intent.getDoubleExtra(ConstantValue.LONGITUDE, 0d);
//            Double latitude = intent.getDoubleExtra(ConstantValue.LATITUDE, 0d);
//            if (longitude != 0d && latitude != 0d) {
//                if (alertDialog.isShowing()) {
//                    alertDialog.cancel();
//                }
//                com.iota.eshopping.model.Address address = new com.iota.eshopping.model.Address(latitude, longitude);
//                getGeoCode(address.getLongitude(), address.getLatitude());
//                loadStoreList(prepareForGetStore(address));
//            }
//            else {
//                alertDialog.show();
//            }
//        }
//        else {
//            getGeoCode(address.getLongitude(), address.getLatitude());
//            loadStoreList(prepareForGetStore(address));
//        }
    }

    /**
     *
     * @param longitude double
     * @param latitude double
     */
    private void getGeoCode(double longitude, double latitude) {
        LoggerHelper.showDebugLog("long: " + longitude + ", lat: " + latitude);
        Geocoder geocoder = new Geocoder(getActivity());
         try {
             Address address = geocoder.getFromLocation(latitude, longitude, 1).get(0);
             if (mAddress == null) {
                 mAddress = new com.iota.eshopping.model.Address();
             }
             mAddress.setLatitude(address.getLatitude());
             mAddress.setLongitude(address.getLongitude());
             mAddress.setAddressLine(address.getAddressLine(0));
             mAddress.setCountryCode(address.getCountryCode());
             mAddress.setCity(address.getAdminArea());
             mAddress.setPostcode(address.getPostalCode());
             mAddress.setCountryName(address.getCountryName());
             LocationPreference.saveLocation(getContext(), mAddress);
        } catch (IOException | IndexOutOfBoundsException e) {
            LoggerHelper.showErrorLog("Error: ", e);
//             Toast.makeText(getContext(), "Cannot get location. Please restart the app", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        super.onDetach();
    }

    /**
     * checkLocationPermission
     */
    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getActivity().requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).requestGps();
        }
    }

    /**
     * Setter serverDateTime
     */
    public void setServerDateTime(String serverDateTime) {
        this.serverDateTime = serverDateTime;
    }

    /**
     * Get btn_selected_location
     *
     * @return btn_selected_location
     */
    public Button getBtn_selected_location() {
        return btn_selected_location;
    }

    /**
     * Get loadingProgressBar
     *
     * @return loadingProgressBar
     */
    public RelativeLayout getLoadingProgressBar() {
        return loadingProgressBar;
    }

    /**
     *
     */
    private void reset() {
        if (adapter != null) {
            estoreList.setAdapter(null);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * @return click when filter
     */
    @Override
    public void onAddressSave(String type) {

        try {
            if (storeList.size() > 0 && storeList != null) {
                storeList.clear();
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {

        }
        if (type.equals(ConstantValue.PRODUCT_ALL)) {
            typeOfFilter = 0;
            StoreRestriction storeRestriction = new StoreRestriction();
            SearchStoreRestriction searchStoreRestriction = new SearchStoreRestriction();
            searchStoreRestriction.setLatitude(mAddress.getLatitude());
            searchStoreRestriction.setLongitude(mAddress.getLongitude());
            searchStoreRestriction.setOpen(0);
            storeRestriction.setStoreRestriction(searchStoreRestriction);
            loadStoreList(storeRestriction);
        } else if (type.equals(ConstantValue.PRODUCT_OPEN)) {
            typeOfFilter = 1;
            StoreRestriction storeRestriction = new StoreRestriction();
            SearchStoreRestriction searchStoreRestriction = new SearchStoreRestriction();
            searchStoreRestriction.setLatitude(mAddress.getLatitude());
            searchStoreRestriction.setLongitude(mAddress.getLongitude());
            searchStoreRestriction.setOpen(1);
            storeRestriction.setStoreRestriction(searchStoreRestriction);
            loadStoreList(storeRestriction);
        }
    }

}


